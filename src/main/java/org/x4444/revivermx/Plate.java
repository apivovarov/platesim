package org.x4444.revivermx;

public class Plate {

  /**
   * Possible plate screen statuses
   * 
   */
  public static enum Screen {
    TAG, MSG, NONE
  }

  private int plateId;
  private String tag;
  private String location;
  private int speed;
  private Screen display = Screen.NONE;

  private MockHttpClient httpClient;

  public Plate(int plateId, Server server) {
    this.plateId = plateId;
    httpClient = new MockHttpClient(server);
  }

  /**
   * This method initialize the plate
   * <p>
   * The plate should contact server to get Tag for plateId
   * </p>
   */
  public void boot() {
    MyRequest req = new MyRequest(MyRequest.Type.TAG, plateId);
    MyResponse resp = httpClient.send(req);
    tag = resp.getTag();
    System.out.println("Plate " + plateId + " init is done, tag: " + tag);
  }

  public int getSpeed() {
    return speed;
  }

  /**
   * 
   * Set plate speed
   */
  public void setSpeed(int speed) {
    this.speed = speed;
    handleSpeedEvent();
  }

  /**
   * <ul>
   * <li>if speed is 0 then display Message for plate current location. Plate
   * should contact server to get message for its location.</li>
   * <li>if speed is greater than 0 then display the Tag</li>
   * <ul>
   */
  protected void handleSpeedEvent() {
    if (speed == 0 && display != Screen.MSG) {
      MyRequest req = new MyRequest(MyRequest.Type.MSG, plateId, location);
      MyResponse resp = httpClient.send(req);
      String msg = resp.getMsg();
      showMsg(msg);
    } else if (speed > 0 && display != Screen.TAG) {
      showTag();
    }
  }

  /**
   * Show message
   * 
   * @param msg
   *          Message
   */
  protected void showMsg(String msg) {
    System.out.println("Plate " + plateId + " at " + location + ": " + msg);
    display = Screen.MSG;
  }

  /**
   * show Tag
   */
  protected void showTag() {
    System.out.println("Plate " + plateId + " is moving: " + tag);
    display = Screen.TAG;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public Screen getDisplay() {
    return display;
  }
}
