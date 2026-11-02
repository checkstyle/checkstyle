package com.google.checkstyle.test.chapter7javadoc.rule711generalform;

/**
 * Input for BlockCommentEndPosition check.
 */
public class InputBlockCommentEndPosition {

  /** Single line javadoc is allowed. */
  int singleLineField;

  /**
   * Summary for valid method.
   *
   * @param arg some argument
   */
  void validMethod(int arg) {}

  // violation 4 lines below 'Block comment end must be on new line.'
  /**
   * Summary for invalid method.
   *
   * @param arg some argument */
  void invalidMethod(int arg) {}

  // violation 2 lines below 'Block comment end must be on new line.'
  /**
   * Summary for another invalid method. */
  void anotherInvalidMethod() {}

  /**
   * Summary for valid constructor.
   *
   * @param arg some argument
   */
  public InputBlockCommentEndPosition(int arg) {}

  // violation 4 lines below 'Block comment end must be on new line.'
  /**
   * Summary for invalid constructor.
   *
   * @param arg some argument... */
  public InputBlockCommentEndPosition(String arg) {}
}
