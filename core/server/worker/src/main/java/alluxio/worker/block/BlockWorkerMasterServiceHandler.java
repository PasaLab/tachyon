package alluxio.worker.block;

import alluxio.RpcUtils;
import alluxio.exception.AlluxioException;
import alluxio.thrift.*;
import alluxio.worker.block.evictor.MT_LRU;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BlockWorkerMasterServiceHandler implements BlockWorkerMasterService.Iface {
  private static final Logger LOG = LoggerFactory.getLogger(BlockWorkerMasterServiceHandler.class);

  public BlockWorkerMasterServiceHandler(BlockWorker worker) {}

  @Override
  public MtLRUSampleTResponse sample(MtLRUSampleTOptions options) throws AlluxioTException, TException {
    return RpcUtils.call(LOG, new RpcUtils.RpcCallable<MtLRUSampleTResponse>() {
      @Override
      public MtLRUSampleTResponse call() throws AlluxioException {
        List<BlockIdToCValue> res = MT_LRU.INSTANCE.masterSample();
        MtLRUSampleTResponse options = new MtLRUSampleTResponse().setBlockIdList(res);
        return options;
      }
    });
  }

  @Override
  public MtLRUGetCricTResponse getCricValue(MtLRUGetCricTOptions options) throws AlluxioTException, TException {
    return RpcUtils.call(LOG, new RpcUtils.RpcCallable<MtLRUGetCricTResponse>() {
      @Override
      public MtLRUGetCricTResponse call() throws AlluxioException {
        long blockId = options.getBlockId();
        long value = -1;
        if(MT_LRU.INSTANCE.mBlockToUsersMap.containsKey(blockId)){
          value = MT_LRU.INSTANCE.mBlockToShadowPrice.get(blockId);
        }
        return new MtLRUGetCricTResponse().setCritValue(value);;
      }
    });
  }

  @Override
  public MtLRUBroadCricTResponse BroadCricValue(MtLRUBroadCricTOptions options) throws AlluxioTException, TException {
    return RpcUtils.call(LOG, new RpcUtils.RpcCallable<MtLRUBroadCricTResponse>() {
      @Override
      public MtLRUBroadCricTResponse call() throws AlluxioException {
        Thread MT_LRUEvict = new Thread(MT_LRU.INSTANCE);
        MT_LRUEvict.start();
        return new MtLRUBroadCricTResponse();
      }
    });
  }

  @Override
  public GetServiceVersionTResponse getServiceVersion(GetServiceVersionTOptions options) throws AlluxioTException, TException {
    return null;
  }
}
