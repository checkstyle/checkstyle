package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

final class InputWhitespaceTrailingWhitespace {

  // violation 2 lines below 'Trailing whitespace is not allowed'
  /**
   * Some javadoc.  
   */
  int test() {
    // violation below 'Trailing whitespace is not allowed'
    return 0;  
  }
}
