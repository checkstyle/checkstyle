package com.google.checkstyle.test.chapter2filebasic.rule21filename;

/** Test for illegal tokens. */
public class InputFileName2 { // ok
  /** Some javadoc. */
  public void defaultMethod() {
    int i = 0;
    switch (i) {
      default:
        i--;
        i++;
        break;
    }
  }

  /** Some javadoc. */
  public native void nativeMethod();

  /** Some javadoc. */
  public void methodWithLiterals() {
    final String ref = "<a href=\"";
    final String refCase = "<A hReF=\"";
  }
}
