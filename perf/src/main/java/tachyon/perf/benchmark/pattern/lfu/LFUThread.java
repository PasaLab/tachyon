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

import tachyon.perf.PerfConstants;
import tachyon.perf.basic.PerfThread;
import tachyon.perf.basic.TaskConfiguration;
import tachyon.perf.benchmark.ListGenerator;
import tachyon.perf.benchmark.Operators;
import tachyon.perf.conf.PerfConf;
import tachyon.perf.fs.PerfFS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shupeng on 2016/1/19.
 */
public class LFUThread extends PerfThread {
    private PerfFS mFileSystem;
    private String mWorkDir;
    private int mIterations;
    private int mHotDataFilesNum;
    private int mHotDataReadTimes;
    private int mColdDataFilesNumPerIteration;
    private int mBufferSize;
    private int mBlockSize;
    private long mFileLength;
    private String mReadType;
    private String mWriteType;

    private double mBasicWriteTime;
    private double mReadTime;
    private double mTmpWriteTime;
    private double mWriteThroughput;
    private double mReadThroughput;
    private double mTmpWriteThroughput;
    private Boolean mSuccess;

    @Override
    public boolean cleanupThread(TaskConfiguration taskConf) {
        try {
            mFileSystem.close();
        } catch (IOException e) {
            LOG.warn("Error when close file system, task " + mTaskId + " - thread " + mId, e);
        }
        return true;
    }

    public void initSyncBarrier() {
        try {
            mFileSystem.create(mWorkDir + "/sync/" + mId);
        } catch (IOException e) {
            LOG.error("Create sync files failed!");
        }
    }

    public double getBasicWriteThroughput() {
        return mWriteThroughput;
    }

    public double getReadThroughput() {
        return mReadThroughput;
    }

    public double getTmpWriteThroughput() { return mTmpWriteThroughput; }

    public double getBasicWriteTime() {
        return mBasicWriteTime;
    }

    public double getReadTime() {
        return mReadTime;
    }

    public double getTmpWriteTime() {
        return mTmpWriteTime;
    }

    public boolean getSuccess() {
        return mSuccess;
    }

