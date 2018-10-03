package com.shorturl.service.core.data.record;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import java.util.Date;

import com.shorturl.shared.data.jpa.entity.KeyedEntity;

@Entity
@Table(name = "url_details", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "old_url", "alias", "custom_id" }),
})
public class ShortURLRecord extends KeyedEntity {

  @Column(nullable = false, unique = true)
  private String oldUrl;

  @Column(nullable = false, unique = true)
  private String alias;

  @Column(name = "custom_id",nullable = false, unique = true)
  private Long customId;

  private Date expirationTime;

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

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public Long getCustomId() {
    return customId;
  }

  public void setCustomId(Long customId) {
    this.customId = customId;
  }

  @Override
  public String toString() {
    return "ShortURLRecord [" + "alias='" + alias + '\'' + ", customId=" + customId + ", oldUrl='"
        + oldUrl + '\'' + ']';
  }
}
