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

package alluxio.worker.block.evictor;

import alluxio.worker.block.BlockMetadataManagerView;
import alluxio.worker.block.BlockStoreLocation;
import alluxio.worker.block.allocator.Allocator;
import alluxio.worker.block.meta.BlockMeta;
import alluxio.worker.block.meta.StorageDirView;
import alluxio.worker.block.meta.StorageTierView;
import com.google.common.base.Throwables;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.*;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * Implementation of an evictor which follows the least recently used algorithm. It discards the
 * least recently used item based on its access.
 */
@NotThreadSafe
public class LRUEvictor extends AbstractEvictor {
  private static final int LINKED_HASH_MAP_INIT_CAPACITY = 200;
  private static final float LINKED_HASH_MAP_INIT_LOAD_FACTOR = 0.75f;
  private static final boolean LINKED_HASH_MAP_ACCESS_ORDERED = true;
  private static final boolean UNUSED_MAP_VALUE = true;

  /**
   * Access-ordered {@link java.util.LinkedHashMap} from blockId to {@link #UNUSED_MAP_VALUE}(just a
   * placeholder to occupy the value), acts as a LRU double linked list where most recently accessed
   * element is put at the tail while least recently accessed element is put at the head.
   */
  protected Map<Long, Boolean> mLRUCache =
      Collections.synchronizedMap(new LinkedHashMap<Long, Boolean>(LINKED_HASH_MAP_INIT_CAPACITY,
          LINKED_HASH_MAP_INIT_LOAD_FACTOR, LINKED_HASH_MAP_ACCESS_ORDERED));

  protected Map<String, LinkedHashMap<Long, Boolean>> mLRUTierCache = new HashMap<>(3);


  /**
   * Creates a new instance of {@link LRUEvictor}.
   *
   * @param view a view of block metadata information
   * @param allocator an allocation policy
   */
  public LRUEvictor(BlockMetadataManagerView view, Allocator allocator) {
    super(view, allocator);

    // preload existing blocks loaded by StorageDir to Evictor
    for (StorageTierView tierView : mManagerView.getTierViews()) {

      String tier = tierView.getTierViewAlias();
      mLRUTierCache.put(tier, new LinkedHashMap<Long, Boolean>(LINKED_HASH_MAP_INIT_CAPACITY,
              LINKED_HASH_MAP_INIT_LOAD_FACTOR, LINKED_HASH_MAP_ACCESS_ORDERED));

      for (StorageDirView dirView : tierView.getDirViews()) {
        for (BlockMeta blockMeta : dirView.getEvictableBlocks()) { // all blocks with initial view
          mLRUCache.put(blockMeta.getBlockId(), UNUSED_MAP_VALUE);
          mLRUTierCache.get(tier).put(blockMeta.getBlockId(), UNUSED_MAP_VALUE);
        }
      }


    }
  }

  @Override
  protected Iterator<Long> getBlockIterator() {
    List<Long> blocks = new ArrayList<>(mLRUCache.keySet());
    return blocks.iterator();
  }

  @Override
  protected  Iterator<Long> getTierBlockIterator(String tier) {
    try {
      tierLock.readLock().lock();
      List<Long> blocks = new ArrayList<>(mLRUTierCache.get(tier).keySet());
      return blocks.iterator();
    } finally {
      tierLock.readLock().unlock();
    }

  }

  @Override
  public void onAccessBlock(long sessionId, long blockId) {
    mLRUCache.put(blockId, UNUSED_MAP_VALUE);
    try {
      tierLock.writeLock().lock();

      String tier = getTier(blockId);

      mLRUTierCache.get(tier).put(blockId, UNUSED_MAP_VALUE);
      if(tier.compareTo("MEM") == 0) {
        mMemHitNum ++;
      }
      else {
        mMemMissNum ++;
      }
    }catch (Exception e) {
      throw Throwables.propagate(e); // we shall never reach here
    }finally {
      tierLock.writeLock().unlock();
    }
  }

  @Override
  public void onCommitBlock(long sessionId, long blockId, BlockStoreLocation location) {
    // Since the temp block has been committed, update Evictor about the new added blocks
    mLRUCache.put(blockId, UNUSED_MAP_VALUE);
    try {
      tierLock.writeLock().lock();

      String tier = getTier(blockId);

      mLRUTierCache.get(tier).put(blockId, UNUSED_MAP_VALUE);

      Long temp = mTierSpace.getOrDefault(tier, new Long(0));
      mTierSpace.put(tier, temp + mManagerView.getBlockMeta(blockId).getBlockSize());

    } catch (Exception e) {
      throw Throwables.propagate(e); // we shall never reach here
    }finally {
      tierLock.writeLock().unlock();
    }
  }

  @Override
  public void onRemoveBlockByClient(long sessionId, long blockId) {

    try {
      tierLock.writeLock().lock();

      String tier = getTier(blockId);

      Long temp = mTierSpace.get(tier);
      mTierSpace.put(tier, temp - mManagerView.getBlockMeta(blockId).getBlockSize());

      mLRUTierCache.get(tier).remove(blockId);
    }catch (Exception e) {
      throw Throwables.propagate(e); // we shall never reach here
    } finally {
      tierLock.writeLock().unlock();
    }

    mLRUCache.remove(blockId);


  }

  @Override
  public void onRemoveBlockByWorker(long sessionId, long blockId) {

    try {
      tierLock.writeLock().lock();
      String tier = getTier(blockId);

      Long temp = mTierSpace.get(tier);
      mTierSpace.put(tier, temp - mManagerView.getBlockMeta(blockId).getBlockSize());

      mLRUTierCache.get(tier).remove(blockId);
    }catch (Exception e) {
      throw Throwables.propagate(e); // we shall never reach here
    } finally {
      tierLock.writeLock().unlock();
    }

    mLRUCache.remove(blockId);
  }

  @Override
  protected void onRemoveBlockFromIterator(long blockId) {

    try {
      tierLock.writeLock().lock();
      String tier = getTier(blockId);

      Long temp = mTierSpace.get(tier);
      mTierSpace.put(tier, temp - mManagerView.getBlockMeta(blockId).getBlockSize());

      mLRUTierCache.get(tier).remove(blockId);
    }catch (Exception e) {
      throw Throwables.propagate(e); // we shall never reach here
    } finally {
      tierLock.writeLock().unlock();
    }

    mLRUCache.remove(blockId);
  }

  @Override
  public int getBlocksNum(String tier) {
    try {
      tierLock.readLock().lock();

      return mLRUTierCache.get(tier).size();
    } finally {
      tierLock.readLock().unlock();
    }
  }

  @Override
  void moveBlock(long blockId, BlockStoreLocation oldLocation, BlockStoreLocation newLocation) {

    String oldTier = oldLocation.tierAlias();
    String newTier = newLocation.tierAlias();
    if(oldTier.compareTo(newTier)!= 0) {

        try {
          tierLock.writeLock().lock();

          Long temp = mTierSpace.get(oldTier);

          mLRUTierCache.get(oldTier).remove(blockId);
          mTierSpace.put(oldTier, temp - mManagerView.getBlockMeta(blockId).getBlockSize());
          temp = mTierSpace.getOrDefault(newTier,new Long(0));
          mLRUTierCache.get(newTier).put(blockId, UNUSED_MAP_VALUE);
          mTierSpace.put(oldTier, temp + mManagerView.getBlockMeta(blockId).getBlockSize());

        }catch (Exception e) {
          throw Throwables.propagate(e); // we shall never reach here
        } finally {
          tierLock.writeLock().unlock();
        }




    }
  }
}
