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

package tachyon.worker.block.evictor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tachyon.Constants;
import tachyon.Sessions;
import tachyon.StorageDirId;
import tachyon.collections.Pair;
import tachyon.conf.TachyonConf;
import tachyon.exception.BlockDoesNotExistException;
import tachyon.worker.WorkerContext;
import tachyon.worker.block.BlockMetadataManagerView;
import tachyon.worker.block.BlockStoreLocation;
import tachyon.worker.block.allocator.Allocator;
import tachyon.worker.block.meta.BlockMeta;
import tachyon.worker.block.meta.StorageDirView;
import tachyon.worker.block.meta.StorageTierView;

public class ARCEvictor extends EvictorBase {
  private static final Logger LOG = LoggerFactory.getLogger(Constants.LOGGER_TYPE);

  private static final int LINKED_HASH_MAP_INIT_CAPACITY = 200;
  private static final float LINKED_HASH_MAP_INIT_LOAD_FACTOR = 0.75f;
  private static final boolean LINKED_HASH_MAP_ACCESS_ORDERED = true;
  private static final boolean UNUSED_MAP_VALUE = true;

  /**
   * access-ordered {@link java.util.LinkedHashMap} from blockId to {@link #UNUSED_MAP_VALUE}(just a
   * placeholder to occupy the value), acts as a LRU double linked list where most recently accessed
   * element is put at the tail while least recently accessed element is put at the head.
   */
  private Map<Long, Map<Long, Boolean>> mLRUT1 = new ConcurrentHashMap<Long, Map<Long, Boolean>>();
  private Map<Long, Map<Long, Boolean>> mLRUB1 = new ConcurrentHashMap<Long, Map<Long, Boolean>>();
  private Map<Long, Map<Long, Boolean>> mLRUT2 = new ConcurrentHashMap<Long, Map<Long, Boolean>>();
  private Map<Long, Map<Long, Boolean>> mLRUB2 = new ConcurrentHashMap<Long, Map<Long, Boolean>>();

  // store blocks moved from other tiers before accessing
  // TODO when the temp blocks should be evicted
  // moved blocks have two kinds: from upper to lower and from lower to upper
  // the first is evict operation and the second is promote

  private Map<Long, Set<Long>> mTmpMovedBlocks = new ConcurrentHashMap<Long, Set<Long>>();

  private Map<Long, Long> mLRUT1Bytes = new ConcurrentHashMap<Long, Long>();
  private Map<Long, Long> mLRUB1Bytes = new ConcurrentHashMap<Long, Long>();
  private Map<Long, Long> mLRUT2Bytes = new ConcurrentHashMap<Long, Long>();
  private Map<Long, Long> mLRUB2Bytes = new ConcurrentHashMap<Long, Long>();

  private Map<Long, Long> mTotalBytes = new ConcurrentHashMap<Long, Long>();
  private Map<Long, Long> mT1LimitBytes = new ConcurrentHashMap<Long, Long>();

  private Map<Long, Long> mBlockSize = new ConcurrentHashMap<Long, Long>();

  private final TachyonConf mTachyonConf;

  private int mHotLimit = 2;
  private double mHistoryBlocksTimes = 2.0;
  private double mLeastLRUPercent = 0.1;
  private double mLeastLFUPercent = 0.1;

  private Map<Long, Map<Long, Integer>> mBlockAccessTimes =
      new ConcurrentHashMap<Long, Map<Long, Integer>>();

