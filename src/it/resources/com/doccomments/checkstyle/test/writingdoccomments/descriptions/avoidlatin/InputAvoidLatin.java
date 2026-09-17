package com.doccomments.checkstyle.test.writingdoccomments.descriptions.avoidlatin;

/**
 * Input for Avoid Latin examples.
 */
class InputAvoidLatin {

  /** Creates a sample instance. */
  InputAvoidLatin() {}

  /**
   * Creates a user, also known as an account owner.
   */
  void createsUserGood() {}

  // violation below 'Javadoc content matches the illegal pattern'
  /**
   * Creates a user, aka an account owner.
   */
  void createsUserWarn() {}

  /**
   * Normalizes text, that is, removes redundant separators.
   */
  void normalizesTextGood() {}

  /**
   * Selects a default value, to be specific, the empty value.
   */
  void selectsDefaultGood() {}

  // violation below 'Javadoc content matches the illegal pattern'
  /**
   * Normalizes text, i.e. removes redundant separators.
   */
  void normalizesTextWarn() {}

  /**
   * Adds one generated item, for example a missing identifier.
   */
  void addsGeneratedItemGood() {}

  // violation below 'Javadoc content matches the illegal pattern'
  /**
   * Adds one generated item, e.g. a missing identifier.
   */
  void addsGeneratedItemWarn() {}

  /**
   * Picks the fallback, in other words, the last configured value.
   */
  void picksFallbackGood() {}

  /**
   * Stores the display name, namely the visible user label.
   */
  void storesDisplayNameGood() {}

  // violation below 'Javadoc content matches the illegal pattern'
  /**
   * Picks the fallback, viz. the last configured value.
   */
  void picksFallbackWarn() {}

}
