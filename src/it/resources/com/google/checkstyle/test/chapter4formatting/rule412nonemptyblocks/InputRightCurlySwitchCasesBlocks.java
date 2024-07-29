package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** some javadoc. */
public class InputRightCurlySwitchCasesBlocks {

  /** some javadoc. */
  public static void test0() {
    int mode = 0;
    switch (mode) {
      case 1:
        { // violation ''{' at column 9 should be on the previous line.'
          int x = 1;
          break;
        }
      case 2:
        { // violation ''{' at column 9 should be on the previous line.'
          int x = 0;
          break;
        }
      default:
    }
  }

  /** some javadoc. */
  public static void test() {
    int mode = 0;
    switch (mode) {
      case 1:
        { // violation ''{' at column 9 should be on the previous line.'
          int x = 1;
          break;
        } default:
        int x = 0;
        // 2 violations 2 lines above:
        //  ''}' at column 9 should be alone on a line.'
        //  ''case' child has incorrect indentation level 8, expected level should be 6.'
    }
  }

  /** some javadoc. */
  public static void test1() {
    int k = 0;
    switch (k) {
      case 1:
        { // violation ''{' at column 9 should be on the previous line.'
        int x = 1; } // violation ''}' at column 20 should be alone on a line.'
        break;
      case 2:
        int x = 2;
        break;
      default:
    }
  }

  /** some javadoc. */
  public static void test2() {
    int mode = 0;
    switch (mode) {
      case 1:
        int x = 1;
        break;
      case 2:
        { // violation ''{' at column 9 should be on the previous line.'
          break;
        }
      default:
    }
  }

  /** some javadoc. */
  public static void test3() {
    int k = 0;
    switch (k) {
      case 1:
        { // violation ''{' at column 9 should be on the previous line.'
          int x = 1;
          break;
        }
      case 2:
        { // violation ''{' at column 9 should be on the previous line.'
        int x = 2; } // violation ''}' at column 20 should be alone on a line.'
        break;
      default:
    }
  }
}
