package com.doccomments.checkstyle.test.writingdoccomments.tagconventions.paramtag;

/**
 * Input for {@code @param} tag punctuation examples.
 */
class InputJavadocRegexpParamTag {

  /** Creates a sample instance. */
  InputJavadocRegexpParamTag() {}

  // violation below 'Punctuation should not be inserted before the description of the parameter'
  /**
   * Stores a value.
   *
   * @param value - value to store
   */
  void dashBeforeDescriptionWarn(String value) {}

  /**
   * Stores a value.
   *
   * @param value value to store
   */
  void dashBeforeDescriptionGood(String value) {}

  // violation below 'Punctuation should not be inserted before the description of the parameter'
  /**
   * Accepts a generic value.
   *
   * @param <T>: value type
   * @param value value to accept
   */
  <T> void colonBeforeGenericDescriptionWarn(T value) {}

  /**
   * Accepts a generic value.
   *
   * @param <T> value type
   * @param value value to accept
   */
  <T> void colonBeforeGenericDescriptionGood(T value) {}

  // violation below 'Punctuation should not be inserted before the description of the parameter'
  /**
   * Moves to coordinates.
   *
   * @param coordinates, x and y coordinates
   */
  void commaBeforeDescriptionWarn(int[] coordinates) {}

  /**
   * Moves to coordinates.
   *
   * @param coordinates x and y coordinates
   */
  void commaBeforeDescriptionGood(int[] coordinates) {}

}
