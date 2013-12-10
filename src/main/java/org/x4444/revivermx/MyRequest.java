package org.x4444.revivermx;

import java.io.Serializable;

/**
 * Request
 * <p>
 * There are two types of requests
 * </p>
 * <ol>
 * <li>request to get Tag by plateId</li>
 * <li>request to get Message by plate location</li>
 * </ol>
 * Each request should have request type and plateId
 * 
 */
public class MyRequest implements Serializable {

  private static final long serialVersionUID = -8559605231722436701L;

  public static enum Type {
    TAG, MSG;
  }

  private Type type;
  private int plateId;
  private String location;

  /**
   * Default constructor
   */
  public MyRequest() {
  }

  public MyRequest(Type type, int plateId) {
    this.type = type;
    this.plateId = plateId;
  }

  public MyRequest(Type type, int plateId, String location) {
    this(type, plateId);
    this.location = location;
  }

  public int getPlateId() {
    return plateId;
  }

  public void setPlateId(int plateId) {
    this.plateId = plateId;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Type getType() {
    return type;
  }
}
