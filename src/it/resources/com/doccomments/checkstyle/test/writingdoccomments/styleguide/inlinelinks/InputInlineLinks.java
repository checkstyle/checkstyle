package com.doccomments.checkstyle.test.writingdoccomments.styleguide.inlinelinks;

/**
 * Input for inline link examples.
 */
class InputInlineLinks {

  /** Creates a sample instance. */
  InputInlineLinks() {}

  // violation 2 lines below 'String' should not be linked
  /**
   * Returns a {@link String} representation.
   */
  void formatNameWarn() {}

  /**
   * Returns a {@code String} representation.
   */
  void formatNameGood() {}

  // violation 3 lines below 'List' should be linked only on its first occurrence
  /**
   * Reads a {@link List}.
   * The {@link List} stores display names.
   */
  void readNamesWarn() {}

  /**
   * Reads a {@link List}.
   * The List stores display names.
   */
  void readNamesGood() {}

  // violation 2 lines below 'java.lang.String' should not be linked
  /**
   * Parses a {@link java.lang.String} value.
   */
  void parseNameWarn() {}

  /**
   * Parses a {@code java.lang.String} value.
   */
  void parseNameGood() {}

}
