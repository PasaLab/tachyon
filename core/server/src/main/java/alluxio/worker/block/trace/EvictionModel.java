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

import pasalab.feature.Feature;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * This class provide a softmax classify model to predict the probability of each
 * {@link alluxio.worker.block.evictor.Evictor} to achieve best performance.
 */
public class EvictionModel {
  private static final int FEATURE_WIDTH = Configuration.getInt(PropertyKey.WORKER_FEATURE_WIDTH);
  private static final String MODEL_PARAMETER_FILE =
      Configuration.get(PropertyKey.WORKER_MODEL_PARAMETER_FILE);
  private static final double MODEL_PREDICT_EPS =
      Configuration.getDouble(PropertyKey.WORKER_MODEL_PREDICT_EPS);

  private final TieredBlockStore mTieredBlockStore;
  private double[][] mWeights = new double[4][FEATURE_WIDTH + 1];

  /**
   * Create an instance of {@link EvictionModel}.
   *
   * @param blockStore instance of {@link TieredBlockStore}
   * @throws IOException if failed to load parameters from file
   */
  public EvictionModel(TieredBlockStore blockStore) throws IOException {
    mTieredBlockStore = blockStore;
    loadWeightParameters();
  }

  private void loadWeightParameters() throws IOException {
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(MODEL_PARAMETER_FILE));
      String line = "";
      int i = 0;
      while ((line = br.readLine()) != null) {
        String[] items = line.split(",");
        if (items.length != FEATURE_WIDTH + 1) {
          throw new IllegalArgumentException("Wrong feature width Error!");
        }
        for (int j = 0; j < items.length; j++) {
          mWeights[i][j] = Double.parseDouble(items[j].trim());
        }
        i++;
      }
    } finally {
      if (br != null) {
        br.close();
      }
    }
  }

  /**
   * Predict the {@link alluxio.worker.block.evictor.Evictor}
   * which will achieve best performance in the near future and decide if switch or not.
   *
   * @param feature instance of {@link Feature}
   */
  public void predict(Feature feature) {
    List<EvictorType> candidateEvictorTypes = new ArrayList<>();
    Vector<Double> vec = feature.toVector();
    vec.add(1.0);
    double[] dots = new double[4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < FEATURE_WIDTH + 1; j++) {
        dots[i] += vec.get(j) * mWeights[i][j];
      }
    }
    double sum = 0;
    for (int i = 0; i < 4; i++) {
      dots[i] = Math.exp(dots[i]);
      sum += dots[i];
    }
    double max = 0.0;
    int maxPos = 0;
    double min = 1.0;
    int minPos = 0;
    for (int i = 0; i < 4; i++) {
      dots[i] /= sum;
      if (dots[i] > max) {
        max = dots[i];
        maxPos = i;
      }
      if (dots[i] < min) {
        min = dots[i];
        minPos = i;
      }
    }
    for (int i = 0; i < 4; i++) {
      if (max - dots[i] < MODEL_PREDICT_EPS) {
        candidateEvictorTypes.add(EvictorType.getEvictorType(i + 1));
      }
      System.out.print(dots[i] + " ");
    }
    System.out.println();
    if (!candidateEvictorTypes.contains(mTieredBlockStore.getEvictorType())) {
      mTieredBlockStore.switchEvictor(EvictorType.getEvictorType(maxPos + 1));
    }
  }
}
