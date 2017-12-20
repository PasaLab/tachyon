package alluxio.client.file;

import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.print.DocFlavor;

/**
 * Created by dell on 2017/12/19.
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V>
    implements SimpleCache<K, V>{
  private final int mMaxSize;

  private K mToRemove = null;
  public LRUCache(int initialCapacity, float loadFactor, boolean accessOrder, int maxSize) {
    super(initialCapacity, loadFactor, accessOrder);
    mMaxSize = maxSize;
  }


  //也就是在
  @Override
  protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
    boolean needRemoveEldest = size() > mMaxSize;
    if (needRemoveEldest) {
      mToRemove = eldest.getKey();
    } else {
      mToRemove = null;
    }
    return needRemoveEldest;
  }

  @Override
  public K add(K key, V value) {
    //如果包含key，此时不会调用removeEldestEntry
    if (containsKey(key)) mToRemove = null;
    super.put(key, value);
    return mToRemove;
  }
}
