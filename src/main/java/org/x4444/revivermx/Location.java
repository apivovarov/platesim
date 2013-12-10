package org.x4444.revivermx;

/**
 * Possible Locations
 * 
 */
public interface Location {
  String HOME_LOT = "HomeLot";
  String DOWNTOWN_PARKING = "DowntownParking";
  String UPTOWN_PARKING = "UptownParking";
  String OUT_OF_BOUNDS = "OutOfBounds";

  String[] ALL = new String[] { HOME_LOT, DOWNTOWN_PARKING, UPTOWN_PARKING, OUT_OF_BOUNDS };
}
