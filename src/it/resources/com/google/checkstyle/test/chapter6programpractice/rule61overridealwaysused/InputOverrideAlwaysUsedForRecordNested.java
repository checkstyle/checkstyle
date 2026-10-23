package com.google.checkstyle.test.chapter6programpractice.rule61overridealwaysused;

import java.util.Locale;

/** Some javadoc. */
public record InputOverrideAlwaysUsedForRecordNested(String outer) {

  @Override
  public String outer() {
    return outer.toUpperCase(Locale.ROOT);
  }

  /** Some javadoc. */
  public record Inner(String inner) {

    // violation 2 lines below 'method must include @java.lang.Override annotation.'
    /** Some javadoc. */
    public String inner() {
      return inner.toLowerCase(Locale.ROOT);
    }
  }
}
