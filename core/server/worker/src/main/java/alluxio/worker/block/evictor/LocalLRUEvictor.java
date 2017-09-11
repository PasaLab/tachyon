package alluxio.worker.block.evictor;

import alluxio.worker.block.BlockMetadataManagerView;
import alluxio.worker.block.allocator.Allocator;
import alluxio.worker.block.meta.BlockMeta;
import alluxio.worker.block.meta.StorageDirView;
import alluxio.worker.block.meta.StorageTierView;

public class LocalLRUEvictor extends LRUEvictor {
  private final String mUser;
  private static final boolean UNUSED_MAP_VALUE = true;

  public LocalLRUEvictor(BlockMetadataManagerView view, Allocator allocator, String user) {
    super(view, allocator);
    mUser = user;
    for (StorageTierView tierView : mManagerView.getTierViews()) {
      for (StorageDirView dirView : tierView.getDirViews()) {
        for (BlockMeta blockMeta : dirView.getEvictableBlocks()) { // all blocks with initial view
          if (mManagerView.isUserOwnBlock(mUser, blockMeta.getBlockId())) {
            mLRUCache.put(blockMeta.getBlockId(), UNUSED_MAP_VALUE);
          }
        }
      }
    }
  }


}
