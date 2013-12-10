package org.x4444.revivermx;

import java.io.Serializable;

/**
 * Response
 * <p>
 * Response might have one of the following
 * </p>
 * <ol>
 * <li>Tag</li>
 * <li>Message</li>
 * <li>Error Message</li>
 * </ol>
 * 
 */
public class MyResponse implements Serializable {

  private static final long serialVersionUID = 2582889557225951226L;

  private String msg;
  private String tag;

  private String errorMsg;

  /**
   * Default constructor
   */
  public MyResponse() {
  }

  public MyResponse(String msg) {
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }
}
