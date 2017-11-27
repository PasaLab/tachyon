package alluxio.worker.block;

import alluxio.underfs.UfsManager;
import alluxio.worker.Worker;
import alluxio.worker.WorkerFactory;
import alluxio.worker.WorkerRegistry;
import alluxio.worker.block.evictor.MT_LRU;

public class MTLRUFactory  implements WorkerFactory {
  public MTLRUFactory() {}

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public Worker create(WorkerRegistry registry, UfsManager ufsManager) {
    registry.add(MT_LRU.class, MT_LRU.INSTANCE);
    return MT_LRU.INSTANCE;
  }
}
