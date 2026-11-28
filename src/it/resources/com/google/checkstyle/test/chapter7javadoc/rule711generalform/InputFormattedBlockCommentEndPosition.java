package com.google.checkstyle.test.chapter7javadoc.rule711generalform;

/** Input for BlockCommentEndPosition check. */
public class InputFormattedBlockCommentEndPosition {

  /** Single line javadoc is allowed. */
  int singleLineField;

  /**
   * Summary for valid method.
   *
   * @param arg some argument
   */
  void validMethod(int arg) {}

  /**
   * Summary for invalid method.
   *
   * @param arg some argument
   */
  void invalidMethod(int arg) {}

  /** Summary for another invalid method. */
  void anotherInvalidMethod() {}

  /**
   * Summary for valid constructor.
   *
   * @param arg some argument
   */
  public InputFormattedBlockCommentEndPosition(int arg) {}

  /**
   * Summary for invalid constructor.
   *
   * @param arg some argument...
   */
  public InputFormattedBlockCommentEndPosition(String arg) {}
}