    @Override
    public void run() {
        long basicBytes = 0;
        long basicTimeMs = 0;
        long readBytes = 0;
        long readTimeMs = 0;
        long tmpWriteBytes = 0;
        long tmpWriteTimeMs = 0;
        mSuccess = true;
        String dataDir = mWorkDir + "/data";
        String tmpDir = mWorkDir + "/tmp";
        long tTimeMs = System.currentTimeMillis();
        for (int b = 0; b < mHotDataFilesNum; b ++) {
            try {
                String fileName = mId + "-" + b;
                Operators.writeSingleFile(mFileSystem, dataDir + "/" + fileName, mFileLength, mBlockSize, mBufferSize, mWriteType);
                basicBytes += mFileLength;
            } catch (IOException e) {
                LOG.error("Failed to write basic file", e);
                mSuccess = false;
                break;
            }
        }
        tTimeMs = System.currentTimeMillis() - tTimeMs;
        basicTimeMs += tTimeMs;

        try {
            syncBarrier();
        } catch (IOException e) {
            LOG.error("Error in Sync Barrier", e);
            mSuccess = false;
        }
        try {
            List<String> candidates = mFileSystem.listFullPath(dataDir);
            List<String> readFiles = ListGenerator.generateSequenceReadFiles(mId,
                    PerfConf.get().THREADS_NUM, mHotDataFilesNum, candidates);
            List<String> lastTmpFiles = new ArrayList<String>();
            int seed = 0;
            for (int i = 0; i < mIterations; i ++) {
                tTimeMs = System.currentTimeMillis();
                for (int k = 0; k < mHotDataReadTimes; k ++) {
                    for (int j = 0; j < readFiles.size(); j ++) {
                        readBytes += Operators.readSingleFile(mFileSystem, readFiles.get(j), mBufferSize, mReadType);
                    }
                }
                if (seed % 2 == 0) {
                    int len = Math.min(readFiles.size(), lastTmpFiles.size());
                    for (int j = 0; j < len; j ++) {
                        readBytes += Operators.readSingleFile(mFileSystem, lastTmpFiles.get(j), mBufferSize, mReadType);
                    }
                }
                tTimeMs = System.currentTimeMillis() - tTimeMs;
                readTimeMs += tTimeMs;
                lastTmpFiles.clear();
                try {
                    tTimeMs = System.currentTimeMillis();
                    for (int k = 0; k < mColdDataFilesNumPerIteration; k ++) {
                        String filename = mId + "-" + i + "-" + k;
                        Operators.writeSingleFile(mFileSystem, tmpDir + "/" + filename, mFileLength, mBlockSize, mBufferSize, mWriteType);
                        tmpWriteBytes += mFileLength;
                        if (seed % 2 == 0) {
                            lastTmpFiles.add(tmpDir+"/"+filename);
                        }
                    }
                    tTimeMs = System.currentTimeMillis() - tTimeMs;
                    seed ++;
                    tmpWriteTimeMs += tTimeMs;
                } catch (IOException e) {
                    LOG.error("Fail to write tmp files!");
                    mSuccess = false;
                }
            }
        } catch (IOException e) {
            LOG.error("Fail to read files!");
            mSuccess = false;
        }
        mBasicWriteTime = 1.0 * basicTimeMs / 1000;
        mReadTime = 1.0 * readTimeMs / 1000;
        mTmpWriteTime = 1.0 * tmpWriteTimeMs / 1000;
        mReadThroughput = (readBytes == 0) ? 0 : (readBytes / 1024.0 / 1024.0) / (readTimeMs / 1000.0);
        mWriteThroughput =
                (basicBytes == 0) ? 0 : (basicBytes / 1024.0 / 1024.0) / (basicTimeMs / 1000.0);
        mTmpWriteThroughput =
                (tmpWriteBytes == 0) ? 0 : (tmpWriteBytes / 1024.0 / 1024.0) / (tmpWriteTimeMs / 1000.0);

    }

    @Override
    public boolean setupThread(TaskConfiguration taskConf) {
        mIterations = taskConf.getIntProperty("iterations");
        mBufferSize = taskConf.getIntProperty("buffer.size.bytes");
        mFileLength = taskConf.getLongProperty("file.length.bytes");
        mBlockSize = taskConf.getIntProperty("block.size.bytes");
        mHotDataFilesNum = taskConf.getIntProperty("hot.data.files.num");
        mHotDataReadTimes = taskConf.getIntProperty("hot.files.read.times");
        mColdDataFilesNumPerIteration = taskConf.getIntProperty("cold.data.files.num.per.iteration");
        mWorkDir = taskConf.getProperty("work.dir");
        mReadType = taskConf.getProperty("read.type");
        mWriteType = taskConf.getProperty("write.type");
        try {
            mFileSystem = PerfConstants.getFileSystem();
            initSyncBarrier();
        } catch (IOException e) {
            LOG.error("Failed to setup thread, task " + mTaskId + " - thread " + mId, e);
            mSuccess = false;
            return false;
        }
        mSuccess = false;
        mBasicWriteTime = 0;
        mReadTime = 0;
        mTmpWriteTime = 0;
        mWriteThroughput = 0;
        mReadThroughput = 0;
        mTmpWriteThroughput = 0;
        return true;
    }

    private void syncBarrier() throws IOException {
        String syncDirPath = mWorkDir + "/sync";
        String syncFileName = "" + mId;
        mFileSystem.delete(syncDirPath + "/" + syncFileName, false);
        while (!mFileSystem.listFullPath(syncDirPath).isEmpty()) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                mSuccess = false;
                LOG.error("Error in Sync Barrier", e);
            }
        }
    }
}
