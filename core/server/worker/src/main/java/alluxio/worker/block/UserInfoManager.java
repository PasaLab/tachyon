package alluxio.worker.block;

import alluxio.collections.Pair;
import alluxio.worker.block.evictor.LocalEvictor;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by lenovo on 2017/9/14.
 */
public final class UserInfoManager {
  private final ConcurrentHashMap<String, HashSet<Long>> mUserBlocksMap = new
          ConcurrentHashMap<>();
  private final ConcurrentHashMap<Long, HashSet<String>> mBlockUsersMap = new ConcurrentHashMap<>();

  private PriorityBlockingQueue<Pair<String, Long>> mQueue = new PriorityBlockingQueue<>
          (mUserBlocksMap
                  .size(),new
                  Comparator<Pair<String, Long>>() {
                    @Override
                    public int compare(Pair<String, Long> o1, Pair<String, Long> o2) {
                      return (int)(o2.getSecond() - o1.getSecond());
                    }
                  });
  private
  private final BlockMasterClientPool mBlockmasterClientPool;

  public final ConcurrentHashMap<String, LocalEvictor> mUserEvictor = new ConcurrentHashMap<>();



  public void addBlockForUser(String owner, long blockId) {
    mUserBlocksMap.putIfAbsent(owner, new HashSet<Long>());
    mUserBlocksMap.get(owner).add(blockId);
  }

  public void addUserForBlock(String owner, long blockId) {
    mBlockUsersMap.putIfAbsent(blockId, new HashSet<String>());
    mBlockUsersMap.get(blockId).add(owner);
  }

  public void removeUserBlockInfo(long blockId) {
    if(mBlockUsersMap.containsKey(blockId)) {
      HashSet<String> owners = mBlockUsersMap.get(blockId);
      mBlockUsersMap.remove(blockId);
      Iterator i = owners.iterator();
      while(i.hasNext()) {
        String owner = (String)i.next();
        mUserBlocksMap.get(owner).remove(blockId);
        if(mUserBlocksMap.get(owner).size() == 0) {
          mUserBlocksMap.remove(owner);
        }
      }
    }
  }

  public boolean isUserOwnBlock(String user, long blockId) {
    if(mUserBlocksMap.containsKey(user)) {
      return mUserBlocksMap.get(user).contains(blockId);
    }
    return false;
  }

  public int blockUsersNum(long blockId) {
    return mBlockUsersMap.get(blockId).size();
  }

  public HashSet<String> getUsersByBlockId(long blockId) {
    return mBlockUsersMap.get(blockId);
  }

  public void UpdateUserSpaceQueue(BlockStoreLocation) throws
          IOException {
    BlockMasterClient client = mBlockmasterClientPool.acquire();
    mQueue.clear();
    for(Map.Entry entry: mUserBlocksMap.entrySet()) {
      String user = (String)entry.getKey();
      List<Long> blocks = new ArrayList<Long>((HashSet)entry.getValue());
      long size = client.getBlocksSize(blocks);
      Pair<String, Long> userPair = new Pair<>(user, size);
      mQueue.add(userPair);
    }
    mBlockmasterClientPool.release(client);
  }

  public Iterator<Pair<String, Long>> getUserSpaceIterator() {
    return mQueue.iterator();
  }
}


