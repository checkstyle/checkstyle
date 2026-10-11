/*
JavadocMissingLeadingAsterisk
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmissingleadingasterisk;

public class InputJavadocMissingLeadingAsteriskIncorrect {

  // violation 2 lines below 'Javadoc line should start with leading asterisk'
  /**
   Java style.
   **/
  public int missingAsterisk;

  // violation 2 lines below 'Javadoc line should start with leading asterisk'
  /**

   *
   */
  public int blankComment;

  // violation 3 lines below 'Javadoc line should start with leading asterisk'
  /**
   *
     @deprecated block tag
   */
  public int blockTag;

  // violation 3 lines below 'Javadoc line should start with leading asterisk'
  /**
   *
     Last line */
  public int lastLine;

  // violation 3 lines below 'Javadoc line should start with leading asterisk'
  // violation 3 lines below 'Javadoc line should start with leading asterisk'
  /**
   Line one.
   Line two.
   */
  public int twoViolations;

  // violation 2 lines below 'Javadoc line should start with leading asterisk'
  /** Wrapped
      single-line comment */
  public int wrapped;

  // violation 3 lines below 'Javadoc line should start with leading asterisk'
  /**
   * <pre>
      int foo = 0;
   * </pre>
   **/
  public int pre;

  // violation 3 lines below 'Javadoc line should start with leading asterisk'
  /**
   * {@code
      int foo = 0;
   * }
   **/
  public int code;

}

// violation 5 lines below 'Javadoc line should start with leading asterisk'
// violation 5 lines below 'Javadoc line should start with leading asterisk'
// violation 5 lines below 'Javadoc line should start with leading asterisk'
/**
* EOF immediately after NEWLINE.

@deprecated block tag
Not blank text*/
class NoTextAfterNewLineIncorrect { }
