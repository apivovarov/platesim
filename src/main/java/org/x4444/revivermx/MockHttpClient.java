package org.x4444.revivermx;

/**
 * Mock http Client which works with simple single thread
 * {@link org.x4444.revivermx.Server}
 * 
 */
public class MockHttpClient {

  private Server server;

  public MockHttpClient(Server server) {
    this.server = server;
  }

  /**
   * <p>
   * Sends request to the server and returns response from the server
   * </p>
   * 
   * <ol>
   * <li>client adds request to server request queue and notifies All who
   * monitors server instance</li>
   * <li>the client waits for server to process the request</li>
   * <li>now two options possible:</li>
   * <li>option 1: server processes request, puts response to response map and
   * notifies All. In this case this client response should be in response map
   * with corresponding plateId
   * <li>this client is notified and reads response from the response map</li>
   * <li>option 2: another client adds request to server request queue and
   * notifies All. In this case response map will not have this client response</li>
   * <li>this client should wait again until the server notifies itself (option
   * 1)</li>
   * </ol>
   * 
   * @param req
   *          {@link org.x4444.revivermx.MyRequest}
   * @return {@link org.x4444.revivermx.MyResponse}
   */
  public MyResponse send(MyRequest req) {
    synchronized (server) {
      // add request to server request queue and notify All
      server.addRequest(req);
      server.notifyAll();

      int plateId = req.getPlateId();
      MyResponse resp = null;
      // if response is null wait for server to process request and notify All
      while (resp == null) {
        // wait for server processing request
        try {
          server.wait();
        } catch (InterruptedException e) {

        }
        // get response from response map
        resp = server.getAndRemoveResponse(plateId);
      }

      return resp;
    }
  }
}
