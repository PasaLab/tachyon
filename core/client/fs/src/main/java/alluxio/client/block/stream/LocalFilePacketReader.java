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

package alluxio.client.block.stream;

import alluxio.Configuration;
import alluxio.PropertyKey;
import alluxio.client.file.FileCache;
import alluxio.client.file.FileSystemContext;
import alluxio.client.file.options.InStreamOptions;
import alluxio.client.netty.NettyRPC;
import alluxio.client.netty.NettyRPCContext;
import alluxio.network.protocol.databuffer.DataBuffer;
import alluxio.network.protocol.databuffer.DataByteBuffer;
import alluxio.proto.dataserver.Protocol;
import alluxio.util.proto.ProtoMessage;
import alluxio.wire.WorkerNetAddress;
import alluxio.worker.block.io.LocalFileBlockReader;

import com.google.common.base.Preconditions;
import io.netty.channel.Channel;

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * A packet reader that simply reads packets from a local file.
 */
@NotThreadSafe
public final class LocalFilePacketReader implements PacketReader {
  /** The file reader to read a local block. */
  private final LocalFileBlockReader mReader;
  private final PacketCache mPacketCache;
  private long mPos;
  private final long mEnd;
  private final long mPacketSize;

  private boolean mClosed;

  /**
   * Creates an instance of {@link LocalFilePacketReader}.
   *
   * @param path the block path
   * @param offset the offset
   * @param len the length to read
   * @param packetSize the packet size
   */
  private LocalFilePacketReader(String path, long offset, long len, long packetSize
      ,PacketCache packetCache)
      throws IOException {
        mReader = new LocalFileBlockReader(path);
    Preconditions.checkArgument(packetSize > 0);
    mPos = offset;
    mEnd = Math.min(mReader.getLength(), offset + len);
    mPacketSize = packetSize;//所以这个变量是没有用的，或者使用这个变量，而不要使用自己的常量
    mPacketCache = packetCache;
  }

  //实际改变了readPacket的语义，因为一次调用就把整个需要的都读完了
  @Override
  public DataBuffer readPacket() throws IOException {//这些都是设置的64KB的buffer，涉及到buffer的拼接问题
    if (mPos >= mEnd) {
      return null;
    }
    //System.out.println("read packet calling: mPos" + mPos + " mEnd:" + mEnd);
    long readLength = Math.min(mPacketSize, mEnd - mPos);
    //System.out.println("readLength:" + readLength);
    long end = mPos + readLength;
    int startIndex = (int)(mPos/ FileCache.PACKET_SIZE);
    int endIndex = (int)((end - 1)/FileCache.PACKET_SIZE);//防止缓存过多的packet
    ByteBuffer [] buffers = new ByteBuffer[endIndex - startIndex + 1];
    for (int i = startIndex; i <= endIndex; i++) {
      buffers[i - startIndex] = mPacketCache.getPacket(i);
    }
    for (int i = startIndex; i <= endIndex ; i++) {
      int bufferIndex = i - startIndex;
      if (buffers[bufferIndex] == null) {
        long currentOffset = i * FileCache.PACKET_SIZE;
        buffers[bufferIndex] = mReader.read(currentOffset
            , Math.min(FileCache.PACKET_SIZE, mEnd - currentOffset));
        mPacketCache.add(i, buffers[bufferIndex]);
      }
    }
    int startOffset = (int)(mPos % FileCache.PACKET_SIZE);
    int endOffset = (int)((end-1) % FileCache.PACKET_SIZE);
    DataBuffer dataBuffer = new CompositeDataBuffer(buffers, startOffset, endOffset, end - mPos);
    mPos += dataBuffer.getLength();
    return dataBuffer;
  }

  @Override
  public long pos() {
    return mPos;
  }

  @Override
  public void close() throws IOException {
    if (mClosed) {
      return;
    }
    mClosed = true;
    mReader.close();
  }

  /**
   * Factory class to create {@link LocalFilePacketReader}s.
   */
  public static class Factory implements PacketReader.Factory {
    private static final long READ_TIMEOUT_MS =
        Configuration.getMs(PropertyKey.USER_NETWORK_NETTY_TIMEOUT_MS);

    private final FileSystemContext mContext;
    private final WorkerNetAddress mAddress;
    private final Channel mChannel;
    private final long mBlockId;
    private final String mPath;
    private final long mPacketSize;
    private final PacketCache mPacketCache;
    private boolean mClosed;

    /**
     * Creates an instance of {@link Factory}.
     *
     * @param context the file system context
     * @param address the worker address
     * @param blockId the block ID
     * @param packetSize the packet size
     * @param options the instream options
     */
    public Factory(FileSystemContext context, WorkerNetAddress address, long blockId,
        long packetSize, InStreamOptions options, PacketCache packetCache) throws IOException {
      mContext = context;
      mAddress = address;
      mBlockId = blockId;
      mPacketSize = packetSize;
      mPacketCache = packetCache;
      mChannel = context.acquireNettyChannel(address);
      Protocol.LocalBlockOpenRequest request =
          Protocol.LocalBlockOpenRequest.newBuilder().setBlockId(mBlockId)
              .setPromote(options.getAlluxioStorageType().isPromote()).build();
      try {
        ProtoMessage message = NettyRPC
            .call(NettyRPCContext.defaults().setChannel(mChannel).setTimeout(READ_TIMEOUT_MS),
                new ProtoMessage(request));
        Preconditions.checkState(message.isLocalBlockOpenResponse());
        mPath = message.asLocalBlockOpenResponse().getPath();
      } catch (Exception e) {
        context.releaseNettyChannel(address, mChannel);
        throw e;
      }
    }

    @Override
    public PacketReader create(long offset, long len) throws IOException {
      return new LocalFilePacketReader(mPath, offset, len, mPacketSize, mPacketCache);
    }

    @Override
    public boolean isShortCircuit() {
      return true;
    }

    @Override
    public void close() throws IOException {
      if (mClosed) {
        return;
      }
      Protocol.LocalBlockCloseRequest request =
          Protocol.LocalBlockCloseRequest.newBuilder().setBlockId(mBlockId).build();
      try {
        NettyRPC.call(NettyRPCContext.defaults().setChannel(mChannel).setTimeout(READ_TIMEOUT_MS),
            new ProtoMessage(request));
      } finally {
        mClosed = true;
        mContext.releaseNettyChannel(mAddress, mChannel);
      }
    }
  }
}

