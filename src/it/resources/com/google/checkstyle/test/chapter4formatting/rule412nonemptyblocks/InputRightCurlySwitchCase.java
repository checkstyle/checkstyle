package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** some javadoc. */
public class InputRightCurlySwitchCase {

  /** some javadoc. */
  public static void method0() {
    int mode = 0;
    switch (mode) {
      case 1:
        int x = 1;
        break;
      default:
        x = 0; } // violation ''}' at column 16 should be alone on a line.'
  }

  /** some javadoc. */
  public static void method1() {
    int mode = 0;
    switch (mode) {
      default:
        int x = 0; } // violation ''}' at column 20 should be alone on a line.'
  }

  /** some javadoc. */
  public static void method2() {
    int mode = 0;
    switch (mode) {
      case 1:
        int x = 1;
        break;
      default:
        x = 0;
    }
  }

  /** some javadoc. */
  public static void method3() {
    int mode = 0;
    switch (mode) {
      default:
        int x = 0;
    }
  }

  /** some javadoc. */
  public static void method4() {
    int mode = 0;
    switch (mode) {
      case 1:
        int y = 2;
        break;
      default:
        int x = 0;
    }
  }
}
