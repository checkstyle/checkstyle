package com.google.checkstyle.test.chapter6programpractice.rule61overridealwaysused;

import java.util.Locale;

/** Some javadoc. */
public record InputOverrideAlwaysUsedForRecord(String name, int age) {

  /** Some javadoc. */
  // violation below, 'method must include @java.lang.Override annotation.'
  public String name() {
    return name.toUpperCase(Locale.ROOT);
  }
}

// violation below 'Top-level class Container has to reside in its own source file'
record Container(String fileName, int port) {

  // violation below, 'method must include @java.lang.Override annotation.'
  public int port() {
    return port;
  }
}
