package alluxio.master.block;

import alluxio.client.file.FileSystemMasterClient;
import alluxio.master.MasterClientConfig;
import alluxio.wire.WorkerInfo;
import alluxio.wire.WorkerNetAddress;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class MTLRUMaster implements Runnable {

  public final BlockMaster mBlockMaster;
  public final List<BlockWorkerMaskerServiceClient> mClientList = new ArrayList<>();
  public final int workerRPCPort = 29997;

  public MTLRUMaster(BlockMaster blockMaster) {
    mBlockMaster = blockMaster;
  }
  public void createAllClients() {
    List<WorkerInfo> workerInfos = mBlockMaster.getWorkerInfoList();
    for(WorkerInfo info : workerInfos) {
      WorkerNetAddress addr = info.getAddress();
      InetSocketAddress address = new InetSocketAddress(addr.getHost(), workerRPCPort);
      BlockWorkerMaskerServiceClient client = new BlockWorkerMaskerServiceClient(address);
      mClientList.add(client);
    }
  }

  @Override
  public void run() {

  }
}
