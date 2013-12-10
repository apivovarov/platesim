package org.x4444.revivermx;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Server Statistics
 * <ul>
 * <li>Plate Server counter</li>
 * <li>Message Served counter</li>
 * </ul>
 */
public class ServerStat {

  private AtomicInteger plateServedCnt = new AtomicInteger();
  private AtomicInteger msgServedCnt = new AtomicInteger();

  public ServerStat() {
  }

  /**
   * 
   * @return Plates Served counter value
   */
  public int getPlateServedCnt() {
    return plateServedCnt.get();
  }

  /**
   * 
   * @return Messages Served counter value
   */
  public int getMsgServedCnt() {
    return msgServedCnt.get();
  }

  /**
   * Resets the counters
   */
  public void reset() {
    plateServedCnt.set(0);
    msgServedCnt.set(0);
  }

  /**
   * Atomically increments Plates Served counter
   * 
   * @return the updated counter value
   */
  public int incPlateServedCnt() {
    return plateServedCnt.incrementAndGet();
  }

  /**
   * Atomically increments Messages Served counter
   * 
   * @return the updated counter value
   */
  public int incMsgServedCnt() {
    return msgServedCnt.incrementAndGet();
  }

  /**
   * Gets statistics and resets the counters
   * 
   * @return message containing server statistics
   */
  public String getAndReset() {
    int p = plateServedCnt.getAndSet(0);
    int m = msgServedCnt.getAndSet(0);
    return "Server Stat: served plates: " + p + ", served messages: " + m;
  }
}
