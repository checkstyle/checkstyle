package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

class InputFormattedNonemptyBlocksLeftRightCurly {
  /**
   * summary.
   *
   * @return helper func *
   */
  boolean condition() {
    return false;
  }

  /** Test do/while loops. */
  void testDoWhile() {

    do {
      testDoWhile();
    } while (condition());

    do {
      testDoWhile();
    } while (condition());
  }

  /** Test while loops. */
  void testWhile() {

    while (condition()) {
      testWhile();
    }

    while (condition()) {
      /* foo */
    }
    while (condition()) {
      testWhile();
    }
    while (condition()) {
      if (condition()) {
        testWhile();
      }
    }
  }

  /** Test for loops. */
  void testFor() {

    for (int i = 1; i < 5; i++) {
      testFor();
    }

    for (int i = 1; i < 5; i++) {
      /* foo */
    }
    for (int i = 1; i < 5; i++) {
      testFor();
    }
    for (int i = 1; i < 5; i++) {
      if (i > 2) {
        testFor();
      }
    }
  }

  /** Test if constructs. */
  public void testIf() {

    if (condition()) {
      testIf();
    } else if (condition()) {
      testIf();
    } else {
      testIf();
    }

    if (condition()) {
      /* foo */
    }
    if (condition()) {
      testIf();
    }
    if (condition()) {
      testIf();
    } else {
      testIf();
    }
    if (condition()) {
      testIf();
    } else {
      testIf();
    }
    if (condition()) {
      testIf();
    } else {
      testIf();
    }
    if (condition()) {
      if (condition()) {
        testIf();
      }
    }
  }

  void whitespaceAfterSemi() {

    int i = 1;
    int j = 2;

    for (; ; ) {}
  }

  /** Empty constructor block. */
  public InputFormattedNonemptyBlocksLeftRightCurly() {}

  /** Empty method block. */
  public void emptyImplementation() {}
}

// violation below 'Top-level class EnumContainerLeft2 has to reside in its own source file.'
class EnumContainerLeft2 {
  private enum Suit {
    CLUBS,
    HEARTS,
    SPADES,
    DIAMONDS
  } // ok
}

// violation below 'Top-level class WithArraysLeft2 has to reside in its own source file.'
class WithArraysLeft2 { // ok
  String[] s1 = {""}; // ok
  String[] empty = {}; // ok
  String[] s2 = { // ok
    "foo", "foo",
  };
  String[] s3 = { // ok
    "foo", "foo",
  };
  String[] s4 = { // ok
    "foo", "foo",
  };
  String[] s5 = {"foo", "foo"}; // ok
}

// violation below 'Top-level class InputRightCurlyOther22 has to reside in its own source file.'
class InputRightCurlyOther22 {
  /**
   * summary.
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
        } else if (x < 0) { // ok

          ;
        } else {
          break;
        } // ok
        switch (a) {
          case 0:
            break;
          default:
            break;
        } // ok
      } catch (Exception e) {
        break;
      } // ok
    } // ok

    synchronized (this) {
      do {
        x = 2;
      } while (x == 2); // ok
    } // ok

    this.wait(666); // Bizarre, but legal

    for (int k = 0; k < 1; k++) {
      String innerBlockVariable = "";
    } // ok

    if (System.currentTimeMillis() > 1000) {
      return 1;
    } else {
      return 2;
    }
  } // ok

  static {
    int x = 1;
  } // ok

  /** some javadoc. */
  public enum GreetingsEnum {
    HELLO,
    GOODBYE
  } // ok

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
  } // ok
} // ok

/**
 * Test input for closing brace if that brace terminates a statement or the body of a constructor.
 */
// violation below 'Top-level class FooCtor2 has to reside in its own source file.'
class FooCtor2 {
  int i3;

  public FooCtor2() {
    i3 = 1;
  }
}

/** Test input for closing brace if that brace terminates a statement or the body of a method. */
// violation below 'Top-level class FooMethod2 has to reside in its own source file.'
class FooMethod2 {
  public void fooMethod() {
    int i = 1;
  }
}

/**
 * Test input for closing brace if that brace terminates a statement or the body of a named class.
 */
// violation below 'Top-level class FooInner2 has to reside in its own source file.'
class FooInner2 {
  class InnerFoo {
    public void fooInnerMethod() {}
  }
} // ok

// violation below 'Top-level class EnumContainer2 has to reside in its own source file.'
class EnumContainer2 {
  private enum Suit {
    CLUBS,
    HEARTS,
    SPADES,
    DIAMONDS
  } // ok
}

// violation below 'Top-level class WithArrays2 has to reside in its own source file.'
class WithArrays2 {
  String[] test = {""}; // ok
  String[] empty = {}; // ok
  String[] s1 = {
    "foo", "foo",
  }; // ok
  String[] s2 = {
    "foo", "foo",
  }; // ok
  String[] s3 = {
    "foo", "foo",
  }; // ok
  String[] s4 = {"foo", "foo"}; // ok
}
