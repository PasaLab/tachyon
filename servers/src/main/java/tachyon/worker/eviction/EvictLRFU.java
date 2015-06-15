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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.HashMultimap;

import tachyon.Pair;
import tachyon.worker.tiered.BlockInfo;
import tachyon.worker.tiered.StorageDir;
import tachyon.worker.tiered.StorageTier;

/**
 * Used to evict old blocks among several StorageDirs by LRFU strategy.
 */
public final class EvictLRFU extends EvictLRFUBase {

  public EvictLRFU(boolean isLastTier, StorageTier storageTier) {
    super(isLastTier, storageTier);
  }

  @Override
  public synchronized Pair<StorageDir, List<BlockInfo>> getDirCandidate(StorageDir[] storageDirs,
      Set<Integer> pinList, long requestBytes) {
    onMiss();
    List<BlockInfo> blockInfoList = new ArrayList<BlockInfo>();
    Map<StorageDir, Pair<Long, Double>> dir2LRFUBlocks =
        new HashMap<StorageDir, Pair<Long, Double>>();
    HashMultimap<StorageDir, Long> dir2BlocksToEvict = HashMultimap.create();
    Map<StorageDir, Long> dir2SizeToEvict = new HashMap<StorageDir, Long>();
    // If no StorageDir has enough space for the request, continue; if no block can be evicted,
    // return null; and if eviction size plus free space of some StorageDir is larger than request
    // size, return the Pair of StorageDir and blockInfoList.
    // TODO Remove while(true). It is in general bad to have the while-loop on true.
    cleanEvictingBlockIds();
    while (true) {
      // Get oldest block in StorageDir candidates
      Pair<StorageDir, Long> candidate =
          getLRFUBlockCandidate(storageDirs, dir2LRFUBlocks, dir2BlocksToEvict, pinList);
      StorageDir dir = candidate.getFirst();
      if (dir == null) {
        return null;
      }
      long blockId = candidate.getSecond();
      long blockSize = dir.getBlockSize(blockId);
      long evictBytes = dir2SizeToEvict.containsKey(dir) ? dir2SizeToEvict.get(dir) : 0L;
      if (blockSize != -1) {
        // Add info of the block to the list
        blockInfoList.add(new BlockInfo(dir, blockId, blockSize));
        dir2BlocksToEvict.put(dir, blockId);
        evictBytes += blockSize;
        dir2SizeToEvict.put(dir, evictBytes);
      }
      dir2LRFUBlocks.remove(dir);
      if (evictBytes + dir.getAvailableBytes() >= requestBytes) {
        updateEvictingBlockIds(blockInfoList);
        return new Pair<StorageDir, List<BlockInfo>>(dir, blockInfoList);
      }
    }
  }

  /**
   * Get a block to evict by choosing the block with minimum CRF in StorageDir candidates
   *
   * @param storageDirs StorageDir candidates that the space will be allocated in
   * @param dir2LRFUBlocks the minimum CRF information of each StorageDir
   * @param dir2BlocksToEvict Ids of blocks that have been selected to be evicted
   * @param pinList list of pinned files
   * @return pair of StorageDir that contains the block to be evicted and Id of the block
   */
  private Pair<StorageDir, Long> getLRFUBlockCandidate(StorageDir[] storageDirs,
      Map<StorageDir, Pair<Long, Double>> dir2LRFUBlocks,
      HashMultimap<StorageDir, Long> dir2BlocksToEvict, Set<Integer> pinList) {
    StorageDir dirCandidate = null;
    long blockId = -1;
    double minCRF = Double.MAX_VALUE;
    for (StorageDir dir : storageDirs) {
      Pair<Long, Double> lrfuBlock;
      if (dir2LRFUBlocks.containsKey(dir)) {
        lrfuBlock = dir2LRFUBlocks.get(dir);
      } else {
        Set<Long> blocksToEvict = dir2BlocksToEvict.get(dir);
        lrfuBlock = getLRFUBlock(dir, blocksToEvict, pinList);
        if (lrfuBlock.getFirst() == -1) {
          continue;
        }
        dir2LRFUBlocks.put(dir, lrfuBlock);
      }
      if (lrfuBlock.getSecond() < minCRF) {
        blockId = lrfuBlock.getFirst();
        minCRF = lrfuBlock.getSecond();
        dirCandidate = dir;
      }
    }
    return new Pair<StorageDir, Long>(dirCandidate, blockId);
  }
}
