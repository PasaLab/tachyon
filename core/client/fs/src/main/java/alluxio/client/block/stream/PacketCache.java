package alluxio.client.block.stream;

import alluxio.Configuration;
import alluxio.PropertyKey;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017/12/18.
 */
public class PacketCache {
  //这边先使用别人的8MB，后续可能自己调参数
  private final BlockCache mParent;
  private final long mBlockId;
  private final Map<Integer, ByteBuffer> mPool = new HashMap<>();

  public PacketCache(BlockCache parent, long blockId) {
    this.mParent = parent;
    mBlockId = blockId;
  }

  //不能直接add，因为需要先看一下空间够不够，并且把缓存踢出去
  public void add(int index, ByteBuffer buffer) {
    //when add a packet in a packet cache, transfer this message to your parent.
    mParent.acquireSlot(mBlockId, index);
    mPool.put(index, buffer);
  }

  public ByteBuffer getPacket(int index) {
    return mPool.get(index);
  }

  public void remove(int index) {
    mPool.remove(index);
  }

  public int size() {
    return mPool.size();
  }
}
