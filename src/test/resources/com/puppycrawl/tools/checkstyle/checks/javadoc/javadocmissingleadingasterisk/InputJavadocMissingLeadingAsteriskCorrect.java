/*
JavadocMissingLeadingAsterisk
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmissingleadingasterisk;

public class InputJavadocMissingLeadingAsteriskCorrect {

  /**
   * Java style.
   **/
  public int javaStyle;

  /** Single-line. **/
  public int singleLine;

  /** Scala style.
    */
  public int scalaStyle;

  /**
   ** More than one asterisk.
   **/
  public int manyAsterisks;

  /**
   *
   * Some blank lines.
   **
   **/
  public int blankLines;

  /** * Asterisk on first line.
   */
  public int firstLineAsterisk;

  /** * Asterisk on single-line. */
  public int singleLineAsterisk;

  /** ** Multiple asterisks on first line.
   */
  public int firstLineAsterisks;

  /** ** Multiple asterisks on single-line. */
  public int singleLineAsterisks;

  /**
   * Asterisk on last line.
   **/
  public int lastLineAsterisk;

  /**
   * Multiple asterisks on last line.
   ** */
  public int lastLineAsterisks;

  /***/
  public int emptyComment;

  /** */
  public int blankComment;

  /**
   *
   **
   */
  public int multiLineBlankComment;

  /**
   * @see #blockTag
   **/
  public int blockTag;

}

/**
*EOF immediately after NEWLINE
*@deprecated block tag
*/
class NoTextAfterNewLineCorrect {

}

class TokenTypes {
  /**
   * @see #RBRACK
   */
  public int METHOD_REF;
  /**
   * {@link TokenTypes#METHOD_REF
   * METHOD_REF},
   */
  public int RBRACK;
}
