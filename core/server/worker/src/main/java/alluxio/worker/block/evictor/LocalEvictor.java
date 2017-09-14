package alluxio.worker.block.evictor;

import alluxio.ClientPolicy;
import alluxio.Configuration;
import alluxio.PropertyKey;
import alluxio.util.CommonUtils;
import alluxio.worker.block.BlockMetadataManagerView;
import alluxio.worker.block.allocator.Allocator;
import com.google.common.base.Throwables;

import java.util.Iterator;

public interface LocalEvictor {
  class Factory {
    public static LocalEvictor create(BlockMetadataManagerView view, Allocator allocator, ClientPolicy
        policy, String user) {
      try {
        if (policy.getNumber() == 0) {
          return new LocalLRUEvictor(view, allocator, user);

        }
        return null;

      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  void lock();

  void unLock();

  Iterator<Long> getBlockIterator();
}
