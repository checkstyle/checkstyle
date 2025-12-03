package com.google.checkstyle.test.chapter7javadoc.rule711generalform;

/**
 * This file contains violations, still it is named "Correct"
 * because it doesn't contain any violations regarding
 * Javadoc's Leading Asterisk Alignments.
 * Other violations are kept to ensure that we cover all the edge cases.
 */
public class InputCorrectJavadocLeadingAsteriskAlignment {

  /** Javadoc for instance variable. */
  private int int1;

  /** */ // violation "Summary javadoc is missing."
  private int int2;

  /***/ // violation "Summary javadoc is missing."
  private String str;

  /** */ // violation "Summary javadoc is missing."
  private String str1;

  /**
    No leading asterisk present. False negative until #17778, Subproblem 2.
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
  // False negative for above javadoc, until #17778, Subproblem 1 & 3.
  public InputCorrectJavadocLeadingAsteriskAlignment(String str) {}

  /** * */ // violation "Summary javadoc is missing."
  private String str2;

  /****/ // violation "Summary javadoc is missing."
  private String str3;

  /**
   * This method does nothing.
   */
  private void foo() {}

  /** Opening tag should be alone on line. // False negative until #17778, Subproblem 1.
   * This method does nothing.
   */
  private void foo2() {}

  /**
   * Javadoc for foo3.
   Closing tag should be alone on line. */ // False negative until #17778, Subproblem 3.
  private void foo3() {
    // foo2 code goes here
  }

  /**
   * Javadoc for enum.
   * */ // False negative until #17778, Subproblem 3.
  private enum CorrectJavadocEnum {
    // violation 2 lines below "Summary javadoc is missing."
    // False negative until #17778, Subproblem 2.
    /**

     */
    ONE,

    // False negative until #17778, Subproblem 2.
    /**
     Not allowed. No leading asterisk present.
     */
    TWO,

    // False negative until #17778, Subproblem 1 & 2.
    /** Not allowed. Opening javadoc tag should be alone on line.

     */
    THREE,

    // False negative until #17778, Subproblem 3.
    /**
     * Not allowed. Closing javadoc tag should be alone on line. */
    FOUR,

    // violation below "Summary javadoc is missing."
    /**

     */
    FIVE
  }
}
