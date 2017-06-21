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

package tachyon.perf.benchmark.pattern.lru;

import tachyon.perf.basic.PerfThread;
import tachyon.perf.benchmark.SimpleTaskContext;
import tachyon.perf.benchmark.pattern.loop.LoopThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shupeng on 2016/1/19.
 */
public class LRUTaskContext extends SimpleTaskContext {
    @Override
    public void setFromThread(PerfThread[] threads) {
        mAdditiveStatistics = new HashMap<String, List<Double>>(4);
        List<Double> basicThroughputs = new ArrayList<Double>(threads.length);
        List<Double> readThroughputs = new ArrayList<Double>(threads.length);
        List<Double> basicWriteTime = new ArrayList<Double>(threads.length);
        List<Double> readTime = new ArrayList<Double>(threads.length);
        for (PerfThread thread : threads) {
            if (!((LRUThread) thread).getSuccess()) {
                mSuccess = false;
            }
            basicThroughputs.add(((LRUThread) thread).getBasicWriteThroughput());
            readThroughputs.add(((LRUThread) thread).getReadThroughput());
            basicWriteTime.add(((LRUThread) thread).getBasicWriteTime());
            readTime.add(((LRUThread) thread).getReadTime());
        }
        mAdditiveStatistics.put("BasicWriteThroughput(MB/s)", basicThroughputs);
        mAdditiveStatistics.put("ReadThroughput(MB/s)", readThroughputs);
        mAdditiveStatistics.put("BasicWriteTime(s)", basicWriteTime);
        mAdditiveStatistics.put("ReadTime(s)", readTime);
    }
}
