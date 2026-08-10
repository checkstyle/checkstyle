package com.doccomments.checkstyle.test.writingdoccomments.descriptions.avoidlatin;

/**
 * Input for incorrect Avoid Latin examples.
 */
class InputIncorrectAvoidLatin {

  /** Creates a sample instance. */
  InputIncorrectAvoidLatin() {}

  // violation below 'Javadoc content matches the illegal pattern'
  /**
   * AKA this name is a shortcut.
   */
  void usesAka() {}

  // violation below 'Javadoc content matches the illegal pattern'
  /**
   * Replaces text, i.e. normalizes it.
   */
  void usesIe() {}

  // violation below 'Javadoc content matches the illegal pattern'
  /**
   * Returns a default, e.g. an empty string.
   *
   * @return default value
   */
  String usesEg() {
    return "";
  }

  // violation below 'Javadoc content matches the illegal pattern'
  /**
   * Picks a value, viz. the configured fallback.
   */
  void usesViz() {}

}
