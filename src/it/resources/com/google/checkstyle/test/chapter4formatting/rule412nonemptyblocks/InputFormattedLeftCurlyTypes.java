package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** Some javadoc. */
public class InputFormattedLeftCurlyTypes {

  /** Some javadoc. */
  private int foo(int... n) {
    return 0;
  }

  static class Inner {
    int val;
  }

  interface Inner2 {
    void doSomething();
  }

  record Inner3() {
    static int b = 1;
  }

  @interface InnerAnnotation {
    int d = 1;
  }
}
