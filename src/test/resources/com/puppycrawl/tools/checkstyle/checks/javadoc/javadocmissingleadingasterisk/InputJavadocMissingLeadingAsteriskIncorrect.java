package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmissingleadingasterisk;

/**
 * Config: default
 */
public class InputJavadocMissingLeadingAsteriskIncorrect {

  /**
   Java style. // violation
   **/
  public int missingAsterisk;

  /** // violation on next line

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

}

/**
* EOF immediately after NEWLINE. // violation on next line

@deprecated block tag // violation
Not blank text*/ // violation
class NoTextAfterNewLineIncorrect {

}
