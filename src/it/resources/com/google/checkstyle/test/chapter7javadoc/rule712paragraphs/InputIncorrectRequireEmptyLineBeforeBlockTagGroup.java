package com.google.checkstyle.test.chapter7javadoc.rule712paragraphs;

// violation 3 lines below 'Javadoc tag '@since' should be preceded with an empty line.'
/**
 * Some Javadoc.
 * @since 8.36
 */
class InputIncorrectRequireEmptyLineBeforeBlockTagGroup {

  // violation 3 lines below 'Javadoc tag '@param' should be preceded with an empty line.'
  /**
   * This documents the private method.
   * @param thisParamTagNeedsNewline this documents the parameter.
   */
  private boolean paramTagNeedsNewline(boolean thisParamTagNeedsNewline) {
    return false;
  }

  // violation 3 lines below 'Javadoc tag '@param' should be preceded with an empty line.'
  /**
   * This documents the private method.
   * @param thisParamTagNeedsNewline this documents the parameter.
   * @return this one does not need an empty line, but the tag before this one does.
   */
  private boolean paramMultiTagNeedsNewline(boolean thisParamTagNeedsNewline) {
    return false;
  }
}
