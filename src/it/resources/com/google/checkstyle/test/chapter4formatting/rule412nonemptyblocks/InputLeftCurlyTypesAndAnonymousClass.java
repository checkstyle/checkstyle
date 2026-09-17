package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** Some javadoc. */
public class InputLeftCurlyTypesAndAnonymousClass {

  /** Some javadoc. */
  public void method() {
    final Runnable r1 =
        new Runnable() {
          @Override
          public void run() {}
        };

    final Thread t = new Thread()
      {
        // violation above ''{' at column 7 should be on the previous line'
        @Override
        public void run() {}
      };
  }

  /** Some javadoc. */
  public void anotherMethod() {
    // violation 2 lines below ''{' at column 24 should have line break after'
    final Runnable runnable =
        new Runnable() { @Override
          public void run() {}
        };

    final Comparable<Integer> comparable =
        new Comparable<Integer>() {
          @Override
          public int compareTo(Integer o) {
            return 0;
          }
        };

    final Object obj = new Object() {};
  }

  // violation below ''{' at column 23 should have line break after'
  static class InnerA { int value = 1;
    int value2 = 2;
  }

  // violation below ''{' at column 19 should have line break after'
  record InnerB() { static
      int num = 1;
  }

  // violation below ''{' at column 20 should have line break after'
  interface InnerC { void doSomething(); }

  static class InnerD extends Thread {
    @Override
    public void run() {}
  }
}
