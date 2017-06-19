/*
 * The Alluxio Open Foundation licenses this work under the Apache License, version 2.0
 * (the "License"). You may not use this work except in compliance with the License, which is
 * available at www.apache.org/licenses/LICENSE-2.0
 *
 * This software is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied, as more fully set forth in the License.
 *
 * See the NOTICE file distributed with this work for information regarding copyright ownership.
 */

package alluxio.worker.block.evictor;

import alluxio.Constants;
import alluxio.Sessions;
import alluxio.collections.Pair;
import alluxio.exception.BlockDoesNotExistException;
import alluxio.worker.block.BlockMetadataManagerView;
import alluxio.worker.block.BlockStoreLocation;
import alluxio.worker.block.allocator.Allocator;
import alluxio.worker.block.meta.BlockMeta;
import alluxio.worker.block.meta.StorageDirView;
import alluxio.worker.block.meta.StorageTierView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of an evictor which combines the feature of LRU and LFU.
 */
public class ARCEvictor extends AbstractEvictor {
  private static final Logger LOG = LoggerFactory.getLogger(Constants.LOGGER_TYPE);

  private static final int LINKED_HASH_MAP_INIT_CAPACITY = 200;
  private static final float LINKED_HASH_MAP_INIT_LOAD_FACTOR = 0.75f;
  private static final boolean LINKED_HASH_MAP_ACCESS_ORDERED = true;
  private static final boolean UNUSED_MAP_VALUE = true;

  /**
   * access-ordered {@link LinkedHashMap} from blockId to {@link #UNUSED_MAP_VALUE}(just a
   * placeholder to occupy the value), acts as a LRU double linked list where most recently accessed
   * element is put at the tail while least recently accessed element is put at the head.
   */
  private Map<BlockStoreLocation, Map<Long, Boolean>> mLRUT1 = new ConcurrentHashMap<>();
  private Map<BlockStoreLocation, Map<Long, Boolean>> mLRUB1 = new ConcurrentHashMap<>();
  private Map<BlockStoreLocation, Map<Long, Boolean>> mLRUT2 = new ConcurrentHashMap<>();
  private Map<BlockStoreLocation, Map<Long, Boolean>> mLRUB2 = new ConcurrentHashMap<>();

  // store blocks moved from other tiers before accessing
  // TODO(shupeng) when the temp blocks should be evicted
  // moved blocks have two kinds: from upper to lower and from lower to upper
  // the first is evict operation and the second is promote
  private Map<BlockStoreLocation, Set<Long>> mTmpMovedBlocks = new ConcurrentHashMap<>();

  private Map<BlockStoreLocation, Long> mLRUT1Bytes = new ConcurrentHashMap<>();
  private Map<BlockStoreLocation, Long> mLRUB1Bytes = new ConcurrentHashMap<>();
  private Map<BlockStoreLocation, Long> mLRUT2Bytes = new ConcurrentHashMap<>();
  private Map<BlockStoreLocation, Long> mLRUB2Bytes = new ConcurrentHashMap<>();

  private Map<BlockStoreLocation, Long> mTotalBytes = new ConcurrentHashMap<>();
  private Map<BlockStoreLocation, Long> mT1LimitBytes = new ConcurrentHashMap<>();

  private Map<Long, Long> mBlockSize = new ConcurrentHashMap<>();

  private double mHistoryBlocksTimes = 1.0;
  private double mLeastLRUPercent = 0.1;
  private double mLeastLFUPercent = 0.1;

