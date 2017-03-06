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

package alluxio.worker.block.promote;

import alluxio.Configuration;
import alluxio.PropertyKey;
import alluxio.util.CommonUtils;
import alluxio.worker.block.BlockMetadataManagerView;
import alluxio.worker.block.allocator.Allocator;

import com.google.common.base.Throwables;

import javax.annotation.concurrent.ThreadSafe;

/**
 * Interface for promote policy in Alluxio.
 */
public interface Promote {
  /**
   * Factory for {@link Promote}.
   */
  @ThreadSafe
  class Factory {

    private Factory() {} // prevent instantiation

    /**
     * Factory for {@link Promote}.
     *
     * @param view {@link BlockMetadataManagerView} to pass to {@link Promote}
     * @param allocator an allocation policy
     * @return the generated {@link Promote}
     */
    public static Promote create(BlockMetadataManagerView view, Allocator allocator) {
      try {
        return CommonUtils.createNewClassInstance(
            Configuration.<Promote>getClass(PropertyKey.WORKER_PROMOTE_CLASS),
            new Class[] {BlockMetadataManagerView.class, Allocator.class},
            new Object[] {view, allocator});
      } catch (Exception e) {
        throw Throwables.propagate(e);
      }
    }
  }

  /**
   * Reorganize the distribution of blocks among the tiers.
   *
   * @param view a view of block metadata information
   * @return a promote plan
   */
  PromotePlan reorganizeBlocks(BlockMetadataManagerView view);

  /**
   * Reorganize the distribution of blocks among the tiers.
   *
   * @param view a view of block metadata information
   * @return a promote plan
   */
  PromotePlan promoteWithView(BlockMetadataManagerView view);
}
