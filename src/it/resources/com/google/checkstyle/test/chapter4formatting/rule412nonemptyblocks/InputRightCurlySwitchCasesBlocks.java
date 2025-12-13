package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** Some javadoc. */
public class InputRightCurlySwitchCasesBlocks {

  /** Some javadoc. */
  public static void test0() {
    int mode = 0;
    switch (mode) {
      case 1:
        {
          int x = 1;
          break;
        }
      case 2:
        {
          int x = 0;
          break;
        }
      default:
        {
          int x = 0;
        }
    }
  }

  /** Some javadoc. */
  public static void test() {
    int mode = 0;
    switch (mode) {
      case 1:
        {
          int x = 1;
          break;
        } default:
        int x = 0;
        // 2 violations 2 lines above:
        //  ''}' at column 9 should be alone on a line.'
        //  ''case' child has incorrect indentation level 8, expected level should be 6.'
    }
  }

  /** Some javadoc. */
  public static void test1() {
    int k = 0;
    switch (k) {
      case 1:
        {
        int x = 1; } // violation ''}' at column 20 should be alone on a line.'
        break;
      case 2:
        int x = 2;
        break;
      default:
    }
  }

  /** Some javadoc. */
  public static void test2() {
    int mode = 0;
    switch (mode) {
      case 1:
        int x = 1;
        break;
      case 2:
        {
          break;
        }
      default:
        {
          break;
        }
    }
  }

  /** Some javadoc. */
  public static void test3() {
    int k = 0;
    switch (k) {
      case 1:
        {
          int x = 1;
          break;
        }
      default:
        {
          int x = 2; } // false-negative until #14782
    }
  }

  /** Some javadoc. */
  public static void test4() {
    int mode = 0;
    switch (mode) {
      case 1: { // violation ''{' at column 15 should be on a new line.'
          int x = 1; // violation '.* incorrect indentation level 10, expected level should be 8.'
          break; // violation '.* incorrect indentation level 10, expected level should be 8.'
          // 2 violations 3 lines below:
          //  ''block rcurly' has incorrect indentation level 8, expected level should be 6.'
          //  ''}' at column 9 should be alone on a line.'
        } default:
          { // violation '.* incorrect indentation level 10, expected level should be 8.'
                  int x = 0; // violation '.* incorrect indentation .*, expected .* 8, 10.'
          } // violation '.* incorrect indentation level 10, expected level should be 8.'
    }
  }

  /** Some javadoc. */
  public static void test5() {
    int k = 0;
    switch (k) {
      case 1: { // violation ''{' at column 15 should be on a new line.'
        int x = 1; } // violation ''}' at column 20 should be alone on a line.'
        break;
      default: { // violation ''{' at column 16 should be on a new line.'
  int a = 2; // violation '.* incorrect indentation level 2, expected level should be 8.'
      }
    }
  }

  /** Some javadoc. */
  public static void test6() {
    int mode = 0;
    switch (mode) {
      case 1:
        int x = 1;
        break;
      case 2: { // violation ''{' at column 15 should be on a new line.'
          break; // violation '.* incorrect indentation level 10, expected level should be 8.'
        } // violation '.* incorrect indentation level 8, expected level should be 6.'
      default:
    }
  }

  /** Some javadoc. */
  public static void test7() {
    int k = 0;
    switch (k) {
      case 1:
        {
            int x = 1; // violation '.* incorrect indentation .* 12, expected .* following: 8, 10.'
            break; // violation '.* incorrect indentation .* 12, expected .* following: 8, 10.'
        }
      default: { // violation ''{' at column 16 should be on a new line.'
        int x = 2; } // false-negative until #14782
    }
  }
}
