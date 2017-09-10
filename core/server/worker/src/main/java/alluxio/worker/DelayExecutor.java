package alluxio.worker;

import org.omg.CORBA.BAD_CONTEXT;

/**
 * Created by lenovo on 2017/9/10.
 */
public class DelayExecutor {
  private long mDelayMills;
  private int mUserNum;
  private final int BAND_WIDTH = ;

  public DelayExecutor(int users, long fileSize) {
    mUserNum = users;
    mDelayMills = (fileSize / BAND_WIDTH) / (users + 1);
  }

  public void delay() {
    try {
      Thread.sleep(sleepMillis);
    } catch (InterruptedException e) {
      //TODO(li)
    }

  }
}
