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

package tachyon.perf.benchmark.pattern.lruAndlfu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tachyon.perf.basic.PerfThread;
import tachyon.perf.benchmark.SimpleTaskContext;

/**
 * Created by shupeng on 2016/1/19.
 */
public class MixedLRUAndLFUTaskContext extends SimpleTaskContext {
    @Override
    public void setFromThread(PerfThread[] threads) {
        mAdditiveStatistics = new HashMap<String, List<Double>>(3);
        List<Double> totalTime = new ArrayList<Double>(threads.length);
        List<Double> lruTime = new ArrayList<Double>(threads.length);
        List<Double> lfuTime = new ArrayList<Double>(threads.length);
        for (PerfThread thread : threads) {
            if (!((MixedLRUAndLFUThread) thread).getSuccess()) {
                mSuccess = false;
            }
            lruTime.add(((MixedLRUAndLFUThread) thread).getLRUTime());
            lfuTime.add(((MixedLRUAndLFUThread) thread).getLFUTime());
            totalTime.add(((MixedLRUAndLFUThread) thread).getTotalTime());
        }
        mAdditiveStatistics.put("lruTime(s)", lruTime);
        mAdditiveStatistics.put("lfuTime(s)", lfuTime);
        mAdditiveStatistics.put("totalTime(s)", totalTime);
    }
}
