package com.google.checkstyle.test.chapter6programpractice.rule61overridealwaysused;

import java.util.Locale;

/** Some javadoc. */
public record InputOverrideAlwaysUsedForRecord(String name, int age) {

  /** Some javadoc. */
  public String name() { // false-negative, ok until #17561
    return name.toUpperCase(Locale.ROOT);
  }
}

// violation below 'Top-level class Container has to reside in its own source file'
record Container(String fileName, int port) {

  public int port() { // false-negative, ok until #17561
    return port;
  }
}
