/*
 * Licensed to the University of California, Berkeley under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package tachyon.worker.eviction;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import tachyon.Pair;
import tachyon.worker.tiered.BlockInfo;
import tachyon.worker.tiered.StorageDir;
import tachyon.worker.tiered.StorageTier;

/**
 * Base class for evicting blocks by LRFU strategy.
 */
public abstract class EvictLRFUBase extends EvictStrategy {
  private final boolean mLastTier;
  private final StorageDir[] mDir;
  private final StorageTier mStorageTier;
  private final Multimap<StorageDir, Long> mEvictingBlockIds;

  // (1/mP)^(mR*x)
  private double mP = 2.0;
  private double mR = 0.5;

  private Map<Long, Double> mBlockIdToCRF = new HashMap<Long, Double>();
  private Map<Long, Long> mBlockIdToLastAccessTime = new HashMap<Long, Long>();

  private AtomicLong mTimeCount = new AtomicLong(0L);

  protected EvictLRFUBase(boolean isLastTier, StorageTier storageTier) {
    mStorageTier = storageTier;
    mDir = storageTier.getStorageDirs();
    mLastTier = isLastTier;
    mEvictingBlockIds = Multimaps.synchronizedMultimap(HashMultimap.<StorageDir, Long>create());
    for (StorageDir dir : mDir) {
      for (long id : dir.getBlockIds()) {
        mBlockIdToCRF.put(id, 0.0);
        mBlockIdToLastAccessTime.put(id, 0L);
      }
    }
  }

  /**
   * Check if the current block can be evicted
   *
   * @param blockId the Id of the block
   * @param pinList the list of the pinned files
   * @return true if the block can be evicted, false otherwise
   */
  protected boolean blockEvictable(long blockId, Set<Integer> pinList) {
    return !mLastTier || !pinList.contains(tachyon.master.BlockInfo.computeInodeId(blockId));
  }

  /**
   * Clean the blocks that have been evicted from evicting list in each StorageDir
   */
  protected void cleanEvictingBlockIds() {
    Iterator<Entry<StorageDir, Long>> iterator = mEvictingBlockIds.entries().iterator();
    while (iterator.hasNext()) {
      Entry<StorageDir, Long> block = iterator.next();
      StorageDir dir = block.getKey();
      long blockId = block.getValue();
      if (!dir.containsBlock(blockId)) {
        iterator.remove();
      }
    }
  }
  
  @Override
  public void onAdd(long blockId) {
    synchronized (mBlockIdToLastAccessTime) {
      synchronized (mBlockIdToCRF) {
        mTimeCount.getAndIncrement();
        long lastAccessTime = 0;
        double newCRF = 1.0 ;
        mBlockIdToLastAccessTime.put(blockId, mTimeCount.get());
        mBlockIdToCRF.put(blockId, newCRF);
      }
    } 
  }

  @Override
  public void onRead(long blockId) {
    synchronized (mBlockIdToLastAccessTime) {
      synchronized (mBlockIdToCRF) {
        mTimeCount.getAndIncrement();
        long lastAccessTime = mBlockIdToLastAccessTime.get(blockId);
        long delta = mTimeCount.get() - lastAccessTime;
        double oldCRF = mBlockIdToCRF.get(blockId);
        double newCRF = 1.0 + Math.pow(1.0 / mP, mR * delta) * oldCRF;
        mBlockIdToLastAccessTime.put(blockId, mTimeCount.get());
        mBlockIdToCRF.put(blockId, newCRF);
      }
    }
  }
  
  @Override
  public void onDelete(long blockId) {
    synchronized (mBlockIdToLastAccessTime) {
      synchronized (mBlockIdToCRF) {
        mTimeCount.getAndIncrement();
        mBlockIdToLastAccessTime.remove(blockId);
        mBlockIdToCRF.remove(blockId);
      }
    }
  }

  public void onMiss() {
    synchronized (mBlockIdToLastAccessTime) {
      synchronized (mBlockIdToCRF) {
        mTimeCount.getAndIncrement();
        for (Entry<Long, Long> entry : mBlockIdToLastAccessTime.entrySet()) {
          long blockId = entry.getKey();
          long lastAccessTime = entry.getValue();
          long delta = mTimeCount.getAndIncrement() - lastAccessTime;
          double oldCRF = mBlockIdToCRF.get(blockId);
          double newCRF = Math.pow(1.0 / mP, mR * delta) * oldCRF;
          mBlockIdToCRF.put(blockId, newCRF);
        }
      }
    }
  }

  /**
   * Update the blocks that are being evicted in each StorageDir
   * 
   * @param blockList The list of blocks that will be added in to evicting list
   */
  protected void updateEvictingBlockIds(Collection<BlockInfo> blockList) {
    for (BlockInfo block : blockList) {
      StorageDir dir = block.getStorageDir();
      long blockId = block.getBlockId();
      if (dir.containsBlock(blockId)) {
        mEvictingBlockIds.put(dir, blockId);
      }
    }
  }

  /**
   * Get block with minimum CRF from a StorageDir
   *
   * @param curDir current StorageDir
   * @param toEvictBlockIds the Ids of blocks that have been selected to evict
   * @param pinList list of pinned files
   * @return the oldest access information of current StorageDir
   */
  protected Pair<Long, Double> getLRFUBlock(StorageDir curDir, Collection<Long> toEvictBlockIds,
      Set<Integer> pinList) {
    long lrfuBlockId = -1;
    double minimumCRF = Double.MAX_VALUE;
    Set<Long> evictingBlockIds = (Set<Long>) mEvictingBlockIds.get(curDir);

    for (long blockId : curDir.getBlockIds()) {
      double crf = mBlockIdToCRF.get(blockId);
      if (toEvictBlockIds.contains(blockId) || evictingBlockIds.contains(blockId)) {
        continue;
      }
      if (crf < minimumCRF && !curDir.isBlockLocked(blockId) && blockEvictable(blockId, pinList)) {
        lrfuBlockId = blockId;
        minimumCRF = crf;
      }
    }
    return new Pair<Long, Double>(lrfuBlockId, minimumCRF);
  }
}
