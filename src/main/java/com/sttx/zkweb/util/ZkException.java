package com.sttx.zkweb.util;


public class ZkException extends Exception {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private String errCode;

  public ZkException() {
    super();
  }

  public ZkException(String message) {
    super(message);
  }

  public String getErrCode() {
    return errCode;
  }

  public void setErrCode(String errCode) {
    this.errCode = errCode;
  }


}
