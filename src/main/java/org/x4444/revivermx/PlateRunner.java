package org.x4444.revivermx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class changes plate location and speed
 * <p>
 * Scenario is the following
 * </p>
 * <ol>
 * <li>plate starts at HomeLot</li>
 * <li>plate stays at the location for stayDurationSec sec</li>
 * <li>plate moving to next random location (except HomeLot) for runDurationSec
 * sec</li>
 * <li>plate stays at the location for stayDurationSec sec</li>
 * <li>repeat last 2 steps 2 times</li>
 * <li>plate moving to HomeLot for runDurationSec sec</li>
 * <li>goto 2</li>
 * </ol>
 * 
 */
public class PlateRunner implements Runnable {

  private Plate plate;
  private int stayDurationMs;
  private int runDurationMs;
  private Random rnd = new Random();
  private List<Integer> locationIds = new ArrayList<Integer>();

  public PlateRunner(Plate plate, int stayDurationSec, int runDurationSec) {
    this.plate = plate;
    this.stayDurationMs = stayDurationSec * 1000;
    this.runDurationMs = runDurationSec * 1000;
    for (int i = 1; i < Location.ALL.length; i++) {
      locationIds.add(i);
    }
  }

  /**
   * Starts PlateRunner
   */
  public void start() {
    new Thread(this).start();
  }

  @Override
  public void run() {
    // init the plate
    plate.boot();

    while (true) {
      // visit locations in random order
      Collections.shuffle(locationIds);
      for (int i = 0; i < Location.ALL.length; i++) {
        String location = i == 0 ? Location.HOME_LOT : Location.ALL[locationIds.get(i - 1)];
        // stop
        plate.setLocation(location);
        plate.setSpeed(0);
        // stay at location for stayDurationMs
        try {
          Thread.sleep(stayDurationMs);
        } catch (InterruptedException e) {
          // ignore
        }

        // go
        plate.setSpeed(rnd.nextInt(75) + 1);

        // move to next location for runDurationMs
        try {
          Thread.sleep(runDurationMs);
        } catch (InterruptedException e) {
          // ignore
        }
      }
    }
  }
}