  /**
   * @param view a view of block metadata information
   * @param allocator an allocation policy
   */
  public ARCEvictor(BlockMetadataManagerView view, Allocator allocator) {
    super(view, allocator);
    mTachyonConf = WorkerContext.getConf();
    mHotLimit = mTachyonConf.getInt(Constants.WORKER_EVICTOR_ARC_HOT_LIMIT);
    // preload existing blocks loaded by StorageDir to Evictor
    for (StorageTierView tierView : mManagerView.getTierViews()) {
      for (StorageDirView dirView : tierView.getDirViews()) {
        int tierAlias = tierView.getTierViewAlias();
        int tierLevel = tierView.getTierViewLevel();
        int dirIndex = dirView.getDirViewIndex();
        long storageDirId = StorageDirId.getStorageDirId(tierLevel, tierAlias, dirIndex);
        long totalBytes = dirView.getAvailableBytes() + dirView.getEvitableBytes();
        long t1Bytes = 0;
        Map<Long, Integer> blockAccessTimes = new ConcurrentHashMap<Long, Integer>();
        Map<Long, Boolean> t1 =
            Collections.synchronizedMap(new LinkedHashMap<Long, Boolean>(
                LINKED_HASH_MAP_INIT_CAPACITY, LINKED_HASH_MAP_INIT_LOAD_FACTOR,
                LINKED_HASH_MAP_ACCESS_ORDERED));
        Map<Long, Boolean> b1 =
            Collections.synchronizedMap(new LinkedHashMap<Long, Boolean>(
                LINKED_HASH_MAP_INIT_CAPACITY, LINKED_HASH_MAP_INIT_LOAD_FACTOR,
                LINKED_HASH_MAP_ACCESS_ORDERED));
        Map<Long, Boolean> t2 =
            Collections.synchronizedMap(new LinkedHashMap<Long, Boolean>(
                LINKED_HASH_MAP_INIT_CAPACITY, LINKED_HASH_MAP_INIT_LOAD_FACTOR,
                LINKED_HASH_MAP_ACCESS_ORDERED));
        Map<Long, Boolean> b2 =
            Collections.synchronizedMap(new LinkedHashMap<Long, Boolean>(
                LINKED_HASH_MAP_INIT_CAPACITY, LINKED_HASH_MAP_INIT_LOAD_FACTOR,
                LINKED_HASH_MAP_ACCESS_ORDERED));
        Set<Long> tmpBlocks = new HashSet<Long>();
        for (BlockMeta blockMeta : dirView.getEvictableBlocks()) { // all blocks with initial view
          long blockId = blockMeta.getBlockId();
          long blocksize = blockMeta.getBlockSize();
          t1.put(blockId, UNUSED_MAP_VALUE);
          t1Bytes += blocksize;
          mBlockSize.put(blockId, blocksize);
          blockAccessTimes.put(blockId, 1);
        }
        mLRUT1.put(storageDirId, t1);
        mLRUB1.put(storageDirId, b1);
        mLRUT2.put(storageDirId, t2);
        mLRUB2.put(storageDirId, b2);
        mTmpMovedBlocks.put(storageDirId, tmpBlocks);
        mLRUT1Bytes.put(storageDirId, t1Bytes);
        mLRUB1Bytes.put(storageDirId, 0L);
        mLRUT2Bytes.put(storageDirId, 0L);
        mLRUB2Bytes.put(storageDirId, 0L);
        mT1LimitBytes.put(storageDirId, (long) (totalBytes * mLeastLRUPercent));
        mTotalBytes.put(storageDirId, totalBytes);
        mBlockAccessTimes.put(storageDirId, blockAccessTimes);
      }
    }
  }

