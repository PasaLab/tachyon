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

import alluxio.exception.BlockDoesNotExistException;
import alluxio.worker.block.AbstractBlockStoreEventListener;
import alluxio.worker.block.BlockMetadataManagerView;
import alluxio.worker.block.BlockStoreLocation;
import alluxio.worker.block.allocator.Allocator;
import alluxio.worker.block.evictor.BlockTransferInfo;
import alluxio.worker.block.meta.BlockMeta;
import alluxio.worker.block.meta.StorageDirView;
import alluxio.worker.block.meta.StorageTierView;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provide the basic implementations of all promotes.
 */
public abstract class AbstractPromote extends AbstractBlockStoreEventListener implements Promote {
  protected BlockMetadataManagerView mManagerView;
  protected final Allocator mAllocator;

  /**
   * Create an instance of {@link AbstractPromote}.
   *
   * @param managerView a view of block metadata information
   * @param allocator an allocation policy
   */
  public AbstractPromote(BlockMetadataManagerView managerView, Allocator allocator) {
    mManagerView = Preconditions.checkNotNull(managerView);
    mAllocator = Preconditions.checkNotNull(allocator);
  }

  @Override
  public PromotePlan reorganizeBlocks(BlockMetadataManagerView managerView) {
    long st = System.currentTimeMillis();
    mManagerView = managerView;
    List<Long> blocks = getBlocksInDescendingOrder();
    long ed1 = System.currentTimeMillis();
    System.out.println("get descending blocks took " + (ed1 - st) + " ms.");
    List<BlockTransferInfo> toPromote = new ArrayList<>();
    int tiers = mManagerView.getTierViews().size();
    long[] totalBytesByTiers = new long[tiers];
    Map<Long, Long>[] bytesBeforeInTier = new Map[tiers];
    for (int i = 0; i < tiers; i++) {
      bytesBeforeInTier[i] = new HashMap<>();
    }
    for (long blockId : blocks) {
      try {
        BlockMeta blockMeta = mManagerView.getExistingBlockMeta(blockId);
        int tierOrd = blockMeta.getParentDir().getParentTier().getTierOrdinal();
        long blockSize = blockMeta.getBlockSize();
        totalBytesByTiers[tierOrd] += blockSize;
        for (int i = 0; i < tiers; i++) {
          bytesBeforeInTier[i].put(blockId, totalBytesByTiers[i]);
        }
      } catch (BlockDoesNotExistException e) {
        // TODO(shupeng) remove block
      }
    }
    for (StorageTierView tierView : mManagerView.getTierViews()) {
      int ord = tierView.getTierViewOrdinal();
      for (StorageDirView dir : tierView.getDirViews()) {
        totalBytesByTiers[ord] += dir.getAvailableBytes();
      }
    }
    // promote plan only move blocks from low tiers to higher tiers now.
    for (long blockId : blocks) {
      try {
        BlockMeta blockMeta = mManagerView.getBlockMeta(blockId);
        if (blockMeta != null) {
          int tierOrd = blockMeta.getParentDir().getParentTier().getTierOrdinal();
          long blockSize = blockMeta.getBlockSize();
          for (int i = 0; i < tierOrd; i++) {
            // Some blocks may be locked
            if (bytesBeforeInTier[i].get(blockId) <= totalBytesByTiers[i]) {
              // TODO(shupeng) find the dest storage dir.
              String tierAlias = mManagerView.getTierViews().get(i).getTierViewAlias();
              BlockStoreLocation dstLocation = BlockStoreLocation.anyDirInTier(tierAlias);
              toPromote
                      .add(new BlockTransferInfo(blockId, blockMeta.getBlockLocation(), dstLocation));
              totalBytesByTiers[i] -= blockSize;
              break;
            }
          }
        }
      } catch (BlockDoesNotExistException e) {
        // TODO(shupeng) remove block
      }
    }
    long ed = System.currentTimeMillis();
    System.out.println("reorganize took " + (ed - st) + " ms.");
    return new PromotePlan(toPromote);
  }

  /**
   * Sorting blocks in the descending order of their importance to users.
   *
   * @return the sorted list of blocks
   */
  protected abstract List<Long> getBlocksInDescendingOrder();
}
