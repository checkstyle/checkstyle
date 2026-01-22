package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

class InputFormattedRightCurlyOther {
  /**
   * Summary.
   *
   * @see test method *
   */
  int foo() throws InterruptedException {
    int x = 1;
    int a = 2;
    while (true) {
      try {
        if (x > 0) {
          break;
        } else if (x < 0) {

          ;
        } else {
          break;
        }
        switch (a) {
          case 0:
            break;
          default:
            break;
        }
      } catch (Exception e) {
        break;
      }
    }

    synchronized (this) {
      do {
        x = 2;
      } while (x == 2);
    }

    this.wait(666); // Bizarre, but legal

    for (int k = 0; k < 1; k++) {
      String innerBlockVariable = "";
    }

    if (System.currentTimeMillis() > 1000) {
      return 1;
    } else {
      return 2;
    }
  }

  static {
    int x = 1;
  }

  public enum GreetingsEnum {
    HELLO,
    GOODBYE
  }

  void method2() {
    boolean flag = true;
    if (flag) {
      System.identityHashCode("heh");
      flag = !flag;
    }
    System.identityHashCode("Xe-xe");

    if (flag) {
      System.identityHashCode("some foo");
    }
  }
}

/**
 * Test input for closing brace if that brace terminates a statement or the body of a constructor.
 */
class FooCtorAlone2 {
  // violation above 'Top-level class FooCtorAlone2 has to reside in its own source file.'
  int test;

  public FooCtorAlone2() {
    test = 1;
  }
}

/** Test input for closing brace if that brace terminates a statement or the body of a method. */
class FooMethodAlone2 {
  // violation above 'Top-level class FooMethodAlone2 has to reside in its own source file.'
  public void fooMethod() {
    int i = 1;
  }
}

/**
 * Test input for closing brace if that brace terminates a statement or the body of a named class.
 */
class FooInnerAlone2 {
  // violation above 'Top-level class FooInnerAlone2 has to reside in its own source file.'
  class InnerFoo {
    public void fooInnerMethod() {}
  }
}

// violation below 'Top-level class EnumContainerAlone2 has to reside in its own source file.'
class EnumContainerAlone2 {
  private enum Suit {
    CLUBS,
    HEARTS,
    SPADES,
    DIAMONDS
  }
}

// violation below 'Top-level class WithArraysAlone2 has to reside in its own source file.'
class WithArraysAlone2 {
  String[] ss = {""};
  String[] empty = {};
  String[] s1 = {
    "foo", "foo",
  };
  String[] s2 = {
    "foo", "foo",
  };
  String[] s3 = {
    "foo", "foo",
  };
  String[] s4 = {"foo", "foo"};
}

// violation below 'Top-level class Interface2 has to reside in its own source file.'
class Interface2 {
  public @interface TestAnnotation {}

  public @interface TestAnnotation1 {
    String someValue();
  }

  public @interface TestAnnotation2 {
    String someValue();
  }

  public @interface TestAnnotation3 {
    String someValue();
  }

  public @interface TestAnnotation4 {
    String someValue();
  }
}

// violation below 'Top-level class TestEnum222 has to reside in its own source file.'
enum TestEnum222 {}

// violation below 'Top-level class TestEnum12 has to reside in its own source file.'
enum TestEnum12 {
  SOME_VALUE;
}

// violation below 'Top-level class TestEnum22 has to reside in its own source file.'
enum TestEnum22 {
  SOME_VALUE;
}

// violation below 'Top-level class TestEnum32 has to reside in its own source file.'
enum TestEnum32 {
  SOME_VALUE;
}

// violation below 'Top-level class TestEnum42 has to reside in its own source file.'
enum TestEnum42 {
  SOME_VALUE;
}
