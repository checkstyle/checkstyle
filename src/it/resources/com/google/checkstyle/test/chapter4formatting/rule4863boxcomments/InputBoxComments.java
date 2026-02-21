package com.google.checkstyle.test.chapter4formatting.rule4863boxcomments;

// =========================================
// violation above 'Comment should not be enclosed in a box.'
// Box with slashes
// =========================================
// violation above 'Comment should not be enclosed in a box.'

// #########################################
// violation above 'Comment should not be enclosed in a box.'
// Box with hashes
// #########################################
// violation above 'Comment should not be enclosed in a box.'

// *****************************************
// violation above 'Comment should not be enclosed in a box.'
// Box with asterisks
// *****************************************
// violation above 'Comment should not be enclosed in a box.'

/** Some javadoc. */
public class InputBoxComments {
  // =========================================
  // violation above 'Comment should not be enclosed in a box.'
  // Methods with separators
  // =========================================
  // violation above 'Comment should not be enclosed in a box.'

  void myFunc2() {
    // =========
    // violation above 'Comment should not be enclosed in a box.'
    int y = 2;
  }

  // *********
  // violation above 'Comment should not be enclosed in a box.'
  void myFunc3() {
    int z = 3;
  }

  void myFunc4() {
    // #########
    // violation above 'Comment should not be enclosed in a box.'
    int a = 4;
  }

  // ========= Section separator =========

  void myFunc5() {
    // ok - normal comment
    int b = 5;
  }

  void myFunc6() {
    // ok - only 6 chars (below 9-char threshold)
    // ######
    int c = 6;
  }

  void myFunc7() {
    // ok - only 7 chars
    // *******
    int d = 7;
  }

  void myFunc8() {
    // ok - only 8 chars (need 9+ for violation)
    // ========
    int e = 8;
  }

  void myFunc9() {
    /* ok - normal block comment */
    int f = 9;
  }

  /*
   * normal Javadoc comment.
   */
  void myFunc10() {
    int g = 10;
  }

  void myFunc11() {
    // ok - has text after separators
    // ========= this is fine =========
    int h = 11;
  }
}
