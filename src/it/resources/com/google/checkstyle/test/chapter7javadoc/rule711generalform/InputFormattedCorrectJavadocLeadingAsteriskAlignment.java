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

  /** No leading asterisk present. */
  public InputFormattedCorrectJavadocLeadingAsteriskAlignment() {}

  // violation 3 lines below "Summary javadoc is missing."
  // violation 3 lines below "Javadoc tag '@param' should be preceded with an empty line."
  // False negative for below javadoc, until #18271
  /***
   * @param a testing....
   */
  public InputFormattedCorrectJavadocLeadingAsteriskAlignment(int a) {}

  /*************************************************
   *** @param str testing.....
   **********************************/
  // False negative for above javadoc, until #18271, #18273
  public InputFormattedCorrectJavadocLeadingAsteriskAlignment(String str) {}

  /** * */
  // violation above "Summary javadoc is missing."
  private String str2;

  /****/
  // violation above "Summary javadoc is missing."
  private String str3;

  /** This method does nothing. */
  private void foo() {}

  /** Opening tag should be alone on line. This method does nothing. */
  private void foo2() {}

  /** Javadoc for foo3. Closing tag should be alone on line. */
  private void foo3() {
    // foo2 code goes here
  }

  /** Javadoc for enum. */
  private enum CorrectJavadocEnum {
    // violation below "Summary javadoc is missing."
    /** */
    ONE,

    /** Not allowed. No leading asterisk present. */
    TWO,

    /** Not allowed. Opening javadoc tag should be alone on line. */
    THREE,

    /** Not allowed. Closing javadoc tag should be alone on line. */
    FOUR,

    // violation below "Summary javadoc is missing."
    /** */
    FIVE
  }
}
