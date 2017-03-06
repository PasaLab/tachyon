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
import alluxio.Constants;
import alluxio.PropertyKey;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Promote blocks from lower tiers to higher tiers with window-LFU algorithm.
 */
public class WLFUPromote extends AbstractPromote {
  private static final Logger LOG = LoggerFactory.getLogger(Constants.LOGGER_TYPE);
  private LinkedList<Pair<Long, Long>> mWindowBlockList = new LinkedList<>();
  private Map<Long, Integer> mLFUCache = Collections.synchronizedMap(new HashMap<Long, Integer>());
  private long mActiveBytes = 0L;
  private long mWindowBytes = 0L;

  /**
   * Initialize an instance of WLFUPromote.
   *
   * @param managerView view of metadata
   * @param allocator instance of {@link Allocator}
   */
  public WLFUPromote(BlockMetadataManagerView managerView, Allocator allocator) {
    super(managerView, allocator);
    mWindowBytes = Configuration.getBytes(PropertyKey.WORKER_PROMOTE_WLFU_WINDOW_LENGTH);
    for (StorageTierView tierView : mManagerView.getTierViews()) {
      for (StorageDirView dirView : tierView.getDirViews()) {
        for (BlockMeta blockMeta : dirView.getEvictableBlocks()) {
          mLFUCache.put(blockMeta.getBlockId(), 0);
        }
      }
    }
  }

  @Override
  public List<Long> getBlocksInDescendingOrder() {
    List<Pair<Long, Integer>> list = new ArrayList<>();
    for (Map.Entry<Long, Integer> entry : mLFUCache.entrySet()) {
      list.add(new Pair<>(entry.getKey(), entry.getValue()));
    }
    for (Iterator<Pair<Long, Integer>> it = list.iterator(); it.hasNext();) {
      Pair<Long, Integer> pair = it.next();
      long blockId = pair.getFirst();
      try {
        BlockMeta blockMeta = mManagerView.getBlockMeta(blockId);
        if (blockMeta != null) {
          if (blockMeta.getParentDir().getParentTier().getTierOrdinal() == 0) {
            pair.setSecond(pair.getSecond() + 4);
          } else if (blockMeta.getParentDir().getParentTier().getTierOrdinal() == 1) {
            pair.setSecond(pair.getSecond() + 2);
          }
        }
      } catch (BlockDoesNotExistException e) {
        it.remove();
      }
    }
    Collections.sort(list, new Comparator<Pair<Long, Integer>>() {
      @Override
      public int compare(Pair<Long, Integer> o1, Pair<Long, Integer> o2) {
        return -Integer.compare(o1.getSecond(), o2.getSecond());
      }
    });
    List<Long> results = new ArrayList<>();
    for (Pair<Long, Integer> pair : list) {
      results.add(pair.getFirst());
    }
    return results;
  }

  @Override
  public void onAccessBlock(long sessionId, long blockId) {
    try {
      BlockMeta blockMeta = mManagerView.getExistingBlockMeta(blockId);
      mActiveBytes += blockMeta.getBlockSize();
      mLFUCache.put(blockId, mLFUCache.get(blockId) + 1);
      mWindowBlockList.add(new Pair<>(blockId, blockMeta.getBlockSize()));
      rollWindow();
    } catch (BlockDoesNotExistException e) {
      LOG.warn("Block {} has been removed.", blockId);
      for (Iterator<Pair<Long, Long>> iterator = mWindowBlockList.iterator(); iterator.hasNext();) {
        Pair<Long, Long> pair = iterator.next();
        if (pair.getFirst() == blockId) {
          mActiveBytes -= pair.getSecond();
          mLFUCache.put(pair.getFirst(), mLFUCache.get(pair.getFirst()) - 1);
          iterator.remove();
        }
      }
    }
  }

  @Override
  public void onCommitBlock(long sessionId, long blockId, BlockStoreLocation location) {
    try {
      BlockMeta blockMeta = mManagerView.getExistingBlockMeta(blockId);
      mActiveBytes += blockMeta.getBlockSize();
      mLFUCache.put(blockId, 1);
      mWindowBlockList.add(new Pair<>(blockId, blockMeta.getBlockSize()));
      rollWindow();
    } catch (BlockDoesNotExistException e) {
      LOG.warn("Failed to commit block {}, because it cannot be found.", blockId);
    }
  }

  @Override
  public void onRemoveBlockByClient(long sessionId, long blockId) {
    removeBlock(blockId);
  }

  @Override
  public void onRemoveBlockByWorker(long sessionId, long blockId) {
    removeBlock(blockId);
  }

  private void rollWindow() throws BlockDoesNotExistException {
    while (mActiveBytes > mWindowBytes) {
      long blockId = mWindowBlockList.removeFirst().getFirst();
      BlockMeta blockMeta = mManagerView.getExistingBlockMeta(blockId);
      mActiveBytes -= blockMeta.getBlockSize();
      mLFUCache.put(blockId, mLFUCache.get(blockId) - 1);
    }
  }

  private void removeBlock(long blockId) {
    for (Iterator<Pair<Long, Long>> iterator = mWindowBlockList.iterator(); iterator.hasNext();) {
      Pair<Long, Long> pair = iterator.next();
      if (pair.getFirst() == blockId) {
        mActiveBytes -= pair.getSecond();
        mLFUCache.remove(blockId);
        iterator.remove();
      }
    }
  }
}
