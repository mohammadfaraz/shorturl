package com.shorturl.service.core.model;

import java.util.Date;

public class ShortURLDetails {

  private String url;

  private Date expirationTime;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Date getExpirationTime() {
    return expirationTime;
  }

  public void setExpirationTime(Date expirationTime) {
    this.expirationTime = expirationTime;
  }

  @Override
  public String toString() {
    return "ShortURLDetails [" + "expirationTime=" + expirationTime + ", url='" + url + '\'' + ']';
  }
}
