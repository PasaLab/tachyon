package alluxio.client.block.stream;

import alluxio.network.protocol.databuffer.DataBuffer;

import com.google.common.base.Preconditions;

import java.nio.ByteBuffer;

/**
 * Created by dell on 2017/12/18.
 */
public final class CompositeDataBuffer implements DataBuffer {
  private final ByteBuffer [] mBuffers;

  /** Represents start offset in the first {@link ByteBuffer}.*/
  private final int mStart;
  /** Represents end offset in the last {@link ByteBuffer}.*/
  private final int mEnd;

  /** Total length in this DataBuffer.*/
  private final long mLength;

  public CompositeDataBuffer(ByteBuffer[] buffers, int start, int end, long length) {
    Preconditions.checkNotNull(buffers, "buffers can not be null");
    Preconditions.checkNotNull(buffers[0], "at least one buffer in the buffers");
    mBuffers = buffers;
    this.mStart = start;
    mBuffers[0].position(mStart);
    mBuffers[0].limit(mBuffers[0].capacity());
    //System.out.println("construct composite data buffer");
    //System.out.println("start:" + start + " end:" + end);
    /*System.out.println("first buffer position: " + mBuffers[0].position() + " limit:"
        + mBuffers[0].limit());*/
    if (mBuffers.length > 2) {
      for (int i = 1; i < mBuffers.length - 2; i++) {
        mBuffers[i].position(0);
        mBuffers[i].limit(buffers[i].capacity());
      }
    }
    this.mEnd = end;
    mBuffers[mBuffers.length - 1].limit(mEnd+1);//因为这个end是可以读到的，而limit代表是读不到的
    /*System.out.println("last buffer " + (mBuffers.length - 1) +" position:"
        + mBuffers[mBuffers.length - 1].position() + " limit:"
        + mBuffers[mBuffers.length - 1].limit());*/
    mLength = length;
  }

  //目前没有实现
  @Override
  public Object getNettyOutput() {
    throw new UnsupportedOperationException("CompositeByteBuffer not used in remote read now!");
  }

  //
  @Override
  public long getLength() {
    return mLength;
  }

  //目前没有实现
  @Override
  public ByteBuffer getReadOnlyByteBuffer() {
    throw new UnsupportedOperationException("CompositeByteBuffer not used in remote read now!");
  }

  @Override
  public void readBytes(byte[] dst, int dstIndex, int length) {
/*    if (length < (mBuffers[0].remaining())) {
      mBuffers[0].get(dst, dstIndex, length);
      return;//第一个可以写完的情况下直接返回
    } else {
      mBuffers[0].get(dst, dstIndex, mBuffers[0].remaining());
      dstIndex +=
    }*/
    int currentIndex = 0;
    while (length > mBuffers[currentIndex].remaining() && currentIndex < mBuffers.length) {
      mBuffers[currentIndex].get(dst, dstIndex, mBuffers[currentIndex].remaining());
      dstIndex += mBuffers[currentIndex].remaining();
      length -= mBuffers[currentIndex].remaining();
      currentIndex++;
    }
    if (currentIndex > mBuffers.length) {
      return;
    } else {
      mBuffers[currentIndex].get(dst, dstIndex, length);
    }
  }

  @Override
  public int readableBytes() {
    int length = 0;
    for (ByteBuffer buffer:mBuffers) {
      length += buffer.remaining();
    }
    return length;
  }

  //目前这里没写实现，因为这里每一个读取的buffer都会添加到ClientCache中，在添加进去的时候会有删除策略
  @Override
  public void release() {
 /*  for (ByteBuffer buffer: mBuffers) {
     buffer.position(0);
     buffer.limit(buffer.capacity());
   }*/
  }
}
