package org.x4444.revivermx;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <p>
 * Simple single thread server.
 * </p>
 * <p>
 * Plate requests should be added to request Queue
 * </p>
 * <p>
 * Responses should be read from response map using plateId as the key
 * </p>
 */
public class Server implements Runnable {

  private Queue<MyRequest> reqQueue = new LinkedList<MyRequest>();
  private HashMap<Integer, MyResponse> respMap = new HashMap<Integer, MyResponse>();

  private HashMap<Integer, String> tagMap = new HashMap<Integer, String>();
  private HashMap<String, String> locationMap = new HashMap<String, String>();

  private ServerStat stat = new ServerStat();

  public Server() {
    // add plateIds and corresponding tags to tagMap
    tagMap.put(1, "ABC-001");
    tagMap.put(2, "BBC-002");
    tagMap.put(3, "CBC-003");
    tagMap.put(4, "DBC-004");
    tagMap.put(5, "EBC-005");
    tagMap.put(6, "FBC-006");
    tagMap.put(7, "GBC-007");
    tagMap.put(8, "HBC-008");
    tagMap.put(9, "IBC-009");
    tagMap.put(10, "JBC-010");

    // init location-to-message dictionary
    locationMap.put(Location.HOME_LOT, "Ready to roll!");
    locationMap.put(Location.DOWNTOWN_PARKING, "Check me out");
    locationMap.put(Location.UPTOWN_PARKING, "Rent me");
    locationMap.put(Location.OUT_OF_BOUNDS, "Help! I'm lost");
  }

  /**
   * Start server
   */
  public void start() {
    Thread t = new Thread(this);
    t.start();
  }

  /**
   * Start 60 sec timer to print server statistics
   */
  protected void startStatTimer() {
    Timer t = new Timer();
    t.schedule(new TimerTask() {

      @Override
      public void run() {
        System.out.println(stat.getAndReset());
      }
    }, 60000, 60000);
  }

  @Override
  public void run() {
    // start stat timer
    startStatTimer();
    // infinite loop
    // reads incoming requests, processes them and writes response to response
    // map
    synchronized (this) {
      while (true) {
        MyRequest req;
        while ((req = reqQueue.poll()) != null) {
          MyResponse resp = process(req);
          respMap.put(req.getPlateId(), resp);
        }
        if (respMap.size() > 0) {
          notifyAll();
        }
        try {
          wait();
        } catch (InterruptedException e) {
          // ignore
        }
      }
    }
  }

  /**
   * This method processes incoming request
   * <p>
   * There are two types of requests expected:
   * </p>
   * <ul>
   * <li>to get Tag for plateId</li>
   * <li>to get Message for location</li>
   * </ul>
   * Response might contain one of the following: tag, message or error message
   * 
   * @param req
   *          Incoming request
   * @return - Response
   */
  protected MyResponse process(MyRequest req) {
    MyResponse resp = new MyResponse();
    int plateId = req.getPlateId();
    MyRequest.Type type = req.getType();
    if (type == null) {
      resp.setErrorMsg("Request type is null for plateId: " + plateId);
    } else {
      switch (type) {
      // tag request - get Tag for plateId
      case TAG:
        String tag = tagMap.get(plateId);
        if (tag == null) {
          resp.setErrorMsg("Tag not found for plateId: " + plateId);
        } else {
          resp.setTag(tag);
        }
        break;
      // message request - get message for location
      case MSG:
        String location = req.getLocation();
        if (location == null) {
          resp.setErrorMsg("Location is null for plateId: " + plateId);
        } else {
          String msg = locationMap.get(location);
          if (msg == null) {
            resp.setErrorMsg("Unknown location: " + location + " for plateId: " + plateId);
          } else {
            resp.setMsg(msg);
            // increment message served counter
            stat.incMsgServedCnt();
          }
        }
        break;
      default:
        resp.setErrorMsg("Unknown request type: " + type + " for plateId: " + plateId);
        break;
      }
    }
    // increment plates served counter
    stat.incPlateServedCnt();
    return resp;
  }

  /**
   * This method returns response for plateId and removes the response from
   * response map
   * 
   * @param plateId
   *          Plate ID
   * @return
   */
  public synchronized MyResponse getAndRemoveResponse(int plateId) {
    return respMap.remove(plateId);
  }

  /**
   * This method adds incoming request to request queue
   * 
   * @param req
   *          Request
   */
  public synchronized void addRequest(MyRequest req) {
    reqQueue.add(req);
    notifyAll();
  }
}
