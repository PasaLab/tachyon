package alluxio.master.block;

import alluxio.AbstractClient;
import alluxio.AbstractMasterClient;
import alluxio.AlluxioURI;
import alluxio.master.MasterClientConfig;
import alluxio.thrift.*;
import org.apache.thrift.TException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class BlockWorkerMaskerServiceClient extends AbstractClient {
  private BlockWorkerMasterService.Client mClient = null;

  public BlockWorkerMaskerServiceClient(InetSocketAddress address) {
    super(null, address);

  }

  public synchronized List<BlockIdToCValue> masterSample() throws IOException{
    return retryRPC(new RpcCallable<List<BlockIdToCValue>>() {
      @Override
      public List<BlockIdToCValue> call() throws TException {
        List<BlockIdToCValue> res = mClient.sample(new MtLRUSampleTOptions()).getBlockIdList();
        return res;
      }
    });
  }

  public synchronized long getCritValues(final long value) throws IOException {
    return retryRPC(new RpcCallable<Long>() {
      @Override
      public Long call() throws TException {
        return mClient.getCricValue(new MtLRUGetCricTOptions().setBlockId(value))
                   .getCritValue();
      }
    });
  }

  public synchronized int broadCast(final long value) throws IOException {
    return retryRPC(new RpcCallable<Integer>() {
      @Override
      public Integer call() throws TException {
        mClient.BroadCricValue(new MtLRUBroadCricTOptions(value));
        return 1;
      }
    });
  }

  @Override
  protected AlluxioService.Client getClient() {
    return mClient;
  }

  @Override
  protected long getServiceVersion() {
    return 0;
  }

  @Override
  protected String getServiceName() {
    return null;
  }

  @Override
  protected void afterConnect() {
    mClient = new BlockWorkerMasterService.Client(mProtocol);
  }
}
