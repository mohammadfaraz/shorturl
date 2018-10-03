package com.shorturl.service.core.model;

import java.util.Date;

public class URLCreationRequest {

  private Date expirationTime;

  private String oldUrl;

  public Date getExpirationTime() {
    return expirationTime;
  }

  public void setExpirationTime(Date expirationTime) {
    this.expirationTime = expirationTime;
  }

  public String getOldUrl() {
    return oldUrl;
  }

  public void setOldUrl(String oldUrl) {
    this.oldUrl = oldUrl;
  }

  @Override
  public String toString() {
    return "URLCreationRequest [" + "expirationTime=" + expirationTime + ", oldUrl='" + oldUrl
        + '\'' + ']';
  }
}
