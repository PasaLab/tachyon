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

import alluxio.worker.block.evictor.BlockTransferInfo;

import com.google.common.base.Preconditions;

import java.util.List;

/**
 * This class provide information about blocks to be promoted from lower tiers to the higher tiers.
 */
public class PromotePlan {
  /** A list of block tranfer information. */
  private final List<BlockTransferInfo> mToPromote;

  /**
   * Create an instance of {@link PromotePlan}.
   *
   * @param toPromote a list of block transfer information
   */
  public PromotePlan(List<BlockTransferInfo> toPromote) {
    mToPromote = Preconditions.checkNotNull(toPromote);
  }

  /**
   * @return a list of block transfer information from lower tiers to higher tiers
   */
  public List<BlockTransferInfo> toPromote() {
    return mToPromote;
  }

  /**
   * Whether the plan is empty.
   *
   * @return empty or not empty
   */
  public boolean isEmpty() {
    return mToPromote.isEmpty();
  }
}
