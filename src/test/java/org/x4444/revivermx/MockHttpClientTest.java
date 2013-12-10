package org.x4444.revivermx;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for MockHttpClient
 */
public class MockHttpClientTest {

  @Test
  public void testTagRequest() {
    Server server = new Server();
    server.start();
    MockHttpClient client = new MockHttpClient(server);
    MyRequest req = new MyRequest(MyRequest.Type.TAG, 1);
    MyResponse resp = client.send(req);
    Assert.assertEquals("ABC-001", resp.getTag());
    Assert.assertNull(resp.getMsg());
    Assert.assertNull(resp.getErrorMsg());
  }

  @Test
  public void testMsgRequest() {
    Server server = new Server();
    server.start();
    MockHttpClient client = new MockHttpClient(server);
    MyRequest req = new MyRequest(MyRequest.Type.MSG, 1, Location.HOME_LOT);
    MyResponse resp = client.send(req);
    Assert.assertEquals("Ready to roll!", resp.getMsg());
    Assert.assertNull(resp.getTag());
    Assert.assertNull(resp.getErrorMsg());
  }

  @Test
  public void testBadTagRequest() {
    Server server = new Server();
    server.start();
    MockHttpClient client = new MockHttpClient(server);
    MyRequest req = new MyRequest(MyRequest.Type.TAG, 20);
    MyResponse resp = client.send(req);
    Assert.assertNull(resp.getMsg());
    Assert.assertNull(resp.getTag());
    Assert.assertNotNull(resp.getErrorMsg());
  }

  @Test
  public void testBadMsgRequest() {
    Server server = new Server();
    server.start();
    MockHttpClient client = new MockHttpClient(server);
    MyRequest req = new MyRequest(MyRequest.Type.MSG, 1, "City");
    MyResponse resp = client.send(req);
    Assert.assertNull(resp.getMsg());
    Assert.assertNull(resp.getTag());
    Assert.assertNotNull(resp.getErrorMsg());
  }

  @Test
  public void testBadRequestType() {
    Server server = new Server();
    server.start();
    MockHttpClient client = new MockHttpClient(server);
    MyRequest req = new MyRequest(null, 1);
    MyResponse resp = client.send(req);
    Assert.assertNull(resp.getMsg());
    Assert.assertNull(resp.getTag());
    Assert.assertNotNull(resp.getErrorMsg());
  }
}
