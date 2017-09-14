package alluxio.worker.block.evictor;

import alluxio.worker.block.BlockMetadataManagerView;
import alluxio.worker.block.allocator.Allocator;
import alluxio.worker.block.meta.BlockMeta;
import alluxio.worker.block.meta.StorageDirView;
import alluxio.worker.block.meta.StorageTierView;

import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

public class LocalLRUEvictor extends LRUEvictor implements LocalEvictor {
  private final String mUser;
  private static final boolean UNUSED_MAP_VALUE = true;
  private ReentrantLock lock;

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
    lock = new ReentrantLock();
  }

  @Override
  public void lock() {
    lock.lock();
  }

  @Override
  public void unLock() {
    lock.unlock();
  }

  @Override
  public Iterator<Long> getBlockIterator() {
    return super.getBlockIterator();
  }
}
