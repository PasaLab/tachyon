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

package tachyon.perf.benchmark.pattern.loop;

import tachyon.perf.PerfConstants;
import tachyon.perf.basic.PerfThread;
import tachyon.perf.basic.TaskConfiguration;
import tachyon.perf.benchmark.ListGenerator;
import tachyon.perf.benchmark.Operators;
import tachyon.perf.conf.PerfConf;
import tachyon.perf.fs.PerfFS;

import java.io.IOException;
import java.util.List;

/**
 * Created by shupeng on 2016/1/19.
 */
public class LoopThread extends PerfThread {
  private PerfFS mFileSystem;
  private String mWorkDir;
  private int mFilesNum;
  private int mReadTimes;
  private int mBufferSize;
  private int mBlockSize;
  private long mFileLength;
  private String mReadType;
  private String mWriteType;

  private double mWriteTime;
  private double mReadTime;
  private double mWriteThroughput;
  private double mReadThroughput;
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

  public double getBasicWriteTime() {
    return mWriteTime;
  }

  public double getReadThroughput() {
    return mReadThroughput;
  }

  public double getReadTime() {
    return mReadTime;
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
    mSuccess = true;
    String dataDir = mWorkDir + "/data";
    long tTimeMs = System.currentTimeMillis();
    for (int b = 0; b < mFilesNum; b ++) {
      try {
        String fileName = mId + "-" + b;
        Operators.writeSingleFile(mFileSystem, dataDir + "/" + fileName, mFileLength, mBlockSize,
            mBufferSize, mWriteType);
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
          PerfConf.get().THREADS_NUM, mFilesNum, candidates);
      for (int i = 0; i < mReadTimes; i ++) {
        tTimeMs = System.currentTimeMillis();
        for (int j = 0; j < readFiles.size(); j ++) {
          readBytes +=
              Operators.readSingleFile(mFileSystem, readFiles.get(j), mBufferSize, mReadType);
        }
        tTimeMs = System.currentTimeMillis() - tTimeMs;
        readTimeMs += tTimeMs;
      }
    } catch (IOException e) {
      LOG.error("Fail to read files!");
    }
    mReadTime = 1.0 * readTimeMs / 1000;
    mWriteTime = 1.0 * basicTimeMs / 1000;
    mReadThroughput = (readBytes == 0) ? 0 : (readBytes / 1024.0 / 1024.0) / (readTimeMs / 1000.0);
    mWriteThroughput =
        (basicBytes == 0) ? 0 : (basicBytes / 1024.0 / 1024.0) / (basicTimeMs / 1000.0);
  }

  @Override
  public boolean setupThread(TaskConfiguration taskConf) {
    mFilesNum = taskConf.getIntProperty("files.per.thread");
    mBufferSize = taskConf.getIntProperty("buffer.size.bytes");
    mBlockSize = taskConf.getIntProperty("block.size.bytes");
    mFileLength = taskConf.getLongProperty("file.length.bytes");
    mReadTimes = taskConf.getIntProperty("file.read.times");
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
    mWriteTime = 0;
    mReadTime = 0;
    mWriteThroughput = 0;
    mReadThroughput = 0;
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
