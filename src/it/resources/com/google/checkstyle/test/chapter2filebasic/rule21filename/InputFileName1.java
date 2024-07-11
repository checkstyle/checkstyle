package com.google.checkstyle.test.chapter2filebasic.rule21filename;

@interface MyAnnotation1 { // ok
  String name();

  int version();
}

/** Some javadoc. */
@MyAnnotation1(name = "ABC", version = 1)
public class InputFileName1 {} // ok

enum Enum1 {
  A,
  B,
  C;

  Enum1() {}

  public String toString() {
    return ""; // some custom implementation
  }
}

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
