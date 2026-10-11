package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/** Some javadoc. */
public class InputFormattedWhitespaceAfterGood {

  int xyz; // multiple space between content and double slash.
  int abc; //       multiple space between double slash and comment's text.
  int pqr; //     testing both.

  /** Some javadoc. */
  public void check1(int x, int y) {
    // violation below ''for' construct must use '{}'s.'
    for (int a = 1, b = 2; a < 5; a++, b--)
      ;
    while (x == 0) {
      int a = 0;
      int b = 1;
    }
    do {
      System.out.println("Testing");
    } while (x == 0 || y == 2);
  }

  /** Some javadoc. */
  public void check2(final int a, final int b) {
    if ((float) a == 0.0) {
      System.out.println("true");
    } else {
      System.out.println("false");
    }
  }

  /** Some javadoc. */
  public void check3(int... a) {
    Runnable r2 = () -> String.valueOf("Hello world two!");
    switch (a[0]) {
      default:
        break;
    }
  }

  /** Some javadoc. */
  public void check4() throws java.io.IOException {
    try (java.io.InputStream ignored = System.in) {
      /* foo */
    }
    try {
      /* foo. */
    } catch (Exception e) {
      /* foo. */
    }
  }

  /** Some javadoc. */
  public void check5() {

    try {
      /* foo. */
    } catch (Exception e) {
      /* foo. */
    }
  }

  /** Some javadoc. */
  public void check6() {
    try {
      /* foo. */
    } catch (Exception e) {
      /* foo. */
    }
  }

  /** Some javadoc. */
  public void check7() {
    synchronized (this) {
    }
  }

  /** Some javadoc. */
  public String check8() {
    return ("a" + "b");
  }
}
