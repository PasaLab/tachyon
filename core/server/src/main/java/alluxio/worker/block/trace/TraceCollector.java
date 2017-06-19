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

import pasalab.feature.Detector;
import pasalab.feature.Feature;
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
  private static final long HISTORY_WINDOW_LENGTH =
      Configuration.getBytes(PropertyKey.WORKER_HISTORY_WINDOW_LENGTH);

  private LinkedList<TraceEntry> mLastTraceEntries = new LinkedList<>();
  private LinkedList<TraceEntry> mTraceEntries = new LinkedList<>();
  private long mCurrentTraceSize = 0L;
  private long mHistoryTraceSize = 0L;

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
      checkTraceWindow();
    }
  }

  private void checkTraceWindow() {
    if (mCurrentTraceSize >= SCAN_WINDOW_LENGTH) {
      while (mHistoryTraceSize > HISTORY_WINDOW_LENGTH) {
        TraceEntry tmpEntry = mLastTraceEntries.removeFirst();
        if (tmpEntry.getBlockOpType() == BlockOpType.COMMIT
            || tmpEntry.getBlockOpType() == BlockOpType.ACCESS) {
          mHistoryTraceSize -= tmpEntry.getBlockSize();
        }
      }
      Detector detector = new Detector(mTraceEntries, mLastTraceEntries);
      Feature feature = detector.detectFeature(false);
      mModel.predict(feature);
      // Drop the least recently collected trace entries to the last window.
      while (mCurrentTraceSize > SCAN_WINDOW_LENGTH - SKIP_WINDOW_LENGTH) {
        TraceEntry tmpEntry = mTraceEntries.removeFirst();
        mLastTraceEntries.add(tmpEntry);
        if (tmpEntry.getBlockOpType() == BlockOpType.COMMIT
            || tmpEntry.getBlockOpType() == BlockOpType.ACCESS) {
          mCurrentTraceSize -= tmpEntry.getBlockSize();
          mHistoryTraceSize += tmpEntry.getBlockSize();
        }
      }
    }
  }
}