  @Override
  protected StorageDirView cascadingEvict(long bytesToBeAvailable, BlockStoreLocation location,
      EvictionPlan plan) {
    StorageDirView candidateDirView =
        EvictorUtils.getDirWithMaxFreeSpace(bytesToBeAvailable, location, mManagerView);
    if (candidateDirView != null) {
      if (candidateDirView.getAvailableBytes() >= bytesToBeAvailable) {
        return candidateDirView;
      }
    } else {
      return null;
    }

    // 2. Iterate over blocks in order until we find a StorageDirView that is in the range of
    // location and can satisfy bytesToBeAvailable after evicting its blocks iterated so far
    EvictionDirCandidates dirCandidates = new EvictionDirCandidates();
    iterateForCandidates(candidateDirView, dirCandidates, bytesToBeAvailable);

    // 3. If there is no eligible StorageDirView, return null
    if (dirCandidates.candidateSize() < bytesToBeAvailable) {
      return null;
    }

    // 4. cascading eviction: try to allocate space in the next tier to move candidate blocks
    // there. If allocation fails, the next tier will continue to evict its blocks to free space.
    // Blocks are only evicted from the last tier or it can not be moved to the next tier.
    candidateDirView = dirCandidates.candidateDir();
    List<Long> candidateBlocks = dirCandidates.candidateBlocks();
    StorageTierView nextTierView = mManagerView.getNextTier(candidateDirView.getParentTierView());
    if (nextTierView == null) {
      // This is the last tier, evict all the blocks.
      for (Long blockId : candidateBlocks) {
        try {
          BlockMeta block = mManagerView.getBlockMeta(blockId);
          if (block != null) {
            candidateDirView.markBlockMoveOut(blockId, block.getBlockSize());
            plan.toEvict()
                .add(
                    new Pair<Long, BlockStoreLocation>(blockId, candidateDirView
                        .toBlockStoreLocation()));
          }
        } catch (BlockDoesNotExistException nfe) {
          continue;
        }
      }
    } else {
      for (Long blockId : candidateBlocks) {
        try {
          BlockMeta block = mManagerView.getBlockMeta(blockId);
          if (block == null) {
            continue;
          }
          StorageDirView nextDirView =
              mAllocator.allocateBlockWithView(Sessions.MIGRATE_DATA_SESSION_ID,
                  block.getBlockSize(),
                  BlockStoreLocation.anyDirInTier(nextTierView.getTierViewAlias()), mManagerView);
          if (nextDirView == null) {
            nextDirView =
                cascadingEvict(block.getBlockSize(),
                    BlockStoreLocation.anyDirInTier(nextTierView.getTierViewAlias()), plan);
          }
          if (nextDirView == null) {
            // If we failed to find a dir in the next tier to move this block, evict it and
            // continue. Normally this should not happen.
            plan.toEvict().add(
                new Pair<Long, BlockStoreLocation>(blockId, block.getBlockLocation()));
            candidateDirView.markBlockMoveOut(blockId, block.getBlockSize());
            continue;
          }
          plan.toMove().add(
              new BlockTransferInfo(blockId, block.getBlockLocation(), nextDirView
                  .toBlockStoreLocation()));
          candidateDirView.markBlockMoveOut(blockId, block.getBlockSize());
          nextDirView.markBlockMoveIn(blockId, block.getBlockSize());
        } catch (BlockDoesNotExistException nfe) {
          continue;
        }
      }
    }

    return candidateDirView;
  }

  private void iterateForCandidates(StorageDirView dir, EvictionDirCandidates dirCandidates,
      long bytesToBeAvailable) {
    StorageTierView tierView = dir.getParentTierView();
    int alias = tierView.getTierViewAlias();
    int level = tierView.getTierViewLevel();
    int dirIndex = dir.getDirViewIndex();
    long storageDirId = StorageDirId.getStorageDirId(level, alias, dirIndex);
    long t1LimitBytes = mT1LimitBytes.get(storageDirId);
    long totalBytes = mTotalBytes.get(storageDirId);
    long t1Bytes = mLRUT1Bytes.get(storageDirId);
    long t2Bytes = mLRUT2Bytes.get(storageDirId);
    long b1Bytes = mLRUB1Bytes.get(storageDirId);
    long b2Bytes = mLRUB2Bytes.get(storageDirId);
    long leastLRUBytes = (long) (totalBytes * mLeastLRUPercent);
    long leastLFUBytes = (long) (totalBytes * mLeastLFUPercent);
    Map<Long, Boolean> t1 = mLRUT1.get(storageDirId);
    Map<Long, Boolean> t2 = mLRUT2.get(storageDirId);
    Iterator<Long> itOfT1 = new ArrayList<Long>(t1.keySet()).iterator();
    Iterator<Long> itOfT2 = new ArrayList<Long>(t2.keySet()).iterator();
    System.out.println("tier " + alias + " Total " + totalBytes + " limit bytes " + t1LimitBytes);
    System.out.println("T1: " + t1Bytes + "B1: " + b1Bytes + " T2: " + t2Bytes + "B2: " + b2Bytes);
    while (dirCandidates.candidateSize() < bytesToBeAvailable
        && (itOfT1.hasNext() || itOfT2.hasNext())) {
      long blockId;
      if (t1LimitBytes - t1Bytes <= (totalBytes - t1LimitBytes - t2Bytes) && itOfT1.hasNext()
          && t1Bytes >= leastLRUBytes || !itOfT2.hasNext() || t2Bytes <= leastLFUBytes) {
        blockId = itOfT1.next();
        t1Bytes -= mBlockSize.get(blockId);
      } else {
        blockId = itOfT2.next();
        t2Bytes -= mBlockSize.get(blockId);
      }
      try {
        BlockMeta block = mManagerView.getBlockMeta(blockId);
        if (block != null) { // might not present in this view
          dirCandidates.add(dir, blockId, block.getBlockSize());
        }
      } catch (BlockDoesNotExistException nfe) {
        LOG.warn("Remove block {} from evictor cache because {}", blockId, nfe);
        if (t1.containsKey(blockId)) {
          itOfT1.remove();
        } else {
          itOfT2.remove();
        }
        onRemoveBlockFromIterator(blockId);
      }
    }
  }

