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

  /**
   * <!-- ignore this
   * spanning
   * multiple lines --> text here
   * text
   */
  public int getRBRACK() {
    return RBRACK;
  }

  /**
   * <code>0.0</code>&hellip;<code>0</code><!--
   * --><i>s</i><sub>1</sub>&hellip;<i>s</i><sub><i>n</i></sub>,
   * where there are exactly -(<i>n</i> + <i>i</i>) zeroes between
   */
  public int getMethodRef() {
    return METHOD_REF;
  }
}
