package com.google.checkstyle.test.chapter2filebasic.rule21filename;

// violation below 'Top-level class MyAnnotation1 has to reside in its own source file.'
@interface MyAnnotation1 { // ok
  String name();

  int version();
}

/** Some javadoc. */
@MyAnnotation1(name = "ABC", version = 1)
public class InputFileName1 {} // ok

// violation below 'Top-level class Enum1 has to reside in its own source file.'
enum Enum1 {
  A,
  B,
  C;

  Enum1() {}

  public String toString() {
    return ""; // some custom implementation
  }
}

// violation below 'Top-level class TestRequireThisEnum has to reside in its own source file.'
interface TestRequireThisEnum { // ok
  enum DayOfWeek {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY
  }
}
