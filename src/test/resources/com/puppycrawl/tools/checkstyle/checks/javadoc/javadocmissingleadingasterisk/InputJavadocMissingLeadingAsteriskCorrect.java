/*
JavadocMissingLeadingAsterisk
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmissingleadingasterisk;

public class InputJavadocMissingLeadingAsteriskCorrect {

  /**
   * Java style.
   **/
  public int javaStyle; // ok

  /** Single-line. **/
  public int singleLine; // ok

  /** Scala style.
    */
  public int scalaStyle; // ok

  /**
   ** More than one asterisk.
   **/
  public int manyAsterisks; // ok

  /**
   *
   * Some blank lines.
   **
   **/
  public int blankLines; // ok

  /** * Asterisk on first line.
   */
  public int firstLineAsterisk; // ok

  /** * Asterisk on single-line. */
  public int singleLineAsterisk; // ok

  /** ** Multiple asterisks on first line.
   */
  public int firstLineAsterisks; // ok

  /** ** Multiple asterisks on single-line. */
  public int singleLineAsterisks; // ok

  /**
   * Asterisk on last line.
   **/
  public int lastLineAsterisk; // ok

  /**
   * Multiple asterisks on last line.
   ** */
  public int lastLineAsterisks; // ok

  /***/
  public int emptyComment; // ok

  /** */
  public int blankComment; // ok

  /**
   *
   **
   */
  public int multiLineBlankComment; // ok

  /**
   * @see #blockTag
   **/
  public int blockTag; // ok

}

/**
*EOF immediately after NEWLINE
*@deprecated block tag
*/
class NoTextAfterNewLineCorrect { // ok

}

class TokenTypes {
  /**
   * @see #RBRACK
   */
  public int METHOD_REF; // ok
  /**
   * {@link TokenTypes#METHOD_REF
   * METHOD_REF},
   */
  public int RBRACK; // ok
}
