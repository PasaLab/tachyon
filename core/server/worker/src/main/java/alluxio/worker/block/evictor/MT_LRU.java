package alluxio.worker.block.evictor;


import alluxio.Sessions;
import alluxio.collections.Pair;
import alluxio.exception.BlockDoesNotExistException;
import alluxio.worker.block.BlockMetadataManager;
import alluxio.worker.block.BlockMetadataManagerView;
import alluxio.worker.block.BlockStoreLocation;
import alluxio.worker.block.TieredBlockStore;
import alluxio.worker.block.meta.BlockMeta;
import alluxio.worker.block.meta.StorageDirView;
import alluxio.worker.block.meta.StorageTierView;
import com.google.common.base.Throwables;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.*;


public enum MT_LRU implements Runnable{
  INSTANCE;
  //TODO consider block size
  private final ConcurrentHashMap<String, AbstractEvictor> mUserEvictors = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<String, TieredBlockStore> mMockStorage = new ConcurrentHashMap<>();
  public final HashMap<Long, List<String>> mBlockToUsersMap = new HashMap<>();
  public ExecutorService executorService = Executors.newFixedThreadPool(4);

  BlockMetadataManager metadataManager;
  private TreeMap<Long, Long> mBlockToShadowPrice = new TreeMap<>(new Comparator<Long>() {
    @Override
    public int compare(Long o1, Long o2) {
      return mBlockToShadowPrice.get(o1).compareTo(mBlockToShadowPrice.get(o2));
    }
  });

  private Boolean mIsSample;

  private List<Long> mVirtualCosts = new ArrayList<>();

  private long mAVC = 0;
  private int mCurrentTime;
  private int mSampleNum;
  private int mFraction;
  private long mSleepTimes;

  public void addUserEvictorInfo(String user, AbstractEvictor evictor) {
    mUserEvictors.putIfAbsent(user, evictor);
  }

  public AbstractEvictor getUserEvictor(String user) {
    return mUserEvictors.get(user);
  }

  public void addMockStorage(String user) {

    TieredBlockStore tieredBlockStore = new TieredBlockStore();
    //TODO set evictor
    tieredBlockStore.isMock = true;
    mMockStorage.putIfAbsent(user, tieredBlockStore);

  }

  public TieredBlockStore getMockBlockStore(String user) {
    return mMockStorage.get(user);
  }

  public void addBlockUserInfo(Long blockid, String user) {
    synchronized(mBlockToUsersMap) {
      mBlockToUsersMap.putIfAbsent(blockid, new ArrayList<>());
      mBlockToUsersMap.get(blockid).add(user);
    }
  }
  @Override
  public void run() {
    //update shadow price of every block
    for(Map.Entry entry : mUserEvictors.entrySet()) {
      getUserBlock(entry.getKey().toString(), (AbstractEvictor)entry.getValue());
    }
    //
    Long cricP, cricBlock;
    if(mIsSample) {
      Integer [] indexArray = Sample();
      cricBlock = (Long)mBlockToShadowPrice.keySet().toArray()[indexArray[mFraction]];

    }
    else{
      cricBlock = (Long)mBlockToShadowPrice.keySet().toArray()[mFraction];
    }
    cricP = mBlockToShadowPrice.get(cricBlock);
    for(Map.Entry entry : mUserEvictors.entrySet()) {
      removeBlock(cricP, (AbstractEvictor)entry.getValue());
    }
    mVirtualCosts.add(cricP);
    mAVC += cricP;
    try {
      Thread.sleep(mSleepTimes);
    } catch (InterruptedException e) {
      throw Throwables.propagate(e); // we shall never reach here
    }

  }

  public void getUserBlock(String user, AbstractEvictor evictor) {
    Iterator<Long> userIter = evictor.getTierBlockIterator("MEM");
    int size = evictor.getBlocksNum("MEM");
    int pos = 0;
    while (userIter.hasNext()) {
      Long blockId = userIter.next();
      pos ++;
      Long shadowPrice = UserHRDForm(user) - mAVC + mAVC * pos / ( size-1 );
      mBlockToShadowPrice.put(blockId, shadowPrice);
    }
  }

  public void removeBlock(Long crip, AbstractEvictor evictor) {
    List<Long> removeBlock = new ArrayList<>();
    Iterator<Long> userIter = evictor.getTierBlockIterator("MEM");
    Long last = new Long(0);
    while (userIter.hasNext()) {
      Long blockId = userIter.next();
      Long price = mBlockToShadowPrice.get(blockId);
      if( price < crip ){
        removeBlock.add(blockId);
        last = price;
      }
      else {
        if((price - crip) <= (crip - last)) {
          removeBlock.add(blockId);
        }
        break;
      }
    }
    removeBlock0(removeBlock, evictor);
  }

