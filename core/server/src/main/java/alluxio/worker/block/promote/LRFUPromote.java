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

package alluxio.worker.block.promote;

import alluxio.Configuration;
import alluxio.PropertyKey;
import alluxio.collections.Pair;
import alluxio.exception.BlockDoesNotExistException;
import alluxio.worker.block.BlockMetadataManagerView;
import alluxio.worker.block.BlockStoreLocation;
import alluxio.worker.block.allocator.Allocator;
import alluxio.worker.block.meta.BlockMeta;
import alluxio.worker.block.meta.StorageDirView;
import alluxio.worker.block.meta.StorageTierView;

import com.google.common.base.Preconditions;
import io.netty.util.internal.chmv8.ConcurrentHashMapV8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This class evaluate the importance of blocks according to frequency. The frequency will decline
 * with the time passing.
 */
public class LRFUPromote extends AbstractPromote {
  // Map from block id to the last updated logic time count
  private final Map<Long, Long> mBlockIdToLastUpdateTime = new ConcurrentHashMapV8<>();
  // Map from block id to the CRF value of the block
  private final Map<Long, Float> mBlockIdToCRFValue = new ConcurrentHashMapV8<>();
  // In the range of [0, 1]. Closer to 0, LRFU closer to LFU. Closer to 1, LRFU closer to LRU
  private final float mStepFactor;
  // logic time count
  private AtomicLong mLogicTimeCount = new AtomicLong(0L);

  /**
   * Create an instance of {@link LRFUPromote}.
   *
   * @param managerView a view of block metadata information
   * @param allocator an allocation policy
   */
  public LRFUPromote(BlockMetadataManagerView managerView, Allocator allocator) {
    super(managerView, allocator);
    mStepFactor = Configuration.getFloat(PropertyKey.WORKER_PROMOTE_LRFU_STEP_FACTOR);
    Preconditions.checkArgument(mStepFactor >= 0.0 && mStepFactor <= 1);
    for (StorageTierView tier : mManagerView.getTierViews()) {
      for (StorageDirView dir : tier.getDirViews()) {
        for (BlockMeta block : dir.getEvictableBlocks()) {
          mBlockIdToLastUpdateTime.put(block.getBlockId(), 0L);
          mBlockIdToCRFValue.put(block.getBlockId(), 0.0f);
        }
      }
    }
  }

  @Override
  public List<Long> getBlocksInDescendingOrder() {
    updateCRFValue();
    List<Pair<Long, Float>> sortedCRF = new ArrayList<>();
    for (Map.Entry<Long, Float> entry : mBlockIdToCRFValue.entrySet()) {
      sortedCRF.add(new Pair<>(entry.getKey(), entry.getValue()));
    }
    for (Pair<Long, Float> pair : sortedCRF) {
      try {
        long blockId = pair.getFirst();
        BlockMeta blockMeta = mManagerView.getExistingBlockMeta(blockId);
        int tierOrd = blockMeta.getParentDir().getParentTier().getTierOrdinal();
        if (tierOrd == 0) {
          pair.setSecond(pair.getSecond() + 2.2f);
        } else if (tierOrd == 1) {
          pair.setSecond(pair.getSecond() + 1.1f);
        }
      } catch (BlockDoesNotExistException e) {
        // TODO(shupeng) remove block
      }
    }
    Collections.sort(sortedCRF, new Comparator<Pair<Long, Float>>() {
      @Override
      public int compare(Pair<Long, Float> o1, Pair<Long, Float> o2) {
        return -Float.compare(o1.getSecond(), o2.getSecond());
      }
    });
    List<Long> sortedBlocks = new ArrayList<>();
    for (Pair<Long, Float> pair : sortedCRF) {
      sortedBlocks.add(pair.getFirst());
    }
    return sortedBlocks;
  }

  private float calculateAccessWeight(long logicTimeInterval) {
    return (float) (Math.pow(0.5f, logicTimeInterval * mStepFactor));
  }

  @Override
  public void onAccessBlock(long sessionId, long blockId) {
    updateOnAccessAndCommit(blockId);
  }

  @Override
  public void onCommitBlock(long sessionId, long blockId, BlockStoreLocation location) {
    updateOnAccessAndCommit(blockId);
  }

  @Override
  public void onRemoveBlockByClient(long sessionId, long blockId) {
    updateOnRemoveBlock(blockId);
  }

  @Override
  public void onRemoveBlockByWorker(long sessionId, long blockId) {
    updateOnRemoveBlock(blockId);
  }

  private void updateCRFValue() {
    long currentLogicTime = mLogicTimeCount.get();
    for (Map.Entry<Long, Float> entry : mBlockIdToCRFValue.entrySet()) {
      long blockId = entry.getKey();
      float crfValue = entry.getValue();
      mBlockIdToCRFValue.put(blockId, crfValue
          * calculateAccessWeight(currentLogicTime - mBlockIdToLastUpdateTime.get(blockId)));
      mBlockIdToLastUpdateTime.put(blockId, currentLogicTime);
    }
  }

  private void updateOnAccessAndCommit(long blockId) {
    synchronized (mBlockIdToLastUpdateTime) {
      long currentLogicTime = mLogicTimeCount.incrementAndGet();
      if (mBlockIdToCRFValue.containsKey(blockId)) {
        mBlockIdToCRFValue.put(blockId,
            mBlockIdToCRFValue.get(blockId)
                * calculateAccessWeight(currentLogicTime - mBlockIdToLastUpdateTime.get(blockId))
                + 1.0f);
      } else {
        mBlockIdToCRFValue.put(blockId, 1.0f);
      }
      mBlockIdToLastUpdateTime.put(blockId, currentLogicTime);
    }
  }

  private void updateOnRemoveBlock(long blockId) {
    synchronized (mBlockIdToLastUpdateTime) {
      mLogicTimeCount.incrementAndGet();
      mBlockIdToCRFValue.remove(blockId);
      mBlockIdToLastUpdateTime.remove(blockId);
    }
  }
}
