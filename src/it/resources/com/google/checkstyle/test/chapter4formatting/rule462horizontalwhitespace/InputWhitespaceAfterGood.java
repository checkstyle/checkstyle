package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/** some javadoc. */
public class InputWhitespaceAfterGood {

  int xyz;       // multiple space between content and double slash.
  int abc; //       multiple space between double slash and comment's text.
  int pqr;       //     testing both.

  /** some javadoc. */
  public void check1(int x, int y) {
    // violation below ''for' construct must use '{}'s.'
    for (int a = 1, b = 2; a < 5; a++, b--)
      ;
    while (x == 0) {
      int a = 0, b = 1; // violation 'Each variable declaration must be in its own statement.'
    }
    do {
      System.out.println("Testing");
    } while (x == 0 || y == 2);
  }

  /** some javadoc. */
  public void check2(final int a, final int b) {
    if ((float) a == 0.0) {
      System.out.println("true");
    } else {
      System.out.println("false");
    }
  }

  /** some javadoc. */
  public void check3(int... a) {
    Runnable r2 = () -> String.valueOf("Hello world two!");
    switch (a[0]) {
      default:
        break;
    }
  }

  /** some javadoc. */
  public void check4() throws java.io.IOException {
    try (java.io.InputStream ignored = System.in) {}
    // 3 violations above:
    // 'Empty try block.'
    //  ''{' is not followed by whitespace.'
    //  ''}' is not preceded with whitespace.'
    try {
      /* foo. */
    } catch (Exception e) {
      /* foo. */
    }
  }

  /** some javadoc. */
  public void check5() {

    try {
      /* foo. */
    } catch (Exception e) {
      /* foo. */
    }
  }

  /** some javadoc. */
  public void check6() {
    try {
      /* foo. */
    } catch (Exception e) {
      /* foo. */
    }
  }

  public void check7() {
    synchronized (this) {
    }
  }

  public String check8() {
    return ("a" + "b");
  }
}
