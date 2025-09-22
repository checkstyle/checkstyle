package com.google.checkstyle.test.chapter7javadoc.rule711generalform;

/**
 * This file contains violations, still it is named "Correct" because it doesn't contain any
 * violations regarding Javadoc's Leading Asterisk Alignments. Other violations are kept to ensure
 * that we cover all the edge cases.
 */
public class InputFormattedCorrectJavadocLeadingAsteriskAlignment {

  /** javadoc for instance variable. */
  private int int1;

  /** */
  // violation above "Summary javadoc is missing."
  private int int2;

  /***/
  // violation above "Summary javadoc is missing."
  private String str;

  /** */
  // violation above "Summary javadoc is missing."
  private String str1;

  /**
   * Not sure if missing leading asterisk is allowed or not. It is addressed at: #17778, Subproblem
   * 2
   */
  public InputFormattedCorrectJavadocLeadingAsteriskAlignment() {}

  // violation 2 lines below "Summary javadoc is missing."
  // violation 2 lines below "Javadoc tag '@param' should be preceded with an empty line."
  /***
   * @param a testing....
   */
  public InputFormattedCorrectJavadocLeadingAsteriskAlignment(int a) {}

  /*************************************************
   *** @param str testing.....
   **********************************/
  // Not sure if "*/" is allowed to be preceded by other content or not,
  // it is addressed at: #17778, Subproblem 3
  public InputFormattedCorrectJavadocLeadingAsteriskAlignment(String str) {}

  /** * */
  // violation above "Summary javadoc is missing."
  private String str2;

  /****/
  // violation above "Summary javadoc is missing."
  private String str3;

  /** This method does nothing. */
  private void foo() {}

  /**
   * Title // Not sure if this is allowed or not, it is addressed at: #17778, Subproblem 1. This
   * method does nothing.
   */
  private void foo2() {}

  /** Javadoc for foo3. This is allowed */
  // Subproblem 3
  private void foo3() {
    // foo2 code goes here
  }

  /** Javadoc for enum. */
  // Subproblem 3
  private enum CorrectJavadocEnum {
    // violation 2 lines below "Summary javadoc is missing."
    // Subproblem 2
    /** */
    ONE,

    // Subproblem 2
    /** Some javadoc. */
    TWO,

    // Subproblem 1 & 2
    /** Some javadoc. */
    THREE,

    // Subproblem 3
    /** Allowed. */
    FOUR,

    // violation below "Summary javadoc is missing."
    /** */
    FIVE
  }
}
