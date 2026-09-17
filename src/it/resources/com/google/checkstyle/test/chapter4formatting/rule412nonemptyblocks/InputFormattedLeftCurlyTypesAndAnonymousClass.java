package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** Some javadoc. */
public class InputFormattedLeftCurlyTypesAndAnonymousClass {

  /** Some javadoc. */
  public void method() {
    final Runnable r1 =
        new Runnable() {
          @Override
          public void run() {}
        };

    final Thread t =
        new Thread() {
          @Override
          public void run() {}
        };
  }

  /** Some javadoc. */
  public void anotherMethod() {
    final Runnable runnable =
        new Runnable() {
          @Override
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

  static class InnerA {
    int value = 1;
    int value2 = 2;
  }

  record InnerB() {
    static int num = 1;
  }

  interface InnerC {
    void doSomething();
  }

  static class InnerD extends Thread {
    @Override
    public void run() {}
  }
}
