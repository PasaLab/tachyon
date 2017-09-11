package alluxio.worker.netty;

import alluxio.ClientPolicy;
import alluxio.network.protocol.RPCProtoMessage;
import alluxio.proto.dataserver.Protocol;
import alluxio.util.proto.ProtoMessage;
import alluxio.worker.block.BlockWorker;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ExecutorService;

public class PolicyHandler extends ChannelInboundHandlerAdapter {
  private final ExecutorService mRpcExecutor;
  private final BlockWorker mWorker;

  public PolicyHandler(ExecutorService service, BlockWorker blockWorker) {
    mRpcExecutor =service;
    mWorker = blockWorker;

  }
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    if (!(msg instanceof RPCProtoMessage)) {
      ctx.fireChannelRead(msg);
      return;
    }

    ProtoMessage message = ((RPCProtoMessage) msg).getMessage();
    if (message.isSetPolicyRequest()) {
      handleSetPolicyRequest(ctx, message.asSetPolicyRequest());
    }
    else {
      ctx.fireChannelRead(msg);
    }
  }

  private void handleSetPolicyRequest(ChannelHandlerContext ctx, final Protocol.SetPolicyRequest
      request) {
    mRpcExecutor.submit(new Runnable() {
      @Override
      public void run() {
        ClientPolicy policy = ClientPolicy.fromProto(request.getPolicyType());
        String user = request.getUser();
        mWorker.setPolicy(user, policy);

      }
    });


  }
}