  /**
   * @param view a view of block metadata information
   * @param allocator an allocation policy
   */
  public ARCEvictor(BlockMetadataManagerView view, Allocator allocator) {
    super(view, allocator);
    // preload existing blocks loaded by StorageDir to Evictor
    for (StorageTierView tier : view.getTierViews()) {
      for (StorageDirView dir : tier.getDirViews()) {
        String tierAlias = tier.getTierViewAlias();
        int tierLevel = tier.getTierViewOrdinal();
        int dirIndex = dir.getDirViewIndex();
        BlockStoreLocation location =
            new BlockStoreLocation(tier.getTierViewAlias(), dir.getDirViewIndex());
        long totalBytes = dir.getAvailableBytes() + dir.getEvitableBytes();
        long t1Bytes = 0;
        Map<Long, Integer> blockAccessTimes = new ConcurrentHashMap<>();
        Map<Long, Boolean> t1 = Collections
            .synchronizedMap(new LinkedHashMap<Long, Boolean>(LINKED_HASH_MAP_INIT_CAPACITY,
                LINKED_HASH_MAP_INIT_LOAD_FACTOR, LINKED_HASH_MAP_ACCESS_ORDERED));
        Map<Long, Boolean> b1 = Collections
            .synchronizedMap(new LinkedHashMap<Long, Boolean>(LINKED_HASH_MAP_INIT_CAPACITY,
                LINKED_HASH_MAP_INIT_LOAD_FACTOR, LINKED_HASH_MAP_ACCESS_ORDERED));
        Map<Long, Boolean> t2 = Collections
            .synchronizedMap(new LinkedHashMap<Long, Boolean>(LINKED_HASH_MAP_INIT_CAPACITY,
                LINKED_HASH_MAP_INIT_LOAD_FACTOR, LINKED_HASH_MAP_ACCESS_ORDERED));
        Map<Long, Boolean> b2 = Collections
            .synchronizedMap(new LinkedHashMap<Long, Boolean>(LINKED_HASH_MAP_INIT_CAPACITY,
                LINKED_HASH_MAP_INIT_LOAD_FACTOR, LINKED_HASH_MAP_ACCESS_ORDERED));
        Set<Long> tmpBlocks = new HashSet<>();
        for (BlockMeta blockMeta : dir.getEvictableBlocks()) { // all blocks with initial view
          long blockId = blockMeta.getBlockId();
          long blocksize = blockMeta.getBlockSize();
          t1.put(blockId, UNUSED_MAP_VALUE);
          t1Bytes += blocksize;
          mBlockSize.put(blockId, blocksize);
          blockAccessTimes.put(blockId, 1);
        }
        mLRUT1.put(location, t1);
        mLRUB1.put(location, b1);
        mLRUT2.put(location, t2);
        mLRUB2.put(location, b2);
        mTmpMovedBlocks.put(location, tmpBlocks);
        mLRUT1Bytes.put(location, t1Bytes);
        mLRUB1Bytes.put(location, 0L);
        mLRUT2Bytes.put(location, 0L);
        mLRUB2Bytes.put(location, 0L);
        mT1LimitBytes.put(location, (long) (totalBytes * mLeastLRUPercent));
        mTotalBytes.put(location, totalBytes);
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
            plan.toEvict().add(new Pair<>(blockId, candidateDirView.toBlockStoreLocation()));
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
          StorageDirView nextDirView = mAllocator.allocateBlockWithView(
              Sessions.MIGRATE_DATA_SESSION_ID, block.getBlockSize(),
              BlockStoreLocation.anyDirInTier(nextTierView.getTierViewAlias()), mManagerView);
          if (nextDirView == null) {
            nextDirView = cascadingEvict(block.getBlockSize(),
                BlockStoreLocation.anyDirInTier(nextTierView.getTierViewAlias()), plan);
          }
          if (nextDirView == null) {
            // If we failed to find a dir in the next tier to move this block, evict it and
            // continue. Normally this should not happen.
            plan.toEvict().add(new Pair<>(blockId, block.getBlockLocation()));
            candidateDirView.markBlockMoveOut(blockId, block.getBlockSize());
            continue;
          }
          plan.toMove().add(new BlockTransferInfo(blockId, block.getBlockLocation(),
              nextDirView.toBlockStoreLocation()));
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
    StorageTierView tier = dir.getParentTierView();
    BlockStoreLocation location =
        new BlockStoreLocation(dir.getParentTierView().getTierViewAlias(), dir.getDirViewIndex());
    long t1LimitBytes = mT1LimitBytes.get(location);
    long totalBytes = mTotalBytes.get(location);
    long t1Bytes = mLRUT1Bytes.get(location);
    long t2Bytes = mLRUT2Bytes.get(location);
    long b1Bytes = mLRUB1Bytes.get(location);
    long b2Bytes = mLRUB2Bytes.get(location);
    long leastLRUBytes = (long) (totalBytes * mLeastLRUPercent);
    long leastLFUBytes = (long) (totalBytes * mLeastLFUPercent);
    Map<Long, Boolean> t1 = mLRUT1.get(location);
    Map<Long, Boolean> t2 = mLRUT2.get(location);
    Iterator<Long> itOfT1 = new ArrayList<>(t1.keySet()).iterator();
    Iterator<Long> itOfT2 = new ArrayList<>(t2.keySet()).iterator();
    while (dirCandidates.candidateSize() < bytesToBeAvailable
        && (itOfT1.hasNext() || itOfT2.hasNext())) {
      long blockId;
      if ((t1LimitBytes - t1Bytes <= (totalBytes - t1LimitBytes - t2Bytes)
          && t1Bytes >= leastLRUBytes || !itOfT2.hasNext() || t2Bytes <= leastLFUBytes)
          && itOfT1.hasNext()) {
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
        BlockStoreLocation location =
            new BlockStoreLocation(tier.getTierViewAlias(), dir.getDirViewIndex());
        Map<Long, Boolean> t1 = mLRUT1.get(location);
        Map<Long, Boolean> t2 = mLRUT2.get(location);
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
    System.out.println("ARC access block " + blockId);
    updateOnAccess(blockId);
  }

  @Override
  public void onCommitBlock(long sessionId, long blockId, BlockStoreLocation location) {
    System.out.println("ARC commit block " + blockId);
    try {
      updateOnCommit(blockId, location);
    } catch (BlockDoesNotExistException e) {
      // TODO(shupeng) Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public void onMoveBlockByClient(long sessionId, long blockId, BlockStoreLocation oldLocation,
      BlockStoreLocation newLocation) {
    updateOnMove(blockId, oldLocation, newLocation);
  }

  @Override
  public void onMoveBlockByWorker(long sessionId, long blockId, BlockStoreLocation oldLocation,
      BlockStoreLocation newLocation) {
    updateOnMove(blockId, oldLocation, newLocation);
  }

  @Override
  public void onRemoveBlockByClient(long sessionId, long blockId) {
    System.out.println("ARC remove block " + blockId);
    updateOnRemove(blockId);
  }

  @Override
  public void onRemoveBlockByWorker(long sessionId, long blockId) {
    System.out.println("ARC remove block " + blockId);
    updateOnRemove(blockId);
  }

  private void updateTail() {
    for (StorageTierView tier : mManagerView.getTierViews()) {
      for (StorageDirView dir : tier.getDirViews()) {
        BlockStoreLocation location =
            new BlockStoreLocation(tier.getTierViewAlias(), dir.getDirViewIndex());
        long t1Bytes = mLRUT1Bytes.get(location);
        long t2Bytes = mLRUT2Bytes.get(location);
        long b1Bytes = mLRUB1Bytes.get(location);
        long b2Bytes = mLRUB2Bytes.get(location);
        long totalBytes = mTotalBytes.get(location);
        Map<Long, Boolean> b1 = mLRUB1.get(location);
        Map<Long, Boolean> b2 = mLRUB2.get(location);
        Iterator<Map.Entry<Long, Boolean>> it1 = b1.entrySet().iterator();
        Iterator<Map.Entry<Long, Boolean>> it2 = b2.entrySet().iterator();
        while (t1Bytes + b1Bytes > mHistoryBlocksTimes * totalBytes && it1.hasNext()) {
          long blockId = it1.next().getKey();
          long blocksize = mBlockSize.get(blockId);
          b1Bytes -= blocksize;
          it1.remove();
        }
        while (t2Bytes + b2Bytes > mHistoryBlocksTimes * totalBytes && it2.hasNext()) {
          long blockId = it2.next().getKey();
          try {
            long blockSize = mBlockSize.get(blockId);
          } catch (Exception e) {
            try {
              long blockSize = mManagerView.getBlockSize(blockId);
              mBlockSize.put(blockId, blockSize);
            } catch (BlockDoesNotExistException be) {
              LOG.warn("Failed to update size of block {}.", blockId);
            }
          }
          long blocksize = mBlockSize.get(blockId);
          b2Bytes -= blocksize;
          it2.remove();
        }
        mLRUB1Bytes.put(location, b1Bytes);
        mLRUB2Bytes.put(location, b2Bytes);
      }
    }
  }

  private void updateOnAccess(long blockId) {
    for (StorageTierView tier : mManagerView.getTierViews()) {
      for (StorageDirView dir : tier.getDirViews()) {
        BlockStoreLocation location =
            new BlockStoreLocation(tier.getTierViewAlias(), dir.getDirViewIndex());
        long t1Bytes = mLRUT1Bytes.get(location);
        long t2Bytes = mLRUT2Bytes.get(location);
        long b1Bytes = mLRUB1Bytes.get(location);
        long b2Bytes = mLRUB2Bytes.get(location);
        long totalBytes = mTotalBytes.get(location);
        long t1LimitBytes = mT1LimitBytes.get(location);
        try {
          long blockSize = mBlockSize.get(blockId);
        } catch (Exception e) {
          try {
            long blockSize = mManagerView.getBlockSize(blockId);
            mBlockSize.put(blockId, blockSize);
          } catch (BlockDoesNotExistException be) {
            LOG.warn("Failed to update size of block {}.", blockId);
          }
        }
        long blocksize = mBlockSize.get(blockId);
        long adjustSize;
        Map<Long, Boolean> t1 = mLRUT1.get(location);
        Map<Long, Boolean> t2 = mLRUT2.get(location);
        Map<Long, Boolean> b1 = mLRUB1.get(location);
        Map<Long, Boolean> b2 = mLRUB2.get(location);
        Set<Long> tmpBlocks = mTmpMovedBlocks.get(location);
        if (t1.containsKey(blockId)) {
          t1.remove(blockId);
          t2.put(blockId, UNUSED_MAP_VALUE);
          t1Bytes -= blocksize;
          t2Bytes += blocksize;
        } else if (t2.containsKey(blockId)) {
          t2.put(blockId, UNUSED_MAP_VALUE);
        } else if (tmpBlocks.contains(blockId)) {
          tmpBlocks.remove(blockId);
          if (b1.containsKey(blockId)) {
            adjustSize = (long) Math.max(1.0 * b2Bytes / b1Bytes, 1.0) * blocksize;
            b1Bytes -= blocksize;
            t2Bytes += blocksize;
            t2.put(blockId, UNUSED_MAP_VALUE);
            t1LimitBytes = Math.min(t1LimitBytes + adjustSize,
                totalBytes - (long) (totalBytes * mLeastLFUPercent));
            b1.remove(blockId);
          } else if (b2.containsKey(blockId)) {
            adjustSize = (long) Math.max(1.0 * b1Bytes / b2Bytes, 1.0) * blocksize;
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
        mLRUT1Bytes.put(location, t1Bytes);
        mLRUT2Bytes.put(location, t2Bytes);
        mLRUB1Bytes.put(location, b1Bytes);
        mLRUB2Bytes.put(location, b2Bytes);
        mT1LimitBytes.put(location, t1LimitBytes);
        updateTail();
      }
    }
  }

  private void updateOnCommit(long blockId, BlockStoreLocation location)
      throws BlockDoesNotExistException {
    for (StorageTierView tier : mManagerView.getTierViews()) {
      for (StorageDirView dir : tier.getDirViews()) {
        BlockStoreLocation newLocation =
            new BlockStoreLocation(tier.getTierViewAlias(), dir.getDirViewIndex());
        if (newLocation.belongsTo(location)) {
          long t1Bytes = mLRUT1Bytes.get(newLocation);
          long b1Bytes = mLRUB1Bytes.get(newLocation);
          long totalBytes = mTotalBytes.get(newLocation);
          long blocksize = mManagerView.getBlockSize(blockId);
          Map<Long, Boolean> t1 = mLRUT1.get(newLocation);
          t1.put(blockId, UNUSED_MAP_VALUE);
          t1Bytes += blocksize;
          mLRUT1Bytes.put(newLocation, t1Bytes);
          mBlockSize.put(blockId, blocksize);
          updateTail();
        }
      }
    }
  }

  private void updateOnMove(long blockId, BlockStoreLocation oldLocation,
      BlockStoreLocation newLocation) {
    if (newLocation.belongsTo(oldLocation)) {
      return;
    }

    for (StorageTierView tier : mManagerView.getTierViews()) {
      for (StorageDirView dir : tier.getDirViews()) {
        BlockStoreLocation location =
            new BlockStoreLocation(tier.getTierViewAlias(), dir.getDirViewIndex());
        long t1Bytes = mLRUT1Bytes.get(location);
        long t2Bytes = mLRUT2Bytes.get(location);
        long b1Bytes = mLRUB1Bytes.get(location);
        long b2Bytes = mLRUB2Bytes.get(location);
        long totalBytes = mTotalBytes.get(location);
        try {
          long blockSize = mBlockSize.get(blockId);
        } catch (Exception e) {
          try {
            long blockSize = mManagerView.getBlockSize(blockId);
            mBlockSize.put(blockId, blockSize);
          } catch (BlockDoesNotExistException be) {
            LOG.warn("Failed to update size of block {}.", blockId);
          }
        }
        long blocksize = mBlockSize.get(blockId);
        long t1LimitBytes = mT1LimitBytes.get(location);
        long bytesToReduce;
        long adjustSize;
        Map<Long, Boolean> t1 = mLRUT1.get(location);
        Map<Long, Boolean> t2 = mLRUT2.get(location);
        Map<Long, Boolean> b1 = mLRUB1.get(location);
        Map<Long, Boolean> b2 = mLRUB2.get(location);
        Set<Long> tmpBlocks = mTmpMovedBlocks.get(location);
        if (location.belongsTo(oldLocation)) {
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
        } else if (location.belongsTo(newLocation)) {
          tmpBlocks.add(blockId);
        }
        mLRUT1Bytes.put(location, t1Bytes);
        mLRUT2Bytes.put(location, t2Bytes);
        mLRUB1Bytes.put(location, b1Bytes);
        mLRUB2Bytes.put(location, b2Bytes);
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
        BlockStoreLocation location =
            new BlockStoreLocation(tier.getTierViewAlias(), dir.getDirViewIndex());
        long t1Bytes = mLRUT1Bytes.get(location);
        long t2Bytes = mLRUT2Bytes.get(location);
        long b1Bytes = mLRUB1Bytes.get(location);
        long b2Bytes = mLRUB2Bytes.get(location);
        try {
          long blockSize = mBlockSize.get(blockId);
        } catch (Exception e) {
          try {
            long blockSize = mManagerView.getBlockSize(blockId);
            mBlockSize.put(blockId, blockSize);
          } catch (BlockDoesNotExistException be) {
            LOG.warn("Failed to update size of block {}.", blockId);
          }
        }
        long blocksize = mBlockSize.get(blockId);
        Map<Long, Boolean> t1 = mLRUT1.get(location);
        Map<Long, Boolean> t2 = mLRUT2.get(location);
        Map<Long, Boolean> b1 = mLRUB1.get(location);
        Map<Long, Boolean> b2 = mLRUB2.get(location);
        Set<Long> tmpBlocks = mTmpMovedBlocks.get(location);
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
        mLRUT1Bytes.put(location, t1Bytes);
        mLRUT2Bytes.put(location, t2Bytes);
        mLRUB1Bytes.put(location, b1Bytes);
        mLRUB2Bytes.put(location, b2Bytes);
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
      return new BlockStoreLocation(location.tierAlias(), candidateDirView.getDirViewIndex());
    } else {
      return location;
    }
  }
}
