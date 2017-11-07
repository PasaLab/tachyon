package alluxio.worker.block.evictor;


import alluxio.worker.block.BlockMetadataManager;
import alluxio.worker.block.TieredBlockStore;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.*;


public enum MT_LRU implements Runnable{
  INSTANCE;
  //TODO consider block size
  private final ConcurrentHashMap<String, AbstractEvictor> mUserEvictors = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<String, MockAbstractEvictor> mMockUserEvictors = new ConcurrentHashMap<>();
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

  public void addMockUserEvictorInfo(String user, MockAbstractEvictor evictor) {
    mMockUserEvictors.putIfAbsent(user, evictor);
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

    }

  }

  public void getUserBlock(String user, AbstractEvictor evictor) {
    Iterator<Long> userIter = evictor.getBlockIterator();
    int size = evictor.getBlocksNum();
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
    Iterator<Long> userIter = evictor.getBlockIterator();
    int size = evictor.getBlocksNum();
    int pos = 0;
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
    //TODO remove block to next level.
  }

  private long UserHRDForm(String user) {


  }

  private long HRDCompute(String user) {
    long actualHR = mUserEvictors.get(user).HRCompute();
    long baselineHR = mMockUserEvictors.get(user).HRCompute();


  }



  private long baselineCompute() {

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

  public Evictor getMMFEvictor() {


  }

  public long getMMFUsedSpace() {

  }









}



