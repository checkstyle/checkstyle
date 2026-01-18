package com.google.checkstyle.test.chapter6programpractice.rule61overridealwaysused;

import java.util.Locale;

/** Some javadoc. */
public record InputOverrideAlwaysUsedForRecordValid(String name, int age) {

  @Override
  public String name() {
    return name.toUpperCase(Locale.ROOT);
  }

  @Override
  public int age() {
    return age;
  }
}
