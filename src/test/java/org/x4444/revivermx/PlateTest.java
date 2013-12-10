package org.x4444.revivermx;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for Plate
 */
public class PlateTest {

  @Test
  public void testApp() {
    Server server = new Server();
    server.start();
    Plate plate = new Plate(1, server);
    plate.boot();
    plate.setLocation(Location.HOME_LOT);
    Assert.assertEquals(Plate.Screen.NONE, plate.getDisplay());
    plate.setSpeed(0);
    Assert.assertEquals(Plate.Screen.MSG, plate.getDisplay());
    plate.setSpeed(10);
    Assert.assertEquals(Plate.Screen.TAG, plate.getDisplay());
    plate.setSpeed(0);
    Assert.assertEquals(Plate.Screen.MSG, plate.getDisplay());
    plate.setSpeed(10);
    Assert.assertEquals(Plate.Screen.TAG, plate.getDisplay());
  }
}
