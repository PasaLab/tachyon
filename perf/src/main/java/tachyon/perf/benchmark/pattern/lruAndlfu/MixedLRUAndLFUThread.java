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

import java.io.IOException;
import java.util.List;

import tachyon.perf.PerfConstants;
import tachyon.perf.basic.PerfThread;
import tachyon.perf.basic.TaskConfiguration;
import tachyon.perf.benchmark.ListGenerator;
import tachyon.perf.benchmark.Operators;
import tachyon.perf.conf.PerfConf;
import tachyon.perf.fs.PerfFS;

/**
 * Created by shupeng on 2016/1/19.
 */
public class MixedLRUAndLFUThread extends PerfThread {
  private PerfFS mFileSystem;
  private String mWorkDir;
  private int mLRUFilesNum;
  private int mLFUFilesNum;
  private int mLFUColdFilesNum;
  private int mIterations;
  private int mLRUIterations;
  private int mLFUIterations;
  private int mBufferSize;
  private int mBlockSize;
  private long mFileLength;
  private String mReadType;
  private String mWriteType;

  private double mTotalTime;
  private double mLRUTime;
  private double mLFUTime;

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

  public double getTotalTime() {
    return 1.0 * mTotalTime;
  }

  public double getLRUTime() { return 1.0 * mLRUTime; }

  public double getLFUTime() { return 1.0 * mLFUTime; }

  public boolean getSuccess() {
    return mSuccess;
  }

  @Override
  public void run() {
    mSuccess = true;
    long tTimeMs = System.currentTimeMillis();
    mLRUTime = 0;
    mLFUTime = 0;
    for (int iter = 0; iter < mIterations; iter ++) {
      for (int i = 0; i < mLRUIterations; i ++) {
        String dataDir = mWorkDir + "/data/" + iter + "/LRU/" + i;
        for (int j = 0; j < mLRUFilesNum; j ++) {
          try {
            String fileName = mId + "-" + j;
            Operators.writeSingleFile(mFileSystem, dataDir + "/" + fileName, mFileLength, mBlockSize,
                    mBufferSize, mWriteType);
          } catch (IOException e) {
            LOG.error("Failed to write LRU file -" + j + " in " + iter + " iteration and " + i + " LRU iteration", e);
            mSuccess = false;
            return;
          }
        }
        try {
          long start = System.currentTimeMillis();
          List<String> candidates = mFileSystem.listFullPath(dataDir);
          List<String> readFiles = ListGenerator.generateSequenceReadFiles(mId,
                  PerfConf.get().THREADS_NUM, mLRUFilesNum, candidates);
          for (int k = readFiles.size() - 1; k >= 0; k --) {
            Operators.readSingleFile(mFileSystem, readFiles.get(k), mBufferSize, mReadType);
          }
          long end = System.currentTimeMillis();
          mLRUTime += end - start;
        } catch (IOException e) {
          LOG.error("Fail to read files!", e);
          mSuccess = false;
          return;
        }
      }
      String dataDir = mWorkDir + "/data/" + iter + "/LFU/";
      for (int j = 0; j < mLFUFilesNum; j ++) {
        try {
          String fileName = mId + "-" + j;
          Operators.writeSingleFile(mFileSystem, dataDir + "/" + fileName, mFileLength, mBlockSize,
                  mBufferSize, mWriteType);
        } catch (IOException e) {
          LOG.error("Failed to write LFU file -" + j + " in " + iter + " iteration", e);
          mSuccess = false;
          return;
        }
      }
      try {
        List<String> candidates = mFileSystem.listFullPath(dataDir);
        List<String> readFiles = ListGenerator.generateSequenceReadFiles(mId,
                PerfConf.get().THREADS_NUM, mLFUFilesNum, candidates);
        for (int j = 0; j < mLFUIterations; j ++) {
          long start = System.currentTimeMillis();
          for (int k = 0; k < readFiles.size(); k ++) {
            Operators.readSingleFile(mFileSystem, readFiles.get(k), mBufferSize, mReadType);
          }
          long end = System.currentTimeMillis();
          mLFUTime += end - start;
          for (int k = 0; k < mLFUColdFilesNum; k ++) {
        	  try {
        		  String fileName = mId + "-" + k;
        		  Operators.writeSingleFile(mFileSystem, dataDir + "cold/" + j + "/" + fileName, mFileLength, mBlockSize,
                          mBufferSize, mWriteType);
        	  } catch (IOException e) {
        		  LOG.error("Failed to write LFU cold files", e);
        		  mSuccess = false;
        		  return;
        	  }
          }
        }
      } catch (IOException e) {
        LOG.error("Fail to read files!", e);
        mSuccess = false;
        return;
      }
    }
    tTimeMs = System.currentTimeMillis() - tTimeMs;
    mTotalTime = tTimeMs;
  }

  @Override
  public boolean setupThread(TaskConfiguration taskConf) {
    mLRUFilesNum = taskConf.getIntProperty("lru.files.per.thread");
    mLFUFilesNum = taskConf.getIntProperty("lfu.files.per.thread");
    mLFUColdFilesNum = taskConf.getIntProperty("lfu.cold.files.per.thread");
    mLRUIterations = taskConf.getIntProperty("lru.iterations");
    mLFUIterations = taskConf.getIntProperty("lfu.iterations");
    mIterations = taskConf.getIntProperty("iterations");
    mBufferSize = taskConf.getIntProperty("buffer.size.bytes");
    mBlockSize = taskConf.getIntProperty("block.size.bytes");
    mFileLength = taskConf.getLongProperty("file.length.bytes");
    mWorkDir = taskConf.getProperty("work.dir");
    mReadType = taskConf.getProperty("read.type");
    mWriteType = taskConf.getProperty("write.type");
    try {
      mFileSystem = PerfConstants.getFileSystem();
    } catch (IOException e) {
      LOG.error("Failed to setup thread, task " + mTaskId + " - thread " + mId, e);
      mSuccess = false;
      return false;
    }
    mSuccess = false;
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
