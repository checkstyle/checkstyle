package com.google.checkstyle.test.chapter4formatting.rule4861blockcommentstyle;

/** Some javadoc. */

public class InputMultilineCommentLeadingAsterisk {

  // violation below 'Multiline comment line(s) 6, 7 should start with leading asterisk'
  /*
     Some Comment
     Some Comment2
   */

  // violation below 'Multiline comment line(s) 12, 13 should start with leading asterisk'
  /*
     This is
     okay.
   */

  // violation below 'Multiline comment line(s) 18, 20, 22 should start with leading asterisk'
  /* Line 1
     Line 2
   * Line 3
     Line 4
   * Line 5
     Line 6
   */

  // violation below 'Multiline comment line(s) 27 should start with leading asterisk'
  /*
     Some Comment */

  /** Bad Examples. */
  public void missingLeadingAsterisk() {}

  /*
   * Some Comment
   * Some Comment2
   */

  /*
   * This is
   * okay.
   */

  /* Line 1
   * Line 2
   * Line 3
   * Line 4
   * Line 5
   * Line 6
   */

  /*
   * Some Comment */

  /** Good Examples. */
  public void presentLeadingAsterisk() {}

}
