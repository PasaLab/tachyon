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

package alluxio.worker.block.trace;

import alluxio.Configuration;
import alluxio.PropertyKey;

import pasalab.trace.BlockOpType;
import pasalab.trace.TraceEntry;

import java.util.LinkedList;

/**
 * This class collect block operation trace online.
 */
public class TraceCollector {
  private static final long SCAN_WINDOW_LENGTH =
      Configuration.getBytes(PropertyKey.WORKER_SCAN_WINDOW_LENGTH);
  private static final long SKIP_WINDOW_LENGTH =
      Configuration.getBytes(PropertyKey.WORKER_SKIP_WINDOW_LENGTH);

  private LinkedList<TraceEntry> mTraceEntries = new LinkedList<>();
  private long mCurrentTraceSize = 0L;

  private EvictionModel mModel;

  /**
   * Create an instance of {@link TraceCollector}.
   *
   * @param model instance of {@link EvictionModel}
   */
  public TraceCollector(EvictionModel model) {
    mModel = model;
  }

  /**
   * Add an entry element of block operation.
   *
   * @param blockId the block id
   * @param blockSize the block size
   * @param type the type of block operation
   */
  public synchronized void addEntry(long blockId, long blockSize, BlockOpType type) {
    TraceEntry entry = new TraceEntry(blockId, blockSize, type);
    mTraceEntries.add(entry);
    if (type == BlockOpType.ACCESS || type == BlockOpType.COMMIT) {
      mCurrentTraceSize += blockSize;
      //checkTraceWindow();
    }
  }

  /**
   * Add an entry element of block operation.
   *
   * @param blockId the block id
   * @param blockSize the block size
   * @param type the type of block operation
   * @param lruHit if the block is hit with {@link alluxio.worker.block.evictor.LRUEvictor}
   * @param lrfuHit if the block is hit with {@link alluxio.worker.block.evictor.LRFUEvictor}
   * @param lirsHit if the block is hit with {@link alluxio.worker.block.evictor.LIRSEvictor}
   * @param arcHit if the block is hit with {@link alluxio.worker.block.evictor.ARCEvictor}
   */
  public synchronized void addEntry(long blockId, long blockSize, BlockOpType type, boolean lruHit,
      boolean lrfuHit, boolean lirsHit, boolean arcHit) {
    TraceEntry entry = new TraceEntry(blockId, blockSize, type);
    entry.setLRUHit(lruHit).setLRFUHit(lrfuHit).setLIRSHit(lirsHit).setARCHit(arcHit);
    mTraceEntries.add(entry);
    if (type == BlockOpType.COMMIT || type == BlockOpType.ACCESS) {
      mCurrentTraceSize += blockSize;
      //checkTraceWindow();
    }
  }

  /**
   * Check the size of current window. If the size of the current window is over some limit,
   * collect the hit rate and judge whether to switch policy.
   */
  public synchronized void checkTraceWindow() {
    if (mCurrentTraceSize >= SCAN_WINDOW_LENGTH) {
      mModel.predict(mTraceEntries);
      while (mCurrentTraceSize > SCAN_WINDOW_LENGTH - SKIP_WINDOW_LENGTH) {
        TraceEntry tmpEntry = mTraceEntries.removeFirst();
        if (tmpEntry.getBlockOpType() == BlockOpType.COMMIT
            || tmpEntry.getBlockOpType() == BlockOpType.ACCESS) {
          mCurrentTraceSize -= tmpEntry.getBlockSize();
        }
      }
    }
  }
}
