package alluxio.client.block.stream;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by dell on 2017/11/21.
 */
//
public class ClientCache {
   private static final int maxSize = 512 * 1024 * 1024;//512MB
   private static final int packetSize =  64 * 1024;//64KB

   //后面改成参数,写死不是太好，这个类本身是个单例，并且是饿汉模式
   private static ClientCache sClientCache = new ClientCache(100, 0.75f, true);

   //实际存储使用LRUCache
   private final LRUCache mCache;//这里直接不使用接口了

   private int size;// the total size of cache

  public static ClientCache getInstance() {
    return sClientCache;
  }
  private ClientCache(int initialCapacity, float loadFactor, boolean accessOrder) {
    this.mCache = new LRUCache(initialCapacity, loadFactor, accessOrder, maxSize);
  }

  //目前将long的长度直接转换为int
  public void add(Long blockId, Long offset, Long length, byte[] buffer) {
      ByteBuf buf = Unpooled.wrappedBuffer(buffer);
      //保存时直接保存当前cache的buffer和start，end
      mCache.put(blockId, new CacheUnit(offset, offset + length, buf));
      mCache.addCacheSize(length.intValue());
  }

  //只有3中情况返回的buffer不是null，在里面，在左边，在右边，所以上层接收到不是null后需要判断一下如何返回
  public CacheUnit get(Long blockId, Long offset, Long length) {
    CacheUnit cache =  mCache.get(blockId);
    if (cache == null) {//cache中没有
      return new CacheUnit(-1, -1, null);
    }
    long start = offset, end = offset + length;
    if (end < cache.getStart() || cache.getEnd() < start) {
      return new CacheUnit(-1, -1, null);//完全失效
    } else if (start >= cache.getStart() && end <= cache.getEnd()) {
      return new CacheUnit(start, end, cache.getBuffer());//使用-1并且buf不为空代表完全命中
    } else if (start < cache.getStart() && end > cache.getEnd()) {
      return new CacheUnit(-1, -1, null);//这会将一次顺序读写拆成2次随机读写，这里也当做未命中，可能需要根据百分比决定是不是使用缓存
    } else if (end > cache.getStart()) {//命中了右半部分,也就是当前buffer的左半部分
      return new CacheUnit(cache.getStart(), end, cache.getBuffer().slice(0, (int)(end - cache.getStart())));
    } else if (start < cache.getEnd()) {//命中了左半部分, 也就是当前buffer的右半部分
      return new CacheUnit(start, cache.getEnd(), cache.getBuffer().slice((int)(start - cache.getStart()), (int)(cache.getEnd() - start)));
    }

    throw new RuntimeException("Cache get cannot go here");
  }

  /*   private final long maxPacketSize;//将来使用变长的设置一个maxPacketSize

   private volatile boolean evictionInProgress;

   private static class EvictionThread implements Runnable {//直接从里面删除

     @Override
     public void run() {

     }
   }*/

  // corresponds to a part in a block
  //存的时候是buffer对应的blockId， start和end
  //取出去的时候就是命中的start和end部分
  public static class CacheUnit {
     private final long start;
     private final long end;
     private final ByteBuf buffer;

    public CacheUnit(long start, long end, ByteBuf buffer) {
      this.start = start;
      this.end = end;
      this.buffer = buffer;
    }

    public long getStart() {
      return start;
    }

    public long getEnd() {
      return end;
    }

    public ByteBuf getBuffer() {
      return buffer;
    }
  }

  private static class LRUCache extends LinkedHashMap<Long, CacheUnit> {
     private final int MAX_CACHE_SIZE;
     private int currentSize;
     public LRUCache(int initialCapacity, float loadFactor, boolean accessOrder,int cacheSize) {
       super(initialCapacity, loadFactor, accessOrder);
       MAX_CACHE_SIZE = cacheSize;
     }

     @Override
    protected boolean removeEldestEntry(Map.Entry<Long, CacheUnit> eldest) {
       boolean toRemove = size() > MAX_CACHE_SIZE;
       CacheUnit cacheUnitToRemove = eldest.getValue();
       if (toRemove) currentSize -= (cacheUnitToRemove.getEnd() - cacheUnitToRemove.getStart());
       return toRemove;
     }

     @Override
    public int size() {
       return currentSize;
     }

     public void addCacheSize(int toAddSize) {
       currentSize += toAddSize;
     }

  }
}
