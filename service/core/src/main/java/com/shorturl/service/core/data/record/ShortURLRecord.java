package com.shorturl.service.core.data.record;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import java.util.Date;

import com.shorturl.shared.data.jpa.entity.KeyedEntity;
/*, uniqueConstraints = {
    @UniqueConstraint(columnNames = { "old_url", "custom_id" }),
}*/
@Entity
@Table(name = "url_details")
public class ShortURLRecord extends KeyedEntity {

  @Lob
  @Column(name = "old_url", nullable = false, unique = true)
  private String oldUrl;

  @Column(name = "custom_id",nullable = false, unique = true)
  private Long customId;

  @Column(nullable = false)
  private Date expirationTime;

  @Column(nullable = false)
  private boolean active = true;

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

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

  public Long getCustomId() {
    return customId;
  }

  public void setCustomId(Long customId) {
    this.customId = customId;
  }

  @Override
  public String toString() {
    return "ShortURLRecord [" + "active=" + active + ", customId=" + customId + ", expirationTime="
        + expirationTime + ", oldUrl='" + oldUrl + '\'' + ']';
  }
}
