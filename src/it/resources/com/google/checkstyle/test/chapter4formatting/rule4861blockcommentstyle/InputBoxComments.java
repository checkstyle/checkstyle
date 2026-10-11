package com.google.checkstyle.test.chapter4formatting.rule4861blockcommentstyle;

// violation below 'Comment uses box-like repetitive character pattern.'
// =========================================
// Box with slashes
// =========================================
// violation above 'Comment uses box-like repetitive character pattern.'

// violation below 'Comment uses box-like repetitive character pattern.'
// #########################################
// Box with hashes
// #########################################
// violation above 'Comment uses box-like repetitive character pattern.'

// violation below 'Comment uses box-like repetitive character pattern.'
// *****************************************
// Box with asterisks
// *****************************************
// violation above 'Comment uses box-like repetitive character pattern.'

/** Some javadoc. */
public class InputBoxComments {

  // violation below 'Comment uses box-like repetitive character pattern.'
  // =========================================
  // Methods with separators
  // =========================================
  // violation above 'Comment uses box-like repetitive character pattern.'

  // box with only 9 chars
  void myFunc2() {
    // violation below 'Comment uses box-like repetitive character pattern.'
    // =========
    int y = 2;
  }

  // box with only 9 chars
  // violation below 'Comment uses box-like repetitive character pattern.'
  // *********
  void myFunc3() {
    int z = 3;
  }

  // box with only 9 chars
  void myFunc4() {
    // violation below 'Comment uses box-like repetitive character pattern.'
    // #########
    int a = 4;
  }

  // mixed text with separators is fine
  // ========= Section separator =========
  void myFunc5() {
    int b = 5;
  }

  // only 6 chars (below 9-char threshold)
  void myFunc6() {
    // ######
    int c = 6;
  }

  // only 7 chars (below 9-char threshold)
  void myFunc7() {
    // *******
    int d = 7;
  }

  // only 8 chars (below 9-char threshold)
  void myFunc8() {
    // ========
    int e = 8;
  }

  // normal block comment
  void myFunc9() {
    /* normal block comment */
    int f = 9;
  }

  // normal Javadoc comment
  /*
   * normal Javadoc comment.
   */
  void myFunc10() {
    int g = 10;
  }

  // has text after separators, not a pure box line
  void myFunc11() {
    // ========= this is fine =========
    int h = 11;
  }
}
