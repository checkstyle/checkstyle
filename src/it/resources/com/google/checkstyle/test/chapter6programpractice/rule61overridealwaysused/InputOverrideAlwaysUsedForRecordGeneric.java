package com.google.checkstyle.test.chapter6programpractice.rule61overridealwaysused;

/** Some javadoc. */
public record InputOverrideAlwaysUsedForRecordGeneric<T>(T value) {

  /** Some javadoc. */
  // violation below, 'method must include @java.lang.Override annotation.'
  public T value() {
    return value;
  }
}

// violation below, 'Top-level class Printable has to reside in its own source file.'
interface Printable {
  void print();
}

// violation below, 'Top-level class Document has to reside in its own source file.'
record Document(String title) implements Printable {

  /** Some javadoc. */
  // violation below, 'method must include @java.lang.Override annotation.'
  public String title() {
    return title.toUpperCase();
  }

  @Override
  public void print() {
    System.out.println(title);
  }
}
