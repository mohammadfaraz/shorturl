package com.shorturl.shared.util.generator;

import java.util.stream.IntStream;

public class StreamGenerator {

  private StreamGenerator() {
  }

  public static String getBase62Stream() {
    StringBuilder charStream = new StringBuilder();
    IntStream.range(48, 123).forEach(number -> {
      if (Character.isAlphabetic(number) || Character.isDigit(number)) {
        charStream.append((char)number);
      }
    });
    return charStream.toString();
  }

}
