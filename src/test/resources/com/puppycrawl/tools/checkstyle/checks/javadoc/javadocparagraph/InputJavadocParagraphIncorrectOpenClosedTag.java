/*
JavadocParagraph
violateExecutionOnNonTightHtml = (default)false
allowNewlineParagraph = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

/**
 * Some summary.
 *
 * <p>  Some Javadoc. </p>
 */
// violation 2 lines above '<p> tag should be placed immediately before the first word'
public class InputJavadocParagraphIncorrectOpenClosedTag {

  /**
   * some javadoc.
   * <p></p>
   */
  // violation 2 lines above '<p> tag should be preceded with an empty line.'
  int a;

  // violation 2 lines below '<p> tag should be preceded with an empty line.'
  /**
   * Some Javadoc.<P>
   * Some Javadoc.<P></P>
   */
  // violation 2 lines above '<p> tag should be preceded with an empty line.'
  int b;

  // violation below 'Redundant <p> tag.'
  /**<p>some javadoc.</p>
   *
   * Some <p>Javadoc.</p>
   *
   * Some <p>Javadoc.</p>
   */
  // violation 5 lines above 'Empty line should be followed by <p> tag on the next line.'
  // violation 4 lines above 'Empty line should be followed by <p> tag on the next line.'
  int c;
}
