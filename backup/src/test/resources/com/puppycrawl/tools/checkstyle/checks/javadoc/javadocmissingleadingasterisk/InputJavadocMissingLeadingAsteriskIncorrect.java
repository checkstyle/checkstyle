/*
JavadocMissingLeadingAsterisk
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmissingleadingasterisk;

public class InputJavadocMissingLeadingAsteriskIncorrect {

  /**
   Java style. // violation
   **/
  public int missingAsterisk;

  /**
 // violation
   *
   */
  public int blankComment;

  /**
   *
     @deprecated block tag // violation
   */
  public int blockTag;

  /**
   *
     Last line */ // violation
  public int lastLine;

  /**
   Line one. // violation
   Line two. // violation
   */
  public int twoViolations;

  /** Wrapped
      single-line comment */ // violation
  public int wrapped;

  /**
   * <pre>
      int foo = 0; // violation
   * </pre>
   **/
  public int pre;

  /**
   * {@code
      int foo = 0; // violation
   * }
   **/
  public int code;

}

/**
* EOF immediately after NEWLINE.
 // violation
@deprecated block tag // violation
Not blank text*/ // violation
class NoTextAfterNewLineIncorrect {

}
