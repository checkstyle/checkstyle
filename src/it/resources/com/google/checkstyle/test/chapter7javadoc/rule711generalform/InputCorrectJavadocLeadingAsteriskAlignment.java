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

  // violation below "Summary javadoc is missing."
  /** */
  private int int2;

  // violation below "Summary javadoc is missing."
  /***/
  private String str;

  // violation below "Summary javadoc is missing."
  /** */
  private String str1;

  // violation 2 lines below "Javadoc line should start with leading asterisk."
  /**
   No leading asterisk present.
   */
  public InputCorrectJavadocLeadingAsteriskAlignment() {}

  // violation 2 lines below "Summary javadoc is missing."
  // False negative for below javadoc, until #18271
  /***
   * @param a testing....
   */
  public InputCorrectJavadocLeadingAsteriskAlignment(int a) {}

  // violation 3 lines below "Comment uses box-like repetitive character pattern."
  // violation 2 lines below "Summary javadoc is missing."
  // violation 3 lines below 'Block comment end must be on new line.'
  /*************************************************
   *** @param str testing.....
   **********************************/
  // False negative for above javadoc, until #18271.

  public InputCorrectJavadocLeadingAsteriskAlignment(String str) {}

  // violation below "Summary javadoc is missing."
  /** * */
  private String str2;

  // violation below "Summary javadoc is missing."
  /****/
  private String str3;

  /**
   * This method does nothing.
   */
  private void foo() {}

  // violation below "Javadoc content should start from the next line."
  /** Opening tag should be alone on line.
   * This method does nothing.
   */
  private void foo2() {}

  // violation 4 lines below "Javadoc line should start with leading asterisk."
  // violation 3 lines below 'Block comment end must be on new line.'
  /**
   * Javadoc for foo3.
   Closing tag should be alone on line. */
  private void foo3() {
    // foo2 code goes here
  }

  // violation 3 lines below 'Block comment end must be on new line.'
  /**
   * Javadoc for enum.
   * */
  private enum CorrectJavadocEnum {
    // violation 2 lines below "Summary javadoc is missing."
    // violation 2 lines below "Javadoc line should start with leading asterisk."
    /**

     */
    ONE,

    // violation 2 lines below "Javadoc line should start with leading asterisk."
    /**
     Not allowed. No leading asterisk present.
     */
    TWO,

    // violation 2 lines below "Javadoc content should start from the next line."
    // violation 2 lines below "Javadoc line should start with leading asterisk."
    /** Not allowed. Opening javadoc tag should be alone on line.

     */
    THREE,

    /**
     * Not allowed. Closing javadoc tag should be alone on line. */
    // violation above 'Block comment end must be on new line.'
    FOUR,

    // violation 2 lines below "Summary javadoc is missing."
    // violation 2 lines below "Javadoc line should start with leading asterisk."
    /**

     */
    FIVE
  }
}
