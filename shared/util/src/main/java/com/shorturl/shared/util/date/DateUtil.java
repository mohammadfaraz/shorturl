package com.shorturl.shared.util.date;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

  public static Date convertLocalDateTimeToDate(LocalDateTime dateTime, TimeZone timeZone) {
    return Date.from(dateTime.atZone(timeZone.toZoneId()).toInstant());
  }

  public static LocalDateTime convertDateToLocalDateTime(Date date, TimeZone timeZone) {
    Instant instant = Instant.ofEpochMilli(date.getTime());
    return LocalDateTime.ofInstant(instant, ZoneId.of(timeZone.getID()));
  }

}
