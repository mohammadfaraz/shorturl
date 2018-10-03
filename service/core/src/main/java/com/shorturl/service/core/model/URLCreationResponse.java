package com.shorturl.service.core.model;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class URLCreationResponse {

  private HttpStatus httpStatus;

  private String alias;

  private Date expirationTime;

  private Date creationTime;

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }

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

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  @Override
  public String toString() {
    return "URLCreationResponse [" + "alias='" + alias + '\'' + ", creationTime=" + creationTime
        + ", expirationTime=" + expirationTime + ", httpStatus=" + httpStatus + ']';
  }
}
