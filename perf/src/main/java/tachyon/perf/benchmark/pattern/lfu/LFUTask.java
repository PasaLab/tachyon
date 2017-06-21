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

package tachyon.perf.benchmark.pattern.lfu;

import tachyon.perf.basic.PerfTaskContext;
import tachyon.perf.benchmark.SimpleTask;
import tachyon.perf.conf.PerfConf;

/**
 * Created by shupeng on 2016/1/19.
 */
public class LFUTask extends SimpleTask {
    @Override
    public String getCleanupDir() {
        return PerfConf.get().WORK_DIR + "/LFU";
    }

    @Override
    protected boolean setupTask(PerfTaskContext taskContext) {
        String workDir = PerfConf.get().WORK_DIR + "/LFU/" + mId;
        mTaskConf.addProperty("work.dir", workDir);
        LOG.info("Work dir " + workDir);
        return true;
    }
}
