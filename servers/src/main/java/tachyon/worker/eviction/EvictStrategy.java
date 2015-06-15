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

package tachyon.worker.eviction;

import java.util.List;
import java.util.Set;

import tachyon.Pair;
import tachyon.worker.tiered.BlockInfo;
import tachyon.worker.tiered.StorageDir;

/**
 * Get a StorageDir where space can be allocated by evicting some blocks. Because the information of
 * pin file / locked blocks may change after the blocks to evict are selected, at the moment when
 * one really attempts to evict blocks, some candidate blocks may not be allowed to evict any more.
 * As a result, one may have to try multiple times to get enough space.
 */
public abstract class EvictStrategy {

  /**
   * Get a StorageDir allocated and a list of blocks to evict among StorageDir candidates
   *
   * @param storageDirs StorageDir candidates that the space will be allocated in
   * @param pinList list of pinned files
   * @param requestBytes size of requested space in bytes
   * @return a pair of StorageDir allocated and a list of blockInfo which contains the information
   *         of blocks to evict, null if no allocated directory is found
   */
  public abstract Pair<StorageDir, List<BlockInfo>> getDirCandidate(StorageDir[] storageDirs,
      Set<Integer> pinList, long requestBytes);

  /**
   * Actions to take when the new block is added
   */
  public void onAdd(long blockId) {}

  /**
   * Actions to take when cache the block
   */
  public void onCache(long blockId) {}

  /**
   * Actions to take when cancel the block
   */
  public void onCancel(long blockId) {}

  /**
   * Actions to take when copy the block
   */
  public void onCopy(long blockId) {}

  /**
   * Actions to take when delete the block
   */
  public void onDelete(long blockId) {}

  /**
   * Actions to take when lock the block
   */
  public void onLock(long blockId) {}

  /**
   * Actions to take when move the block
   */
  public void onMove(long blockId) {}

  /**
   * Actions to take when read the block
   */
  public void onRead(long blockId) {}

  /**
   * Actions to take when unlock the block
   */
  public void onUnLock(long blockId) {}
}
