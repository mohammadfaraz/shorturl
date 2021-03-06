package com.shorturl.shared.util.generator;


import org.apache.commons.lang3.RandomStringUtils;

public class KeyGenerator {

  private static final int CUSTOM_ID_SIZE = 9;

  private KeyGenerator() {
  }

  public static Long getUniqueCustomKey() {
    return getUniqueRandomKey(CUSTOM_ID_SIZE);
  }

  private static Long getUniqueRandomKey(int size) {
    Long initials = 2L;
    Long randomPart = Long.valueOf(RandomStringUtils.randomNumeric(size));
    return initials * (long) Math.pow(10, size) + randomPart;
  }

}
