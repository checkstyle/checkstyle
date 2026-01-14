package com.google.checkstyle.test.chapter6programpractice.rule61overridealwaysused;

/** Some javadoc. */
public class InputOverrideAlwaysUsedForRecordInClass {

  private String name;

  /** Some javadoc. */
  public String name() {
    return name;
  }

  /** Some javadoc. */
  public record InnerRecord(String value) {

    /** Some javadoc. */
    // violation below, 'method must include @java.lang.Override annotation.'
    public String value() {
      return value.trim();
    }
  }
}
