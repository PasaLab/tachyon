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
import alluxio.worker.block.TieredBlockStore;
import alluxio.worker.block.evictor.EvictorType;

import pasalab.trace.TraceEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provide a model to predict the probability of each
 * {@link alluxio.worker.block.evictor.Evictor} to achieve best performance.
 */
public class EvictionModel {
  private static final double MODEL_PREDICT_EPS =
      Configuration.getDouble(PropertyKey.WORKER_MODEL_PREDICT_EPS);

  private final TieredBlockStore mTieredBlockStore;

  /**
   * Create an instance of {@link EvictionModel}.
   *
   * @param blockStore instance of {@link TieredBlockStore}
   */
  public EvictionModel(TieredBlockStore blockStore) {
    mTieredBlockStore = blockStore;
  }

  /**
   * Predict the {@link alluxio.worker.block.evictor.Evictor}
   * which will achieve best performance in the near future and decide if switch or not.
   *
   * @param traceEntries list of {@link TraceEntry}
   */
  public void predict(List<TraceEntry> traceEntries) {
    long[] hitBytes = new long[4];
    for (TraceEntry entry : traceEntries) {
      if (entry.getLRUHit()) {
        hitBytes[0] += entry.getBlockSize();
      }
      if (entry.getLRFUHit()) {
        hitBytes[1] += entry.getBlockSize();
      }
      if (entry.getLIRSHit()) {
        hitBytes[2] += entry.getBlockSize();
      }
      if (entry.getARCHit()) {
        hitBytes[3] += entry.getBlockSize();
      }
    }
    long max = 0L;
    int maxPos = 0;
    for (int i = 0; i < 4; i++) {
      if (hitBytes[i] > max) {
        max = hitBytes[i];
        maxPos = i;
      }
    }
    List<EvictorType> candidateEvictorTypes = new ArrayList<>();
    if (max == 0L) {
      for (int i = 0; i < 4; i++) {
        candidateEvictorTypes.add(EvictorType.getEvictorType(i));
      }
    } else {
      for (int i = 0; i < 4; i++) {
        if (1.0 * (max - hitBytes[i]) / max < MODEL_PREDICT_EPS) {
          candidateEvictorTypes.add(EvictorType.getEvictorType(i));
        }
        System.out.print(hitBytes[i] + " ");
      }
      System.out.println();
    }
    System.out.println("max: " + max + " maxPos: " + maxPos);
    if (!candidateEvictorTypes.contains(mTieredBlockStore.getEvictorType())) {
      mTieredBlockStore.switchEvictor(EvictorType.getEvictorType(maxPos));
    }
  }
}
