package alluxio.client.block.stream;

import alluxio.Configuration;
import alluxio.PropertyKey;
import alluxio.client.file.FileCache;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class BlockCache {

  private final FileCache mParent;
  private final long mFileId;
  public final Map<Long, PacketCache> mPool = new HashMap<>();

  public BlockCache(FileCache parent, long fileId) {
    mParent = parent;
    mFileId = fileId;
  }

  public void add(long blockId, PacketCache packetCache) {
    mPool.put(blockId, packetCache);
  }

  public PacketCache getPacketCache(long blockId) {
    PacketCache packetCache = mPool.get(blockId);
    if (packetCache == null) {
      packetCache = new PacketCache(this, blockId);
      mPool.put(blockId, packetCache);
    }
    return packetCache;
  }
  public void remove(long blockId) {
    mPool.remove(blockId);
  }

  public long size() {
    int totalPackets = 0;
    for (PacketCache packetCache: mPool.values()) {
      totalPackets += packetCache.size();
    }
    return totalPackets;
  }

  public void acquireSlot(long blockId, int packetIndex) {
    mParent.acquireSlot(mFileId, blockId, packetIndex);
  }
}
