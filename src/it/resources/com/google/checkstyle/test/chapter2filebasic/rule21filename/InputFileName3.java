package com.google.checkstyle.test.chapter2filebasic.rule21filename;

// violation below 'The name of the outer type and the file do not match.'
@interface MyAnnotation2 {
  String name();

  int version();
}

@MyAnnotation2(name = "ABC", version = 1)
class InputFileName3 {} // ok

enum Enum2 { // ok
  A,
  B,
  C;

  Enum2() {}

  public String toString() {
    return ""; // some custom implementation
  }
}

interface TestRequireThisEnum2 { // ok
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
