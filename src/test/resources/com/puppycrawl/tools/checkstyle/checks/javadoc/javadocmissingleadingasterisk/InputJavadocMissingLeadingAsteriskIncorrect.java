/*
JavadocMissingLeadingAsterisk
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmissingleadingasterisk;

public class InputJavadocMissingLeadingAsteriskIncorrect {

  /**
   Java style. // violation 'Javadoc line should start with leading asterisk'
   **/
  public int missingAsterisk;

  /**
 // violation 'Javadoc line should start with leading asterisk'
   *
   */
  public int blankComment;

  /**
   *
     @deprecated block tag // violation 'Javadoc line should start with leading asterisk'
   */
  public int blockTag;

  /**
   *
     Last line */ // violation 'Javadoc line should start with leading asterisk'
  public int lastLine;

  /**
   Line one. // violation 'Javadoc line should start with leading asterisk'
   Line two. // violation 'Javadoc line should start with leading asterisk'
   */
  public int twoViolations;

  /** Wrapped
      single-line comment */ // violation 'Javadoc line should start with leading asterisk'
  public int wrapped;

  /**
   * <pre>
      int foo = 0; // violation 'Javadoc line should start with leading asterisk'
   * </pre>
   **/
  public int pre;

  /**
   * {@code
      int foo = 0; // violation 'Javadoc line should start with leading asterisk'
   * }
   **/
  public int code;

}

/**
* EOF immediately after NEWLINE.
 // violation 'Javadoc line should start with leading asterisk'
@deprecated block tag // violation 'Javadoc line should start with leading asterisk'
Not blank text*/ // violation 'Javadoc line should start with leading asterisk'
class NoTextAfterNewLineIncorrect {

}
