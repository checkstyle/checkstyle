package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/*
 * Bug 806242 (NoWhitespaceBeforeCheck violation with an interface).
 *
 * @author o_sukhodolsky
 * @version 1.0
 */

/** Class for testing whitespace issues. violation missing author tag. */
class InputWhitespaceAroundBasic {
  private final int var1= 1; // violation ''=' is not preceded with whitespace.'
  private final int var2 =1; // violation ''=' is not followed by whitespace.'

  /** Should be ok. */
  private final int var3 = 1;

  /** skip blank lines between comment and code, should be ok. */
  private final int var4 = 1;

  int xyz;       // multiple space between content and double slash.
  int abc; //       multiple space between double slash and comment's text.
  int pqr;       //     testing both.

  /** bug 806243 (NoWhitespaceBeforeCheck violation for anonymous inner class). */
  private int test;

  private int i4, i5, i6;
  // violation above 'Each variable declaration must be in its own statement.'

  /** method. */
  void method1() {
    final int a = 1;
    int b= 1; // violation ''=' is not preceded with whitespace.'
    b= 1; // violation ''=' is not preceded with whitespace.'
    b +=1; // violation ''\+=' is not followed by whitespace.'
    b -=- 1 + (+ b); // violation ''-=' is not followed by whitespace.'
    b = b++ + b--; // ok
    b = ++ b - -- b; // ok
  }

  /** method. */
  void method2() {
    synchronized(this) {
      // 2 violations above:
      //  ''synchronized' is not followed by whitespace.'
      //  ''synchronized' is not followed by whitespace.'
    }
    try{
      // 3 violations above:
      //  ''try' is not followed by whitespace.'
      //  ''try' is not followed by whitespace.'
      //  ''{' is not preceded with whitespace.'
    } catch (RuntimeException e) {// violation ''{' is not followed by whitespace.'
    }
  }

  /** test WS after void return. */
  private void fastExit() {
    boolean complicatedStuffNeeded = true;
    // 2 violations 3 lines below:
    //  ''if' is not followed by whitespace.'
    //  ''if' is not followed by whitespace.'
    if(!complicatedStuffNeeded) {
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
      return(2);
      // 2 violations above:
      //  ''return' is not followed by whitespace.'
      //  ''return' is not followed by whitespace.'
    } else {
      return 2; // this is ok
    }
  }

  /** test casts. */
  private void testCasts() {
    Object o = (Object) new Object(); // ok
    o = (Object) o; // ok
    o = ( Object ) o; // ok
    o = (Object)
            o; // ok
  }

  /** test questions. */
  private void testQuestions() {

    boolean b = (1 ==2) ? false : true; // violation ''==' is not followed by whitespace.'
  }

  /** star test. */
  private void starTest() {
    int x = 2 * 3* 4; // violation ''*' is not preceded with whitespace.'
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
    int b = 4% 2; // violation ''%' is not preceded with whitespace.'
    int c = 4 %2; // violation ''%' is not followed by whitespace.'
    int d = 4% 2; // violation ''%' is not preceded with whitespace.'
    int e = 4 / 2;
    int f = 4/ 2; // violation ''/' is not preceded with whitespace.'
    int g = 4 /2; // violation ''/' is not followed by whitespace.'
  }

  /**
   * summary.
   *
   * @return dot test *
   */
  private java.lang.String dotTest() {
    Object o = new java.lang.Object();
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
    assert(true); // violation ''assert' is not followed by whitespace.'

    // missing WS around colon
    assert true: "Whups"; // violation '':' is not preceded with whitespace.'
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
        new InputWhitespaceAroundBasic() {
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
      for (int x:ll) {}
      // 2 violations above:
      //  '':' is not followed by whitespace.'
      //  '':' is not preceded with whitespace.'
      for (int x :ll) {} // violation '':' is not followed by whitespace.'
      for (int x: ll) {} // violation '':' is not preceded with whitespace.'
      for (int x : ll) {} // ok
    }
  }

  /** Operators mentioned in Google Coding Standards 2016-07-12. */
  class NewGoogleOperators {
    NewGoogleOperators() {
      Runnable l;

      l = ()-> {}; // violation ''->' is not preceded with whitespace.'
      l = () ->{};
      // 2 violations above:
      //  ''->' is not followed by whitespace.'
      //  ''->' is not followed by whitespace.'
      l = () -> {}; // ok
      l = () -> {}; // ok

      java.util.Arrays.sort(null, String::compareToIgnoreCase);
      java.util.Arrays.sort(null, String::compareToIgnoreCase);

      new Object().toString();
      new Object().toString();
    }
  }
}
