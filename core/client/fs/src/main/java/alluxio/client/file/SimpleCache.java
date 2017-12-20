package alluxio.client.file;

/**
 *  when add a new key value in the pool, if the returned key is null means no key value is removed
 *  from the pool, otherwise the returned key value is removed from the pool.
 */
public interface SimpleCache<K, V> {
  K add(K key, V value);
}
