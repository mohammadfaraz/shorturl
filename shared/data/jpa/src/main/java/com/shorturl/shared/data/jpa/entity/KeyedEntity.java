package com.shorturl.shared.data.jpa.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

@MappedSuperclass
public class KeyedEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Date creationTime;

  private Date lastUpdated;

  public Long getId() {
    return id;
  }

  public Date getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(Date creationTime) {
    this.creationTime = creationTime;
  }

  public Date getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(Date lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  @PreUpdate
  void updateAuditData() {
    this.lastUpdated = new Date();
  }

  @PrePersist
  void setAuditData() {
    this.creationTime = new Date();
  }

  @Override
  public String toString() {
    return "KeyedEntity [" + "creationTime=" + creationTime + ", id=" + id + ", lastUpdated="
        + lastUpdated + ']';
  }
}
