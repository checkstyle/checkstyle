package com.google.checkstyle.test.chapter6programpractice.rule61overridealwaysused;

import java.util.Locale;

/** Some javadoc. */
public record InputOverrideAlwaysUsedForRecordMixed(String name, int age) {

  /** Some javadoc. */
  // violation below, 'method must include @java.lang.Override annotation.'
  public String name() {
    return name.toUpperCase(Locale.ROOT);
  }

  /** Some javadoc. */
  public String name(String prefix) {
    return prefix + name;
  }

  @Override
  public int age() {
    return age;
  }

  /** Some javadoc. */
  public void customMethod() {
    System.out.println(name);
  }
}