  @Override
  protected void onRemoveBlockFromIterator(long blockId) {
    for (StorageTierView tier : mManagerView.getTierViews()) {
      for (StorageDirView dir : tier.getDirViews()) {
        int alias = tier.getTierViewAlias();
        int level = tier.getTierViewLevel();
        int dirIndex = dir.getDirViewIndex();
        long storageDirId = StorageDirId.getStorageDirId(level, alias, dirIndex);
        Map<Long, Boolean> t1 = mLRUT1.get(storageDirId);
        Map<Long, Boolean> t2 = mLRUT2.get(storageDirId);
        if (t1.containsKey(blockId)) {
          t1.remove(blockId);
        }
        if (t2.containsKey(blockId)) {
          t2.remove(blockId);
        }
      }
    }
  }

  @Override
  protected Iterator<Long> getBlockIterator() {
    return null;
  }

  @Override
  public void onAccessBlock(long sessionId, long blockId) {
    System.out.println("access block " + blockId);
    System.out.println("Access increment");
    updateOnAccess(blockId);
  }

  @Override
  public void onCommitBlock(long sessionId, long blockId, BlockStoreLocation location) {
    System.out.println("commit block " + blockId);
    try {
      updateOnCommit(blockId, location);
    } catch (BlockDoesNotExistException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public void onMoveBlockByClient(long sessionId, long blockId, BlockStoreLocation oldLocation,
      BlockStoreLocation newLocation) {
    System.out.println("move block " + blockId + " from tier " + oldLocation.tierAlias()
        + " to tier " + newLocation.tierAlias());
    if (newLocation.tierAlias() < oldLocation.tierAlias()) {
      System.out.println("Miss increment");
    }
    updateOnMove(blockId, oldLocation, newLocation);
  }

  @Override
  public void onMoveBlockByWorker(long sessionId, long blockId, BlockStoreLocation oldLocation,
      BlockStoreLocation newLocation) {
    System.out.println("move block " + blockId + " from tier " + oldLocation.tierAlias()
        + " to tier " + newLocation.tierAlias());
    if (newLocation.tierAlias() < oldLocation.tierAlias()) {
      System.out.println("Miss increment");
    }
    updateOnMove(blockId, oldLocation, newLocation);
  }

  @Override
  public void onRemoveBlockByClient(long sessionId, long blockId) {
    System.out.println("remove block " + blockId);
    updateOnRemove(blockId);
  }

  @Override
  public void onRemoveBlockByWorker(long sessionId, long blockId) {
    System.out.println("remove block " + blockId);
    updateOnRemove(blockId);
  }

  private void updateTail() {
    for (StorageTierView tier : mManagerView.getTierViews()) {
      for (StorageDirView dir : tier.getDirViews()) {
        int alias = tier.getTierViewAlias();
        int level = tier.getTierViewLevel();
        int dirIndex = dir.getDirViewIndex();
        long storageDirId = StorageDirId.getStorageDirId(level, alias, dirIndex);
        long t1Bytes = mLRUT1Bytes.get(storageDirId);
        long t2Bytes = mLRUT2Bytes.get(storageDirId);
        long b1Bytes = mLRUB1Bytes.get(storageDirId);
        long b2Bytes = mLRUB2Bytes.get(storageDirId);
        long totalBytes = mTotalBytes.get(storageDirId);
        Map<Long, Boolean> b1 = mLRUB1.get(storageDirId);
        Map<Long, Boolean> b2 = mLRUB2.get(storageDirId);
        Map<Long, Integer> blockAccessTimes = mBlockAccessTimes.get(storageDirId);
        Iterator<Map.Entry<Long, Boolean>> it1 = b1.entrySet().iterator();
        Iterator<Map.Entry<Long, Boolean>> it2 = b2.entrySet().iterator();
        while (t1Bytes + b1Bytes > mHistoryBlocksTimes * totalBytes && it1.hasNext()) {
          long blockId = it1.next().getKey();
          long blocksize = mBlockSize.get(blockId);
          b1Bytes -= blocksize;
          it1.remove();
          blockAccessTimes.remove(blockId);
        }
        while (t2Bytes + b2Bytes > mHistoryBlocksTimes * totalBytes && it2.hasNext()) {
          long blockId = it2.next().getKey();
          long blocksize = mBlockSize.get(blockId);
          b2Bytes -= blocksize;
          it2.remove();
          blockAccessTimes.remove(blockId);
        }
        mLRUB1Bytes.put(storageDirId, b1Bytes);
        mLRUB2Bytes.put(storageDirId, b2Bytes);
      }
    }
  }

  private void updateOnAccess(long blockId) {
    for (StorageTierView tier : mManagerView.getTierViews()) {
      for (StorageDirView dir : tier.getDirViews()) {
        int alias = tier.getTierViewAlias();
        int level = tier.getTierViewLevel();
        int dirIndex = dir.getDirViewIndex();
        long storageDirId = StorageDirId.getStorageDirId(level, alias, dirIndex);
        long t1Bytes = mLRUT1Bytes.get(storageDirId);
        long t2Bytes = mLRUT2Bytes.get(storageDirId);
        long b1Bytes = mLRUB1Bytes.get(storageDirId);
        long b2Bytes = mLRUB2Bytes.get(storageDirId);
        long totalBytes = mTotalBytes.get(storageDirId);
        long t1LimitBytes = mT1LimitBytes.get(storageDirId);
        long blocksize = mBlockSize.get(blockId);
        long adjustSize;
        Map<Long, Boolean> t1 = mLRUT1.get(storageDirId);
        Map<Long, Boolean> t2 = mLRUT2.get(storageDirId);
        Map<Long, Boolean> b1 = mLRUB1.get(storageDirId);
        Map<Long, Boolean> b2 = mLRUB2.get(storageDirId);
        Set<Long> tmpBlocks = mTmpMovedBlocks.get(storageDirId);
        Map<Long, Integer> blockAccessTimes = mBlockAccessTimes.get(storageDirId);
        if (t1.containsKey(blockId)) {
          t1.remove(blockId);
          blockAccessTimes.put(blockId, blockAccessTimes.get(blockId) + 1);
          if (blockAccessTimes.get(blockId) >= mHotLimit) {
            t2.put(blockId, UNUSED_MAP_VALUE);
            t1Bytes -= blocksize;
            t2Bytes += blocksize;
          } else {
            t1.put(blockId, UNUSED_MAP_VALUE);
          }
        } else if (t2.containsKey(blockId)) {
          t2.put(blockId, UNUSED_MAP_VALUE);
        } else if (tmpBlocks.contains(blockId)) {
          tmpBlocks.remove(blockId);
          if (!blockAccessTimes.containsKey(blockId)) {
            blockAccessTimes.put(blockId, 1);
          } else {
            blockAccessTimes.put(blockId, blockAccessTimes.get(blockId) + 1);
          }
          if (b1.containsKey(blockId)) {
            adjustSize = (long)Math.max(1.0 * b2Bytes / b1Bytes, 1.0) * blocksize;
            if (blockAccessTimes.get(blockId) >= mHotLimit) {
              b1Bytes -= blocksize;
              t2Bytes += blocksize;
              t2.put(blockId, UNUSED_MAP_VALUE);
              t1LimitBytes =
                  Math.min(t1LimitBytes + adjustSize, totalBytes
                      - (long) (totalBytes * mLeastLFUPercent));
              System.out.println("access block " + blockId + " in B1, has been accessed "
                  + mHotLimit + " times");
            } else {
              b1Bytes -= blocksize;
              t1Bytes += blocksize;
              t1.put(blockId, UNUSED_MAP_VALUE);
              System.out.println("access block " + blockId + " in B1, has been accessed "
                  + blockAccessTimes.get(blockId) + " times");
            }
            b1.remove(blockId);
          } else if (b2.containsKey(blockId)) {
            adjustSize = (long)Math.max(1.0 * b1Bytes / b2Bytes, 1.0) * blocksize;
            b2Bytes -= blocksize;
            t2Bytes += blocksize;
            b2.remove(blockId);
            t2.put(blockId, UNUSED_MAP_VALUE);
            t1LimitBytes =
                Math.max(t1LimitBytes - adjustSize, (long) (totalBytes * mLeastLRUPercent));
            System.out.println("access block " + blockId + " in B2");
          } else {
            t1Bytes += blocksize;
            t1.put(blockId, UNUSED_MAP_VALUE);
          }
        }
        mLRUT1Bytes.put(storageDirId, t1Bytes);
        mLRUT2Bytes.put(storageDirId, t2Bytes);
        mLRUB1Bytes.put(storageDirId, b1Bytes);
        mLRUB2Bytes.put(storageDirId, b2Bytes);
        mT1LimitBytes.put(storageDirId, t1LimitBytes);
        updateTail();
      }
    }
  }

  private void updateOnCommit(long blockId, BlockStoreLocation location)
      throws BlockDoesNotExistException {
    for (StorageTierView tier : mManagerView.getTierViews()) {
      for (StorageDirView dir : tier.getDirViews()) {
        int alias = tier.getTierViewAlias();
        int level = tier.getTierViewLevel();
        int dirIndex = dir.getDirViewIndex();
        if (new BlockStoreLocation(alias, level, dirIndex).belongTo(location)) {
          long storageDirId = StorageDirId.getStorageDirId(level, alias, dirIndex);
          long t1Bytes = mLRUT1Bytes.get(storageDirId);
          long b1Bytes = mLRUB1Bytes.get(storageDirId);
          long totalBytes = mTotalBytes.get(storageDirId);
          long blocksize = mManagerView.getBlockMetaTmpMethod(blockId).getBlockSize();
          Map<Long, Boolean> t1 = mLRUT1.get(storageDirId);
          Map<Long, Integer> blockAccessTimes = mBlockAccessTimes.get(storageDirId);
          t1.put(blockId, UNUSED_MAP_VALUE);
          t1Bytes += blocksize;
          mLRUT1Bytes.put(storageDirId, t1Bytes);
          mBlockSize.put(blockId, blocksize);
          blockAccessTimes.put(blockId, 1);
          updateTail();
        }
      }
    }
  }

  private void updateOnMove(long blockId, BlockStoreLocation oldLocation,
      BlockStoreLocation newLocation) {
    if (newLocation.belongTo(oldLocation)) {
      return;
    }

    for (StorageTierView tier : mManagerView.getTierViews()) {
      for (StorageDirView dir : tier.getDirViews()) {
        int alias = tier.getTierViewAlias();
        int level = tier.getTierViewLevel();
        int dirIndex = dir.getDirViewIndex();
        long storageDirId = StorageDirId.getStorageDirId(level, alias, dirIndex);
        long t1Bytes = mLRUT1Bytes.get(storageDirId);
        long t2Bytes = mLRUT2Bytes.get(storageDirId);
        long b1Bytes = mLRUB1Bytes.get(storageDirId);
        long b2Bytes = mLRUB2Bytes.get(storageDirId);
        long totalBytes = mTotalBytes.get(storageDirId);
        long blocksize = mBlockSize.get(blockId);
        long t1LimitBytes = mT1LimitBytes.get(storageDirId);
        long bytesToReduce;
        long adjustSize;
        Map<Long, Boolean> t1 = mLRUT1.get(storageDirId);
        Map<Long, Boolean> t2 = mLRUT2.get(storageDirId);
        Map<Long, Boolean> b1 = mLRUB1.get(storageDirId);
        Map<Long, Boolean> b2 = mLRUB2.get(storageDirId);
        Set<Long> tmpBlocks = mTmpMovedBlocks.get(storageDirId);
        // Map<Long, Integer> blockAccessTimes = mBlockAccessTimes.get(blockId);
        BlockStoreLocation location = new BlockStoreLocation(alias, level, dirIndex);
        if (location.belongTo(oldLocation)) {
          if (t1.containsKey(blockId)) {
            t1Bytes -= blocksize;
            b1Bytes += blocksize;
            t1.remove(blockId);
            b1.put(blockId, UNUSED_MAP_VALUE);
          } else if (t2.containsKey(blockId)) {
            t2Bytes -= blocksize;
            b2Bytes += blocksize;
            t2.remove(blockId);
            b2.put(blockId, UNUSED_MAP_VALUE);
          } else if (tmpBlocks.contains(blockId)) {
            tmpBlocks.remove(blockId);
          }
        } else if (location.belongTo(newLocation)) {
          tmpBlocks.add(blockId);
        }
        mLRUT1Bytes.put(storageDirId, t1Bytes);
        mLRUT2Bytes.put(storageDirId, t2Bytes);
        mLRUB1Bytes.put(storageDirId, b1Bytes);
        mLRUB2Bytes.put(storageDirId, b2Bytes);
        updateTail();
      }
    }
  }

  private void updateOnRemove(long blockId) {
    for (StorageTierView tier : mManagerView.getTierViews()) {
      for (StorageDirView dir : tier.getDirViews()) {
        if (!mBlockSize.containsKey(blockId)) {
          continue;
        }
        int alias = tier.getTierViewAlias();
        int level = tier.getTierViewLevel();
        int dirIndex = dir.getDirViewIndex();
        long storageDirId = StorageDirId.getStorageDirId(level, alias, dirIndex);
        long t1Bytes = mLRUT1Bytes.get(storageDirId);
        long t2Bytes = mLRUT2Bytes.get(storageDirId);
        long b1Bytes = mLRUB1Bytes.get(storageDirId);
        long b2Bytes = mLRUB2Bytes.get(storageDirId);
        long blocksize = mBlockSize.get(blockId);
        Map<Long, Boolean> t1 = mLRUT1.get(storageDirId);
        Map<Long, Boolean> t2 = mLRUT2.get(storageDirId);
        Map<Long, Boolean> b1 = mLRUB1.get(storageDirId);
        Map<Long, Boolean> b2 = mLRUB2.get(storageDirId);
        Map<Long, Integer> blockAccessTimes = mBlockAccessTimes.get(storageDirId);
        Set<Long> tmpBlocks = mTmpMovedBlocks.get(storageDirId);
        if (t1.containsKey(blockId)) {
          t1Bytes -= blocksize;
          t1.remove(blockId);
        } else if (t2.containsKey(blockId)) {
          t2Bytes -= blocksize;
          t2.remove(blockId);
        } else if (b1.containsKey(blockId)) {
          b1Bytes -= blocksize;
          b1.remove(blockId);
        } else if (b2.containsKey(blockId)) {
          b2Bytes -= blocksize;
          b2.remove(blockId);
        }
        if (tmpBlocks.contains(blockId)) {
          tmpBlocks.remove(blockId);
        }
        if (blockAccessTimes.containsKey(blockId)) {
          blockAccessTimes.remove(blockId);
        }
        mLRUT1Bytes.put(storageDirId, t1Bytes);
        mLRUT2Bytes.put(storageDirId, t2Bytes);
        mLRUB1Bytes.put(storageDirId, b1Bytes);
        mLRUB2Bytes.put(storageDirId, b2Bytes);
        mBlockSize.remove(blockId);
      }
    }
  }

  @Override
  protected BlockStoreLocation updateBlockStoreLocation(long bytesToBeAvailable,
      BlockStoreLocation location) {
    StorageDirView candidateDirView =
        EvictorUtils.getDirWithMaxFreeSpace(bytesToBeAvailable, location, mManagerView);
    if (candidateDirView != null) {
      return new BlockStoreLocation(location.tierAlias(), location.tierLevel(),
          candidateDirView.getDirViewIndex());
    } else {
      return location;
    }
  }
}
