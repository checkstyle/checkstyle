package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** Some javadoc. */
public class InputLeftCurlyTypes {

  /** Some javadoc. */
  private int foo(int... n) {
    return 0;
  }

  // violation below ''{' at column 22 should have line break after'
  static class Inner { int val; }

  // violation below ''{' at column 20 should have line break after'
  interface Inner2 { void doSomething(); }

  // violation below ''{' at column 19 should have line break after'
  record Inner3() { static
      int b = 1;
  }

  // violation below ''{' at column 30 should have line break after'
  @interface InnerAnnotation { int d = 1;
  }
}
