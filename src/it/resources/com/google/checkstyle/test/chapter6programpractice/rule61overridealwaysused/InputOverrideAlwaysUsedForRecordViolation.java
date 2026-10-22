package com.google.checkstyle.test.chapter6programpractice.rule61overridealwaysused;

import java.util.Locale;

/** Some javadoc. */
public record InputOverrideAlwaysUsedForRecordViolation(String name, int age) {

  // violation 2 lines below 'method must include @java.lang.Override annotation.'
  /** Some javadoc. */
  public String name() {
    return name.toUpperCase(Locale.ROOT);
  }

  // violation 2 lines below 'method must include @java.lang.Override annotation.'
  /** Some javadoc. */
  public int age() {
    return age;
  }
}
