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

import tachyon.worker.tiered.StorageTier;

/**
 * Used to get a specific EvictStrategy based on EvictStrategyType
 */
public class EvictStrategies {
  /**
   * Get block EvictStrategy based on configuration
   *
   * @param strategyType configuration of EvictStrategy
   * @param isLastTier whether eviction is applied on the last StorageTier
   * @return EvictStrategy generated
   */
  public static EvictStrategy getEvictStrategy(EvictStrategyType strategyType,
      StorageTier storageTier) {
    boolean isLastTier = storageTier.isLastTier();
    switch (strategyType) {
      case LRU:
        return new EvictLRU(isLastTier, storageTier);
      case PARTIAL_LRU:
        return new EvictPartialLRU(isLastTier, storageTier);
      case LRFU:
        return new EvictLRFU(isLastTier, storageTier);
      default:
        return new EvictLRU(isLastTier, storageTier);
    }
  }

  private EvictStrategies() {}
}
