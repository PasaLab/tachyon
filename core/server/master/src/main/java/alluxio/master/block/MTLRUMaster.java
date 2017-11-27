package alluxio.master.block;

import alluxio.client.file.FileSystemMasterClient;
import alluxio.master.MasterClientConfig;
import alluxio.thrift.BlockIdToCValue;
import alluxio.wire.WorkerInfo;
import alluxio.wire.WorkerNetAddress;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;

public class MTLRUMaster implements Runnable {

  public final BlockMaster mBlockMaster;
  public final List<BlockWorkerMaskerServiceClient> mClientList = new ArrayList<>();
  public final int workerRPCPort = 29998;
  private long mSleepTimes;
  private LinkedBlockingQueue<List<BlockIdToCValue>> receivedQueue = new LinkedBlockingQueue<>();
  private List<BlockIdToCValue> mTempSampleRes = new ArrayList<>();
  private ExecutorService executor = Executors.newFixedThreadPool(2);
  private Condition mSampleFinish;
  private AtomicBoolean mAllCome;
  private double mFraction;



  public MTLRUMaster(BlockMaster blockMaster) {
    mBlockMaster = blockMaster;
  }

  private void createAllClients() {
    List<WorkerInfo> workerInfos = mBlockMaster.getWorkerInfoList();
    for(WorkerInfo info : workerInfos) {
      WorkerNetAddress addr = info.getAddress();
      InetSocketAddress address = new InetSocketAddress(addr.getHost(), workerRPCPort);
      BlockWorkerMaskerServiceClient client = new BlockWorkerMaskerServiceClient(address);
      mClientList.add(client);
    }
  }

  private void merge() throws Exception {
    receivedQueue.clear();
    mTempSampleRes.clear();
    mAllCome.getAndSet(false);
    executor.submit(new Runnable() {
      @Override
      public void run() {
        try {
          for (BlockWorkerMaskerServiceClient client : mClientList) {
            receivedQueue.put(client.masterSample());
          }
          mAllCome.getAndSet(true);
        } catch (Exception e) {
        }
      }
    });
    mSampleFinish.await();

  }



  @Override
  public void run() {
    createAllClients();
    executor.submit(new Runnable() {
      @Override
      public void run() {
        while(true) {
          try {
            List<BlockIdToCValue> tempList = receivedQueue.take();
            mTempSampleRes.addAll(tempList);
            Collections.sort(mTempSampleRes, new Comparator<BlockIdToCValue>() {
              @Override
              public int compare(BlockIdToCValue o1, BlockIdToCValue o2) {
                return (int)(o1.getCValue() - o2.getCValue());
              }
            });
            if(mAllCome.get()) {
              mSampleFinish.signal();
              mAllCome.getAndSet(false);
            }
          } catch (Exception e) {

          }
        }
      }
    });

    do {
      try {
        Thread.sleep(mSleepTimes);
        merge();
        int index =(int)(mTempSampleRes.size()*mFraction);
        long critValue = (long)mTempSampleRes.toArray()[index];
        for (BlockWorkerMaskerServiceClient client : mClientList) {
          client.broadCast(critValue);
        }
      } catch(Exception e) {

      }
    } while(true);
  }
}
