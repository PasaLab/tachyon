/*
 * Licensed to the University of California, Berkeley under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package tachyon.worker.block.evictor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.util.ConcurrentHashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import tachyon.Constants;
import tachyon.Sessions;
import tachyon.StorageDirId;
import tachyon.collections.Pair;
import tachyon.conf.TachyonConf;
import tachyon.exception.BlockDoesNotExistException;
import tachyon.worker.WorkerContext;
import tachyon.worker.block.BlockMetadataManagerView;
import tachyon.worker.block.BlockStoreLocation;
import tachyon.worker.block.allocator.Allocator;
import tachyon.worker.block.meta.BlockMeta;
import tachyon.worker.block.meta.StorageDirView;
import tachyon.worker.block.meta.StorageTierView;

public final class LIRSEvictor extends EvictorBase {

  class BlockLIRSInfo {
    private boolean mIsLIR;
    private boolean mIsHIR;
    private boolean mIsResident;
    private int mAccessTimes;

    public BlockLIRSInfo(boolean isLIR, boolean isResident) {
      mIsLIR = isLIR;
      mIsHIR = !isLIR;
      mIsResident = isResident;
      mAccessTimes = 1;
    }

    public void setIsLIR(boolean isLIR) {
      mIsLIR = isLIR;
      mIsHIR = !isLIR;
    }

    public void setIsHIR(boolean isHIR) {
      mIsLIR = !isHIR;
      mIsHIR = isHIR;
    }

    public void setIsResident(boolean isResident) {
      mIsResident = isResident;
    }

    public void setAccessTimes(int times) {
      mAccessTimes = times;
    }

    public boolean getIsLIR() {
      return mIsLIR;
    }

    public boolean getIsHIR() {
      return mIsHIR;
    }

    public boolean getIsResident() {
      return mIsResident;
    }

    public void incrementAccessTimes() {
      mAccessTimes ++;
    }

    public int getAccessTimes() {
      return mAccessTimes;
    }
  }

  private static final Logger LOG = LoggerFactory.getLogger(Constants.LOGGER_TYPE);

  private static final int LINKED_HASH_MAP_INIT_CAPACITY = 200;
  private static final float LINKED_HASH_MAP_INIT_LOAD_FACTOR = 0.75f;
  private static final boolean LINKED_HASH_MAP_ACCESS_ORDERED = false;

  private final double mLIRPercent;
  private final double mHIRPercent;

  private final TachyonConf mTachyonConf;

  private Map<Long, Long> mDirTotalBytes = new ConcurrentHashMap<Long, Long>();
  private Map<Long, Long> mDirLIRUsedBytes = new ConcurrentHashMap<Long, Long>();
  private Map<Long, Long> mDirHIRUsedBytes = new ConcurrentHashMap<Long, Long>();
  private Map<Long, Long> mBlockSize = new ConcurrentHashMap<Long, Long>();

  private int mHotLimit = 2;

  protected Map<Pair<Long, Long>, BlockLIRSInfo> mLIRCache = Collections
      .synchronizedMap(new LinkedHashMap<Pair<Long, Long>, BlockLIRSInfo>(
          LINKED_HASH_MAP_INIT_CAPACITY, LINKED_HASH_MAP_INIT_LOAD_FACTOR,
          LINKED_HASH_MAP_ACCESS_ORDERED));
  protected Map<Pair<Long, Long>, BlockLIRSInfo> mHIRCache = Collections
      .synchronizedMap(new LinkedHashMap<Pair<Long, Long>, BlockLIRSInfo>(
          LINKED_HASH_MAP_INIT_CAPACITY, LINKED_HASH_MAP_INIT_LOAD_FACTOR,
          LINKED_HASH_MAP_ACCESS_ORDERED));
  protected Map<Long, Set<Long>> mTMPCache = new ConcurrentHashMap<Long, Set<Long>>();

  public LIRSEvictor(BlockMetadataManagerView view, Allocator allocator) {
    super(view, allocator);
    mTachyonConf = WorkerContext.getConf();
    mHIRPercent = mTachyonConf.getDouble(Constants.WORKER_EVICTOR_LIRS_HIR_PERCENT);
    Preconditions.checkArgument(mHIRPercent >= 0 && mHIRPercent <= 1,
        "HIR percent should be larger than 0 and less than 1");
    mLIRPercent = 1.0 - mHIRPercent;
    mHotLimit = mTachyonConf.getInt(Constants.WORKER_EVICTOR_LIRS_HOT_LIMIT);

    for (StorageTierView tier : view.getTierViews()) {
      for (StorageDirView dir : tier.getDirViews()) {
        long storageDirId = getStorageDirId(tier, dir);
        long totalbytes = 0;
        long lirbytes = 0;
        long hirbytes = 0;
        mTMPCache.put(storageDirId, new ConcurrentHashSet<Long>());
        mDirTotalBytes.put(storageDirId, dir.getAvailableBytes() + dir.getEvitableBytes());
        mDirLIRUsedBytes.put(storageDirId, 0L);
        mDirHIRUsedBytes.put(storageDirId, 0L);
        for (BlockMeta block : dir.getEvictableBlocks()) {
          long blockId = block.getBlockId();
          long blocksize = block.getBlockSize();
          Pair<Long, Long> key = new Pair<Long, Long>(storageDirId, blockId);
          if (lirbytes + blocksize > mLIRPercent * totalbytes) {
            BlockLIRSInfo blockinfo = new BlockLIRSInfo(false, true);
            mLIRCache.put(key, blockinfo);
            mHIRCache.put(key, blockinfo);
            hirbytes += blocksize;
          } else {
            mLIRCache.put(key, new BlockLIRSInfo(true, true));
            lirbytes += blocksize;
          }
          mBlockSize.put(blockId, blocksize);
        }
        mDirHIRUsedBytes.put(storageDirId, hirbytes);
        mDirLIRUsedBytes.put(storageDirId, lirbytes);
      }
    }
  }

  @Override
  protected StorageDirView cascadingEvict(long bytesToBeAvailable, BlockStoreLocation location,
      EvictionPlan plan) {
    location = updateBlockStoreLocation(bytesToBeAvailable, location);

    // 1. If bytesToBeAvailable can already be satisfied without eviction, return the eligible
    // StoargeDirView
    StorageDirView candidateDirView =
        EvictorUtils.selectDirWithRequestedSpace(bytesToBeAvailable, location, mManagerView);
    if (candidateDirView != null) {
      return candidateDirView;
    }

    // 2. Iterate over blocks in order until we find a StorageDirView that is in the range of
    // location and can satisfy bytesToBeAvailable after evicting its blocks iterated so far
    EvictionDirCandidates dirCandidates = new EvictionDirCandidates();
    iteratorForCandidates(dirCandidates, bytesToBeAvailable, location, mHIRCache);
    if (dirCandidates.candidateSize() < bytesToBeAvailable) {
      List<Map.Entry<Pair<Long, Long>, BlockLIRSInfo>> list =
          new ArrayList<Map.Entry<Pair<Long, Long>, BlockLIRSInfo>>();
      Iterator<Map.Entry<Pair<Long, Long>, BlockLIRSInfo>> it = mLIRCache.entrySet().iterator();
      while (it.hasNext()) {
        list.add(it.next());
      }
      Map<Pair<Long, Long>, BlockLIRSInfo> map =
          Collections.synchronizedMap(new LinkedHashMap<Pair<Long, Long>, BlockLIRSInfo>(
              LINKED_HASH_MAP_INIT_CAPACITY, LINKED_HASH_MAP_INIT_LOAD_FACTOR,
              LINKED_HASH_MAP_ACCESS_ORDERED));
      for (int i = list.size() - 1; i >= 0; i --) {
        Map.Entry<Pair<Long, Long>, BlockLIRSInfo> entry = list.get(i);
        map.put(entry.getKey(), entry.getValue());
      }
      iteratorForCandidates(dirCandidates, bytesToBeAvailable, location, map);
    }

    // 3. If there is no eligible StorageDirView, return null
    if (dirCandidates.candidateSize() < bytesToBeAvailable) {
      return null;
    }

    // 4. cascading eviction: try to allocate space in the next tier to move candidate blocks
    // there. If allocation fails, the next tier will continue to evict its blocks to free space.
    // Blocks are only evicted from the last tier or it can not be moved to the next tier.
    candidateDirView = dirCandidates.candidateDir();
    List<Long> candidateBlocks = dirCandidates.candidateBlocks();
    StorageTierView nextTierView = mManagerView.getNextTier(candidateDirView.getParentTierView());
    if (nextTierView == null) {
      // This is the last tier, evict all the blocks.
      for (Long blockId : candidateBlocks) {
        try {
          BlockMeta block = mManagerView.getBlockMeta(blockId);
          if (block != null) {
            candidateDirView.markBlockMoveOut(blockId, block.getBlockSize());
            plan.toEvict()
                .add(
                    new Pair<Long, BlockStoreLocation>(blockId, candidateDirView
                        .toBlockStoreLocation()));
          }
        } catch (BlockDoesNotExistException nfe) {
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
          StorageDirView nextDirView =
              mAllocator.allocateBlockWithView(Sessions.MIGRATE_DATA_SESSION_ID,
                  block.getBlockSize(),
                  BlockStoreLocation.anyDirInTier(nextTierView.getTierViewAlias()), mManagerView);
          if (nextDirView == null) {
            nextDirView =
                cascadingEvict(block.getBlockSize(),
                    BlockStoreLocation.anyDirInTier(nextTierView.getTierViewAlias()), plan);
          }
          if (nextDirView == null) {
            // If we failed to find a dir in the next tier to move this block, evict it and
            // continue. Normally this should not happen.
            plan.toEvict().add(
                new Pair<Long, BlockStoreLocation>(blockId, block.getBlockLocation()));
            candidateDirView.markBlockMoveOut(blockId, block.getBlockSize());
            continue;
          }
          plan.toMove().add(
              new BlockTransferInfo(blockId, block.getBlockLocation(), nextDirView
                  .toBlockStoreLocation()));
          candidateDirView.markBlockMoveOut(blockId, block.getBlockSize());
          nextDirView.markBlockMoveIn(blockId, block.getBlockSize());
        } catch (BlockDoesNotExistException nfe) {
          continue;
        }
      }
    }

    return candidateDirView;
  }

  private void iteratorForCandidates(EvictionDirCandidates dirCandidates, long bytesToBeAvailable,
      BlockStoreLocation location, Map<Pair<Long, Long>, BlockLIRSInfo> map) {
    Iterator<Map.Entry<Pair<Long, Long>, BlockLIRSInfo>> it = map.entrySet().iterator();
    while (it.hasNext() && dirCandidates.candidateSize() < bytesToBeAvailable) {
      Entry<Pair<Long, Long>, BlockLIRSInfo> entry = it.next();
      BlockLIRSInfo blockinfo = entry.getValue();
      long blockId = entry.getKey().getSecond();
      if (!blockinfo.getIsResident()) {
        continue;
      }
      try {
        BlockMeta block = mManagerView.getBlockMeta(blockId);
        if (block != null) { // might not present in this view
          if (block.getBlockLocation().belongTo(location)) {
            int tierAlias = block.getParentDir().getParentTier().getTierAlias();
            int dirIndex = block.getParentDir().getDirIndex();
            dirCandidates.add(mManagerView.getTierView(tierAlias).getDirView(dirIndex), blockId,
                block.getBlockSize());
          }
        } else {
          System.out.println("block " + blockId + " cannot be evicted, may be locked!");
        }
      } catch (BlockDoesNotExistException nfe) {
        LOG.warn("Remove block {} from evictor cache because {}", blockId, nfe);
        it.remove();
        onRemoveBlockFromIterator(blockId);
      }
    }
  }

  private long getStorageDirId(StorageTierView tier, StorageDirView dir) {
    int tierLevel = tier.getTierViewLevel();
    int tierAliasValue = tier.getTierViewAlias();
    int dirIndex = dir.getDirViewIndex();
    return StorageDirId.getStorageDirId(tierLevel, tierAliasValue, dirIndex);
  }

  @Override
  protected Iterator<Long> getBlockIterator() {
    return null;
  }

  @Override
  public void onAccessBlock(long userId, long blockId) {
    System.out.println("access block " + blockId);
    System.out.println("Access increment");
    updateOnAccess(blockId);
  }

  @Override
  public void onCommitBlock(long userId, long blockId, BlockStoreLocation location) {
    System.out.println("commit block " + blockId);
    try {
      updateOnCommit(blockId, location);
    } catch (BlockDoesNotExistException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onMoveBlockByClient(long sessionId, long blockId, BlockStoreLocation oldLocation,
      BlockStoreLocation newLocation) {
    System.out.println("move block " + blockId + " from tier " + oldLocation.tierAlias()
        + " to tier " + newLocation.tierAlias());
    if (newLocation.tierAlias() < oldLocation.tierAlias()) {
      System.out.println("Miss increment");
    }
    updateOnMove(blockId, oldLocation, newLocation);
  }

  @Override
  public void onMoveBlockByWorker(long sessionId, long blockId, BlockStoreLocation oldLocation,
      BlockStoreLocation newLocation) {
    System.out.println("move block " + blockId + " from tier " + oldLocation.tierAlias()
        + " to tier " + newLocation.tierAlias());
    if (newLocation.tierAlias() < oldLocation.tierAlias()) {
      System.out.println("Miss increment");
    }
    updateOnMove(blockId, oldLocation, newLocation);
  }

  @Override
  public void onRemoveBlockByClient(long userId, long blockId) {
    System.out.println("remove block " + blockId);
    updateOnRemove(blockId);
  }

  @Override
  public void onRemoveBlockByWorker(long userId, long blockId) {
    System.out.println("remove block " + blockId);
    updateOnRemove(blockId);
  }

  private void updateDirLIRSize(long storageDirId) {
    long lirbytes = mDirLIRUsedBytes.get(storageDirId);
    long hirbytes = mDirHIRUsedBytes.get(storageDirId);
    long totalbytes = mDirTotalBytes.get(storageDirId);
    Iterator<Map.Entry<Pair<Long, Long>, BlockLIRSInfo>> it = mLIRCache.entrySet().iterator();
    while (it.hasNext() && lirbytes > totalbytes * mLIRPercent) {
      Entry<Pair<Long, Long>, BlockLIRSInfo> entry = it.next();
      BlockLIRSInfo blockinfo = entry.getValue();
      boolean isEnd = false;
      while (entry.getKey().getFirst() != storageDirId) {
        if (it.hasNext()) {
          entry = it.next();
          blockinfo = entry.getValue();
        } else {
          isEnd = true;
        }
      }
      if (isEnd) {
        break;
      }
      if (blockinfo.getIsHIR()) {
        it.remove();
      } else if (lirbytes > totalbytes * mLIRPercent && blockinfo.getIsLIR()) {
        long blocksize = mBlockSize.get(entry.getKey().getSecond());
        blockinfo.setIsHIR(true);
        blockinfo.setIsResident(true);
        blockinfo.setAccessTimes(blockinfo.getAccessTimes() / 2);
        mHIRCache.put(entry.getKey(), blockinfo);
        lirbytes -= blocksize;
        hirbytes += blocksize;
        it.remove();
      }
    }
    mDirLIRUsedBytes.put(storageDirId, lirbytes);
    mDirHIRUsedBytes.put(storageDirId, hirbytes);
  }

  private void updateOnAccess(long blockId) {
    for (StorageTierView tier : mManagerView.getTierViews()) {
      for (StorageDirView dir : tier.getDirViews()) {
        long storageDirId = getStorageDirId(tier, dir);
        long blocksize = mBlockSize.get(blockId);
        long lirbytes = mDirLIRUsedBytes.get(storageDirId);
        long hirbytes = mDirHIRUsedBytes.get(storageDirId);
        long totalbytes = mDirTotalBytes.get(storageDirId);
        Pair<Long, Long> key = new Pair<Long, Long>(storageDirId, blockId);
        Set<Long> tmpBlocks = mTMPCache.get(storageDirId);
        if (tmpBlocks.contains(blockId)) {
          tmpBlocks.remove(blockId);
          if (mLIRCache.containsKey(key)) {
            BlockLIRSInfo blockinfo = mLIRCache.get(key);
            blockinfo.incrementAccessTimes();
            if (blockinfo.getAccessTimes() >= mHotLimit) {
              lirbytes += blocksize;
              blockinfo.setIsLIR(true);
              blockinfo.setIsResident(true);
              mLIRCache.remove(key);
              mLIRCache.put(key, blockinfo);
            } else {
              hirbytes += blocksize;
              blockinfo.setIsResident(true);
              mLIRCache.remove(key);
              mLIRCache.put(key, blockinfo);
              mHIRCache.put(key, blockinfo);
            }
            mDirLIRUsedBytes.put(storageDirId, lirbytes);
            mDirHIRUsedBytes.put(storageDirId, hirbytes);
            updateDirLIRSize(storageDirId);
          } else if (lirbytes + blocksize > totalbytes * mLIRPercent) {
            hirbytes += blocksize;
            BlockLIRSInfo blockinfo = new BlockLIRSInfo(false, true);
            mHIRCache.put(key, blockinfo);
            mLIRCache.put(key, blockinfo);
            mDirHIRUsedBytes.put(storageDirId, hirbytes);
          } else {
            lirbytes += blocksize;
            BlockLIRSInfo blockinfo = new BlockLIRSInfo(true, true);
            mLIRCache.put(key, blockinfo);
            mDirLIRUsedBytes.put(storageDirId, lirbytes);
          }
        } else {
          if (mHIRCache.containsKey(key)) {
            BlockLIRSInfo blockinfo = mHIRCache.get(key);
            blockinfo.incrementAccessTimes();
            if (mLIRCache.containsKey(key)) {
              if (blockinfo.getAccessTimes() >= mHotLimit) {
                blockinfo.setIsLIR(true);
                blockinfo.setIsResident(true);
                mHIRCache.remove(key);
                mLIRCache.remove(key);
                mLIRCache.put(key, blockinfo);
                lirbytes += blocksize;
                hirbytes -= blocksize;
              } else {
                mHIRCache.remove(key);
                mHIRCache.put(key, blockinfo);
                mLIRCache.remove(key);
                mLIRCache.put(key, blockinfo);
              }
              mDirLIRUsedBytes.put(storageDirId, lirbytes);
              mDirHIRUsedBytes.put(storageDirId, hirbytes);
              updateDirLIRSize(storageDirId);
            } else {
              mHIRCache.remove(key);
              mHIRCache.put(key, blockinfo);
              mLIRCache.put(key, blockinfo);
            }
          } else if (mLIRCache.containsKey(key)) {
            BlockLIRSInfo blockinfo = mLIRCache.get(key);
            blockinfo.incrementAccessTimes();
            if (blockinfo.getIsLIR()) {
              mLIRCache.remove(key);
              mLIRCache.put(key, blockinfo);
              updateDirLIRSize(storageDirId);
            }
          }
        }
      }
    }
  }

  private void updateOnCommit(long blockId, BlockStoreLocation location)
      throws BlockDoesNotExistException {
    for (StorageTierView tier : mManagerView.getTierViews()) {
      for (StorageDirView dir : tier.getDirViews()) {
        int alias = tier.getTierViewAlias();
        int level = tier.getTierViewLevel();
        long storageDirId = getStorageDirId(tier, dir);
        if (new BlockStoreLocation(alias, level, dir.getDirViewIndex()).belongTo(location)) {
          Pair<Long, Long> key = new Pair<Long, Long>(storageDirId, blockId);
          long lirbytes = mDirLIRUsedBytes.get(storageDirId);
          long hirbytes = mDirHIRUsedBytes.get(storageDirId);
          long totalbytes = mDirTotalBytes.get(storageDirId);
          long blocksize = mManagerView.getBlockMetaTmpMethod(blockId).getBlockSize();
          mBlockSize.put(blockId, blocksize);
          if (lirbytes + blocksize > totalbytes * mLIRPercent) {
            hirbytes += blocksize;
            BlockLIRSInfo blockinfo = new BlockLIRSInfo(false, true);
            mHIRCache.put(key, blockinfo);
            mLIRCache.put(key, blockinfo);
            mDirHIRUsedBytes.put(storageDirId, hirbytes);
          } else {
            lirbytes += blocksize;
            mLIRCache.put(key, new BlockLIRSInfo(true, true));
            mDirLIRUsedBytes.put(storageDirId, lirbytes);
          }
          return;
        }
      }
    }
  }

  private void updateOnMove(long blockId, BlockStoreLocation oldLocation,
      BlockStoreLocation newLocation) {
    if (newLocation.belongTo(oldLocation)) {
      return;
    }
    for (StorageTierView tier : mManagerView.getTierViews()) {
      for (StorageDirView dir : tier.getDirViews()) {
        long storageDirId = getStorageDirId(tier, dir);
        int alias = tier.getTierViewAlias();
        int level = tier.getTierViewLevel();
        long blocksize = mBlockSize.get(blockId);
        long totalbytes = mDirTotalBytes.get(storageDirId);
        long lirbytes = mDirLIRUsedBytes.get(storageDirId);
        long hirbytes = mDirHIRUsedBytes.get(storageDirId);
        Pair<Long, Long> key = new Pair<Long, Long>(storageDirId, blockId);
        Set<Long> blocks = mTMPCache.get(storageDirId);
        if (new BlockStoreLocation(alias, level, dir.getDirViewIndex()).belongTo(oldLocation)) {
          if (mHIRCache.containsKey(key)) {
            hirbytes -= blocksize;
            BlockLIRSInfo blockinfo = mHIRCache.get(key);
            blockinfo.setIsResident(false);
            mHIRCache.remove(key);
            mDirHIRUsedBytes.put(storageDirId, hirbytes);
          } else if (mLIRCache.containsKey(key)) {
            BlockLIRSInfo blockinfo = mLIRCache.get(key);
            if (blockinfo.getIsLIR()) {
              lirbytes -= blocksize;
              mDirLIRUsedBytes.put(storageDirId, lirbytes);
              blockinfo.setIsHIR(true);
              blockinfo.setIsResident(false);
              updateDirLIRSize(storageDirId);
            }
          } else if (blocks.contains(blockId)) {
            blocks.remove(blockId);
          }
        }
        if (new BlockStoreLocation(alias, level, dir.getDirViewIndex()).belongTo(newLocation)) {
          if (newLocation.tierLevel() < oldLocation.tierLevel()) {
            blocks.add(blockId);
            mTMPCache.put(storageDirId, blocks);
          } else if (newLocation.tierLevel() > oldLocation.tierLevel()) {
            if (mLIRCache.containsKey(key)) {
              BlockLIRSInfo blockinfo = mLIRCache.get(key);
              blockinfo.incrementAccessTimes();
              if (blockinfo.getAccessTimes() >= mHotLimit) {
                lirbytes += blocksize;
                blockinfo.setIsLIR(true);
                blockinfo.setIsResident(true);
                mLIRCache.remove(key);
                mLIRCache.put(key, blockinfo);
              } else {
                hirbytes += blocksize;
                blockinfo.setIsHIR(true);
                blockinfo.setIsResident(true);
                mLIRCache.remove(key);
                mLIRCache.put(key, blockinfo);
                mHIRCache.put(key, blockinfo);
              }
              mDirLIRUsedBytes.put(storageDirId, lirbytes);
              mDirHIRUsedBytes.put(storageDirId, hirbytes);
              updateDirLIRSize(storageDirId);
            } else if (lirbytes + blocksize > totalbytes * mLIRPercent) {
              hirbytes += blocksize;
              BlockLIRSInfo blockinfo = new BlockLIRSInfo(false, true);
              mHIRCache.put(key, blockinfo);
              mLIRCache.put(key, blockinfo);
              mDirHIRUsedBytes.put(storageDirId, hirbytes);
            } else {
              lirbytes += blocksize;
              mLIRCache.put(key, new BlockLIRSInfo(true, true));
              mDirLIRUsedBytes.put(storageDirId, lirbytes);
            }
          }
        }
      }
    }
  }

  private void updateOnRemove(long blockId) {
    for (StorageTierView tier : mManagerView.getTierViews()) {
      for (StorageDirView dir : tier.getDirViews()) {
        long storageDirId = getStorageDirId(tier, dir);
        if (!mBlockSize.containsKey(blockId)) {
          continue;
        }
        long blocksize = mBlockSize.get(blockId);
        long totalbytes = mDirTotalBytes.get(storageDirId);
        long lirbytes = mDirLIRUsedBytes.get(storageDirId);
        long hirbytes = mDirHIRUsedBytes.get(storageDirId);
        Pair<Long, Long> key = new Pair<Long, Long>(storageDirId, blockId);
        Set<Long> tmpBlocks = mTMPCache.get(storageDirId);
        if (mHIRCache.containsKey(key)) {
          mHIRCache.remove(key);
          hirbytes -= blocksize;
          mDirHIRUsedBytes.put(storageDirId, hirbytes);
        } else if (mLIRCache.containsKey(key)) {
          BlockLIRSInfo blockinfo = mLIRCache.get(key);
          lirbytes -= blocksize;
          mDirLIRUsedBytes.put(storageDirId, lirbytes);
          mLIRCache.remove(key);
          updateDirLIRSize(storageDirId);
        } else if (tmpBlocks.contains(blockId)) {
          tmpBlocks.remove(blockId);
        }
        mBlockSize.remove(blockId);
      }
    }
  }
}
