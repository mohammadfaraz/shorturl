package com.shorturl.service.core;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class NumbToString {

  private char[] arra = new char[62];

  private void show() {
    AtomicInteger i = new AtomicInteger(0);
    IntStream.range(48, 123).forEach(number -> {
      if (Character.isAlphabetic(number) || Character.isDigit(number)) {
        arra[i.getAndIncrement()] = (char) number;
      }
    });
  }

  private String convert(Long number, int base) {
    int remainder;
    while(number >0) {

    }
    return null;
  }

  /*public static void main(String[] args) {
    NumbToString a  = new NumbToString();
    a.show();
    System.out.println(a.arra);
    System.out.println(a.arra.length);
  }*/

}