  public void removeBlock0(List<Long> removeBlocks, AbstractEvictor evictor) {
    BlockMetadataManagerView mManagerView = evictor.mTieredBlockStore.getUpdatedView();

    List<BlockTransferInfo> toMove = new ArrayList<>();
    List<Pair<Long, BlockStoreLocation>> toEvict = new ArrayList<>();
    EvictionPlan plan = new EvictionPlan(toMove, toEvict);

    // 1. If bytesToBeAvailable can already be satisfied without eviction, return the eligible
    // StoargeDirView

    // 2. Iterate over blocks in order until we find a StorageDirView that is in the range of
    // location and can satisfy bytesToBeAvailable after evicting its blocks iterated so far
    EvictionDirCandidates dirCandidates = new EvictionDirCandidates();

    for(Long blockId : removeBlocks) {
      try {
        BlockMeta block = mManagerView.getBlockMeta(blockId);
        if (block != null) { // might not present in this view
          String tierAlias = block.getParentDir().getParentTier().getTierAlias();
          int dirIndex = block.getParentDir().getDirIndex();
          dirCandidates.add(mManagerView.getTierView(tierAlias).getDirView(dirIndex), blockId,
                  block.getBlockSize());

        }
      } catch (BlockDoesNotExistException e) {
        it.remove();
        onRemoveBlockFromIterator(blockId);
      }
    }


    // 4. cascading eviction: try to allocate space in the next tier to move candidate blocks
    // there. If allocation fails, the next tier will continue to evict its blocks to free space.
    // Blocks are only evicted from the last tier or it can not be moved to the next tier.
    StorageDirView candidateDirView = dirCandidates.candidateDir();
    List<Long> candidateBlocks = dirCandidates.candidateBlocks();
    StorageTierView nextTierView = mManagerView.getNextTier(candidateDirView.getParentTierView());
    if (nextTierView == null) {
      // This is the last tier, evict all the blocks.
      for (Long blockId : candidateBlocks) {
        try {
          BlockMeta block = mManagerView.getBlockMeta(blockId);
          if (block != null) {
            candidateDirView.markBlockMoveOut(blockId, block.getBlockSize());
            plan.toEvict().add(new Pair<>(blockId, candidateDirView.toBlockStoreLocation()));
          }
        } catch (BlockDoesNotExistException e) {
          continue;
        }
      }
    } else {
      for (Long blockId : candidateBlocks) {
        try {
          BlockMeta block = mManagerView.getBlockMeta(blockId);
          if (block == null) {
            continue;
          }
          StorageDirView nextDirView = evictor.mAllocator.allocateBlockWithView(
                  Sessions.MIGRATE_DATA_SESSION_ID, block.getBlockSize(),
                  BlockStoreLocation.anyDirInTier(nextTierView.getTierViewAlias()), mManagerView);
          if (nextDirView == null) {
            nextDirView = evictor.cascadingEvict(block.getBlockSize(),
                    BlockStoreLocation.anyDirInTier(nextTierView.getTierViewAlias()), plan);
          }
          if (nextDirView == null) {
            // If we failed to find a dir in the next tier to move this block, evict it and
            // continue. Normally this should not happen.
            plan.toEvict().add(new Pair<>(blockId, block.getBlockLocation()));
            candidateDirView.markBlockMoveOut(blockId, block.getBlockSize());
            continue;
          }
          plan.toMove().add(new BlockTransferInfo(blockId, block.getBlockLocation(),
                  nextDirView.toBlockStoreLocation()));
          candidateDirView.markBlockMoveOut(blockId, block.getBlockSize());
          nextDirView.markBlockMoveIn(blockId, block.getBlockSize());
        } catch (BlockDoesNotExistException e) {
          continue;
        }
      }
    }
    mManagerView.clearBlockMarks();
    /*
    if (candidateDirView == null) {
      return null;
    }*/

    try {
      evictor.mTieredBlockStore.freeSpaceByLRU(0, plan);
    } catch (Exception e) {

    }
    //return candidateDirView;
  }


  private long penaltyFunction(long ratio) {



  }

  private long HRDCompute(String user) {
    long actualHR = mUserEvictors.get(user).HRCompute();
    long baselineHR = ((AbstractEvictor)mMockStorage.get(user).mEvictor).HRCompute();
    long HRD =  baselineHR - actualHR;
    long HRDPenalty =
  }


  private Integer[] Sample() {
    int size = mBlockToShadowPrice.size();
    Set<Integer> checker = new HashSet<>();

    Random random = new Random();
    while(checker.size()!=mSampleNum) {
      int randomNumber =  random.nextInt(size-1)%(size);
      checker.add(randomNumber);
    }
    Integer[] res = (Integer[])checker.toArray();
    Arrays.sort(res);
    return res;
  }

  public AbstractEvictor getMMFEvictor(String tier) {
    long max = 0;
    AbstractEvictor maxEvictor = null;
    for(Map.Entry entry : mUserEvictors.entrySet()) {
      AbstractEvictor evictor = (AbstractEvictor)entry.getKey();
      if(evictor.mTierSpace.get(tier) > max) {
        max = evictor.mTierSpace.get(tier);
        maxEvictor = evictor;
      }
    }
    return maxEvictor;
  }
}



