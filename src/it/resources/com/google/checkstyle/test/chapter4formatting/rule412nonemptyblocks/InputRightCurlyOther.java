package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

class InputRightCurlyOther {
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
        } // violation ''}' at column 9 should be on the same line as the next part of .*'
        else {
          break;
        }
        switch (a) {
          case 0:
            break;
          default:
            break;
        }
      } // violation ''}' at column 7 should be on the same line as the next part of .*'
      catch (Exception e) {
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
      // 2 violations 3 lines below:
      //  ''}' at column 21 should have line break before.'
      //  ''method def' child has incorrect indentation level 6, expected level should be 4.'
      flag = !flag; } System
      .identityHashCode("Xe-xe");

    if (flag) {
      System.identityHashCode("some foo");
    }
  }
}

/**
 * Test input for closing brace if that brace terminates a statement or the body of a constructor.
 */
class FooCtorAlone {
  // violation above 'Top-level class FooCtorAlone has to reside in its own source file.'
  int test;

  public FooCtorAlone() {
    test = 1;
  } } // violation ''}' at column 3 should be alone on a line.'

/** Test input for closing brace if that brace terminates a statement or the body of a method. */
class FooMethodAlone {
  // violation above 'Top-level class FooMethodAlone has to reside in its own source file.'
  public void fooMethod() {
    int i = 1;
  } } // violation ''}' at column 3 should be alone on a line.'

/**
 * Test input for closing brace if that brace terminates a statement or the body of a named class.
 */
class FooInnerAlone {
  // violation above 'Top-level class FooInnerAlone has to reside in its own source file.'
  class InnerFoo {
    public void fooInnerMethod() {}
  }
}

// violation below 'Top-level class EnumContainerAlone has to reside in its own source file.'
class EnumContainerAlone {
  private enum Suit {
    CLUBS,
    HEARTS,
    SPADES,
    DIAMONDS
  }
}

// violation below 'Top-level class WithArraysAlone has to reside in its own source file.'
class WithArraysAlone {
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

// violation below 'Top-level class Interface has to reside in its own source file.'
class Interface {
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

// violation below 'Top-level class TestEnum has to reside in its own source file.'
enum TestEnum {}

// violation below 'Top-level class TestEnum1 has to reside in its own source file.'
enum TestEnum1 {
  SOME_VALUE;
}

// violation below 'Top-level class TestEnum2 has to reside in its own source file.'
enum TestEnum2 {
  SOME_VALUE;
}

// violation below 'Top-level class TestEnum3 has to reside in its own source file.'
enum TestEnum3 {
  SOME_VALUE;
}

// violation below 'Top-level class TestEnum4 has to reside in its own source file.'
enum TestEnum4 {
  SOME_VALUE;
}
