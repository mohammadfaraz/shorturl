package com.shorturl.service.core.model;

import java.util.Date;

import com.shorturl.service.core.data.record.ShortURLRecord;

public class ShortURLObjectCache {

  private Date accessedOn;

  private ShortURLRecord shortURLRecord;

  public Date getAccessedOn() {
    return accessedOn;
  }

  public void setAccessedOn(Date accessedOn) {
    this.accessedOn = accessedOn;
  }

  public ShortURLRecord getShortURLRecord() {
    return shortURLRecord;
  }

  public void setShortURLRecord(ShortURLRecord shortURLRecord) {
    this.shortURLRecord = shortURLRecord;
  }

  @Override
  public String toString() {
    return "ShortURLObjectCache [" + "accessedOn=" + accessedOn + ", shortURLRecord="
        + shortURLRecord + ']';
  }
}
