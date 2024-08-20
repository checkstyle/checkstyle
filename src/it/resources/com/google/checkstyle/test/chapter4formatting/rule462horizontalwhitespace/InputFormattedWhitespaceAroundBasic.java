package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/*
 * Bug 806242 (NoWhitespaceBeforeCheck violation with an interface).
 *
 * @author o_sukhodolsky
 * @version 1.0
 */

/** Class for testing whitespace issues. violation missing author tag. */
class InputFormattedWhitespaceAroundBasic {
  private final int var1 = 1;
  private final int var2 = 1;

  /** Should be ok. */
  private final int var3 = 1;

  /** skip blank lines between comment and code, should be ok. */
  private final int var4 = 1;

  int xyz; // multiple space between content and double slash.
  int abc; //       multiple space between double slash and comment's text.
  int pqr; //     testing both.

  /** bug 806243 (NoWhitespaceBeforeCheck violation for anonymous inner class). */
  private int test;

  private int i4;
  private int i5;
  private int i6;

  /** method. */
  void method1() {
    final int a = 1;
    int b = 1;
    b = 1;
    b += 1;
    b -= -1 + (+b);
    b = b++ + b--; // ok
    b = ++b - --b; // ok
  }

  /** method. */
  void method2() {
    synchronized (this) {
    }
    try {
      /* foo */
    } catch (RuntimeException e) {
      /* foo */
    }
  }

  /** test WS after void return. */
  private void fastExit() {
    boolean complicatedStuffNeeded = true;
    if (!complicatedStuffNeeded) {
      // should not complain about missing WS after return
    } else {
      // do complicated stuff
    }
  }

  /**
   * test WS after non void return.
   *
   * @return 2
   */
  private int nonVoid() {
    if (true) {
      return (2);
    } else {
      return 2; // this is ok
    }
  }

  /** test casts. */
  private void testCasts() {
    Object o = (Object) new Object(); // ok
    o = (Object) o; // ok
    o = (Object) o; // ok
    o = (Object) o; // ok
  }

  /** test questions. */
  private void testQuestions() {

    boolean b = (1 == 2) ? false : true;
  }

  /** star test. */
  private void starTest() {
    int x = 2 * 3 * 4;
  }

  /** boolean test. */
  private void boolTest() {
    boolean a = true;
    boolean x = !a;
    int z = ~1 + ~2;
  }

  /** division test. */
  private void divTest() {
    int a = 4 % 2;
    int b = 4 % 2;
    int c = 4 % 2;
    int d = 4 % 2;
    int e = 4 / 2;
    int f = 4 / 2;
    int g = 4 / 2;
  }

  /**
   * summary.
   *
   * @return dot test *
   */
  private String dotTest() {
    Object o = new Object();
    o.toString();
    o.toString();
    o.toString();
    return o.toString();
  }

  /** assert statement test. */
  public void assertTest() {
    // OK
    assert true;

    // OK
    assert true : "Whups";

    // evil colons, should be OK
    assert "OK".equals(null) ? false : true : "Whups";

    // missing WS around assert
    assert (true);

    // missing WS around colon
    assert true : "Whups";
  }

  /** another check. */
  void donBradman(Runnable run) {
    donBradman(
        new Runnable() {
          public void run() {}
        });

    final Runnable r =
        new Runnable() {
          public void run() {}
        };
  }

  /** rfe 521323, detect whitespace before ';'. */
  void rfe521323() {
    doStuff();
    for (int i = 0; i < 5; i++) {}
  }

  /** bug 806243 (NoWhitespaceBeforeCheck violation for anonymous inner class). */
  void bug806243() {
    Object o =
        new InputFormattedWhitespaceAroundBasic() {
          private int test;
        };
  }

  void doStuff() {}

  interface Foo {
    void foo();
  }

  /**
   * Avoid Whitespace violations in for loop.
   *
   * @author lkuehne
   * @version 1.0
   */
  class SpecialCasesInForLoop {
    void forIterator() {
      // avoid conflict between WhiteSpaceAfter ';' and ParenPad(nospace)
      for (int i = 0; i++ < 5; ) {
        //                  ^ no whitespace
      }

      // bug 895072
      // avoid conflict between ParenPad(space) and NoWhiteSpace before ';'
      int i = 0;
      for (; i < 5; i++) {
        //   ^ whitespace
      }
      for (int anInt : getSomeInts()) {
        // Should be ignored
      }
    }

    int[] getSomeInts() {
      int i = 2 / 3;
      return null;
    }

    void forColon() {
      int[] ll = new int[10];
      for (int x : ll) {}
      for (int x : ll) {}
      for (int x : ll) {}
      for (int x : ll) {} // ok
    }
  }

  /** Operators mentioned in Google Coding Standards 2016-07-12. */
  class NewGoogleOperators {
    NewGoogleOperators() {
      Runnable l;

      l = () -> {};
      l = () -> {};
      l = () -> {}; // ok
      l = () -> {}; // ok

      java.util.Arrays.sort(null, String::compareToIgnoreCase);
      java.util.Arrays.sort(null, String::compareToIgnoreCase);

      new Object().toString();
      new Object().toString();
    }
  }
}
