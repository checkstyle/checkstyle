package com.google.checkstyle.test.chapter6programpractice.rule61overridealwaysused;

import java.util.Locale;

/** Some javadoc. */
public record InputOverrideAlwaysUsedForRecordViolation(String name, int age) {

  /** Some javadoc. */
  // violation below, 'method must include @java.lang.Override annotation.'
  public String name() {
    return name.toUpperCase(Locale.ROOT);
  }

  /** Some javadoc. */
  // violation below, 'method must include @java.lang.Override annotation.'
  public int age() {
    return age;
  }
}
