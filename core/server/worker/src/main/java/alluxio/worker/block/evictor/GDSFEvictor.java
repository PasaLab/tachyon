package alluxio.worker.block.evictor;

import alluxio.exception.BlockDoesNotExistException;
import alluxio.worker.block.AbstractBlockStoreEventListener;
import alluxio.worker.block.BlockMetadataManagerView;
import alluxio.worker.block.BlockStoreLocation;
import alluxio.worker.block.allocator.Allocator;
import alluxio.worker.block.meta.BlockMeta;
import com.google.common.base.Function;
import com.google.common.base.Throwables;
import com.google.common.collect.Iterators;
import io.netty.util.internal.chmv8.ConcurrentHashMapV8;

import java.util.*;

public class GDSFEvictor  extends AbstractEvictor  {

  protected Map<String, Map<Long, Double>> mGDSFTierCache = new HashMap<>(3);
  private final Map<Long, Double> mBlockIdToValue = new ConcurrentHashMapV8<>();
  private final Map<Long, Integer> mBlockAccessNum = new ConcurrentHashMapV8<>();

  private  double mLowest;
  private double L;

  public GDSFEvictor(BlockMetadataManagerView view, Allocator allocator) {
    super(view, allocator);

  }

  @Override
  protected Iterator<Long> getBlockIterator() {

    List<Map.Entry<Long, Double>> sortedCRF = new ArrayList<>(mBlockIdToValue.entrySet());
    Collections.sort(sortedCRF, new Comparator<Map.Entry<Long, Double>>() {
      @Override
      public int compare(Map.Entry<Long, Double> o1, Map.Entry<Long, Double> o2) {
        return Double.compare(o1.getValue(), o2.getValue());
      }
    });

    return Iterators.transform(sortedCRF.iterator(),
        new Function<Map.Entry<Long, Double>, Long>() {
          @Override
          public Long apply(Map.Entry<Long, Double> input) {
            return input.getKey();
          }
        });

  }

  @Override
  protected Iterator<Long> getTierBlockIterator(String tier) {
    try {
      tierLock.readLock().lock();
      List<Map.Entry<Long, Double>> sortedTier = new ArrayList<>(mGDSFTierCache.get(tier).entrySet());
      Collections.sort(sortedTier, new Comparator<Map.Entry<Long, Double>>() {
        @Override
        public int compare(Map.Entry<Long, Double> o1, Map.Entry<Long, Double> o2) {
          return Double.compare(o1.getValue(), o2.getValue());
        }
      });

      return Iterators.transform(sortedTier.iterator(),
          new Function<Map.Entry<Long, Double>, Long>() {
            @Override
            public Long apply(Map.Entry<Long, Double> input) {
              return input.getKey();
            }
          });
    } finally {
      tierLock.readLock().unlock();
    }
  }

  @Override
  protected void onRemoveBlockFromIterator(long blockId) {

    removerTierBlockInfo(blockId);
  }



  @Override
  public int getBlocksNum(String tier) {
    try {
      tierLock.readLock().lock();
      return mGDSFTierCache.get(tier).size();

    } finally {
      tierLock.readLock().unlock();
    }
  }

  @Override
  public void onAccessBlock(long sessionId, long blockId) {

    try {

      String tier = getTier(blockId);
      long size = mManagerView.getBlockMeta(blockId).getBlockSize();
      double value = 0;

      if(tier.compareTo("MEM") == 0) {
        mMemHitNum ++;
        value = mLowest +
                    Math.min(L, mBlockAccessNum.get(blockId)) *
                        penaltyFunc(blockId, tier, true) / size;
      }
      else {
        mMemMissNum ++;
        value = mLowest + penaltyFunc(blockId, tier, false) /size;
      }

      mGDSFTierCache.get(tier).put(blockId, value);
      mBlockIdToValue.put(blockId, value);
      mBlockAccessNum.put(blockId, mBlockAccessNum.get(blockId) + 1);

    }catch (Exception e) {
      throw Throwables.propagate(e); // we shall never reach here
    }


  }

  @Override
  public void onCommitBlock(long sessionId, long blockId, BlockStoreLocation location) {
    try {
      tierLock.writeLock().lock();

      String tier = getTier(blockId);
      long size = mManagerView.getBlockMeta(blockId).getBlockSize();

      double value;

      mMemMissNum ++;
      value = mLowest + penaltyFunc(blockId, tier, tier.compareTo("MEM") == 0 ) /size;

      mGDSFTierCache.get(tier).put(blockId, value);
      mBlockIdToValue.put(blockId, value);
      mBlockAccessNum.put(blockId, 1);

      Long temp = mTierSpace.getOrDefault(tier, new Long(0));
      mTierSpace.put(tier, temp + mManagerView.getBlockMeta(blockId).getBlockSize());

    }catch (Exception e) {
      throw Throwables.propagate(e); // we shall never reach here
    }finally {
      tierLock.writeLock().unlock();
    }
  }


  @Override
  public void onRemoveBlockByClient(long sessionId, long blockId) {
    removerTierBlockInfo(blockId);
  }

  @Override
  public void onRemoveBlockByWorker(long sessionId, long blockId) {
    removerTierBlockInfo(blockId);
  }

  private void removerTierBlockInfo(long blockId) {
    mBlockIdToValue.remove(blockId);

    try {
      tierLock.writeLock().lock();

      String tier = getTier(blockId);

      Long temp = mTierSpace.get(tier);
      mTierSpace.put(tier, temp - mManagerView.getBlockMeta(blockId).getBlockSize());

      mGDSFTierCache.get(tier).remove(blockId);
      mBlockAccessNum.remove(blockId);

    }catch (Exception e) {
      throw Throwables.propagate(e); // we shall never reach here
    }finally {
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
        mGDSFTierCache.get(oldTier).remove(blockId);
        mTierSpace.put(oldTier, temp - mManagerView.getBlockMeta(blockId).getBlockSize());
        temp = mTierSpace.getOrDefault(newTier,new Long(0));
        mGDSFTierCache.get(newTier).put(blockId, mBlockIdToValue.get(blockId));
        mTierSpace.put(oldTier, temp + mManagerView.getBlockMeta(blockId).getBlockSize());

      }catch (Exception e) {
        throw Throwables.propagate(e); // we shall never reach here
      }
      finally {
        tierLock.writeLock().unlock();
      }


    }

  }

  private double penaltyFunc(long blockId, String tier, boolean isHit) {
    //TODO(li) add penalty function.
    return 1;
  }
}
