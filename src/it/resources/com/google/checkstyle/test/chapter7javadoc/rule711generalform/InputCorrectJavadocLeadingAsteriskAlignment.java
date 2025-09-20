package com.google.checkstyle.test.chapter7javadoc.rule711generalform;

/**
 * This file contains violations, still it is named "Correct"
 * because it doesn't contain any violations regarding
 * Javadoc's Leading Asterisk Alignments.
 * Other violations are kept to ensure that we cover all the edge cases.
 */
public class InputCorrectJavadocLeadingAsteriskAlignment {

  /** javadoc for instance variable. */
  private int int1;

  /** */ // violation "Summary javadoc is missing."
  private int int2;

  /***/ // violation "Summary javadoc is missing."
  private String str;

  /** */ // violation "Summary javadoc is missing."
  private String str1;

  /**
    Not allowed, leading asterisk should always be present.
    It is addressed at: #17778, Subproblem 2
   */
  public InputCorrectJavadocLeadingAsteriskAlignment() {}

  // violation 2 lines below "Summary javadoc is missing."
  // violation 2 lines below "Javadoc tag '@param' should be preceded with an empty line."
  /***
   * @param a testing....
   */
  public InputCorrectJavadocLeadingAsteriskAlignment(int a) {}

  /*************************************************
   *** @param str testing.....
   **********************************/
  // "/**" and "*/" should be alone on the line,
  // it is addressed at: #17778, Subproblem 1 & 3
  public InputCorrectJavadocLeadingAsteriskAlignment(String str) {}

  /** * */ // violation "Summary javadoc is missing."
  private String str2;

  /****/ // violation "Summary javadoc is missing."
  private String str3;

  /**
   * This method does nothing.
   */
  private void foo() {}

  /** Title // "/**" should be alone on the line, it is addressed at: #17778, Subproblem 1.
   * This method does nothing.
   */
  private void foo2() {}

  /**
   * Javadoc for foo3.
   This is allowed */ // "*/" should be alone on the line, it is addressed at #17778, Subproblem 3
  private void foo3() {
    // foo2 code goes here
  }

  /**
   * Javadoc for enum.
   * */ // Subproblem 3
  private enum CorrectJavadocEnum {
    // violation 2 lines below "Summary javadoc is missing."
    // Subproblem 2
    /**

     */
    ONE,

    // Subproblem 2
    /**
     Some javadoc.
     */
    TWO,

    // Subproblem 1 & 2
    /** Some javadoc.

     */
    THREE,

    // Subproblem 3
    /**
     * Allowed. */
    FOUR,

    // violation below "Summary javadoc is missing."
    /**

     */
    FIVE
  }
}
