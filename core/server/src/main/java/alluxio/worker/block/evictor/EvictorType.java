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

package alluxio.worker.block.evictor;

/**
 * Common type of evictors.
 */
public enum EvictorType {
  /**
   * Corresponding to LRUEvictor.
   */
  LRU(0),

  /**
   * Corresponding to LRFUEvictor.
   */
  LRFU(1),

  /**
   * Corresponding to LIRSEvictor.
   */
  LIRS(2),

  /**
   * Corresponding to ARCEvictor.
   */
  ARC(3);

  private final int mValue;

  EvictorType(int value) {
    mValue = value;
  }

  /**
   * @return the Evictor type value
   */
  public int getValue() {
    return mValue;
  }

  /**
   * Get EvictorType of value.
   *
   * @param value int value
   * @return EvictorType corresponding to the value
   */
  public static EvictorType getEvictorType(int value) {
    switch (value) {
      case 0:
        return EvictorType.LRU;
      case 1:
        return EvictorType.LRFU;
      case 2:
        return EvictorType.LIRS;
      case 3:
        return EvictorType.ARC;
      default:
        return null;
    }
  }

  /**
   * Get {@link EvictorType} of {@link Evictor}.
   *
   * @param evictor a instance of {@link Evictor}
   * @return {@link EvictorType}
   */
  public static EvictorType getEvictorType(Evictor evictor) {
    if (evictor instanceof ARCEvictor) {
      return EvictorType.ARC;
    } else if (evictor instanceof LIRSEvictor) {
      return EvictorType.LIRS;
    } else if (evictor instanceof LRFUEvictor) {
      return EvictorType.LRFU;
    } else if (evictor instanceof LRUEvictor) {
      return EvictorType.LRU;
    } else {
      return null;
    }
  }
}
