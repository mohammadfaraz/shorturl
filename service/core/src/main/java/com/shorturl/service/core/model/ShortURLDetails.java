package com.shorturl.service.core.model;

import java.util.Date;

public class ShortURLDetails {

  private String alias;

  private Date expirationTime;

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public Date getExpirationTime() {
    return expirationTime;
  }

  public void setExpirationTime(Date expirationTime) {
    this.expirationTime = expirationTime;
  }

  @Override
  public String toString() {
    return "ShortURLDetails [" + "alias='" + alias + '\'' + ", expirationTime=" + expirationTime
        + ']';
  }
}
