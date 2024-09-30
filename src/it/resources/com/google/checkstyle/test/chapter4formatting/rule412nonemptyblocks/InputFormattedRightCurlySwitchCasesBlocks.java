package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** some javadoc. */
public class InputFormattedRightCurlySwitchCasesBlocks {

  /** some javadoc. */
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

  /** some javadoc. */
  public static void test() {
    int mode = 0;
    switch (mode) {
      case 1:
        {
          int x = 1;
          break;
        }
      default:
        int x = 0;
    }
  }

  /** some javadoc. */
  public static void test1() {
    int k = 0;
    switch (k) {
      case 1:
        {
          int x = 1;
        }
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
        {
          break;
        }
      default:
        {
          break;
        }
    }
  }

  /** some javadoc. */
  public static void test3() {
    int k = 0;
    switch (k) {
      case 1:
        {
          int x = 1;
          break;
        }
      case 2:
        {
          int x = 2;
        }
        break;
      default:
        {
          int x = 2;
        } // false-negative
    }
  }

  /** some javadoc. */
  public static void test4() {
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

  /** some javadoc. */
  public static void test5() {
    int mode = 0;
    switch (mode) {
      case 1:
        {
          int x = 1;
          break;
        }
      default:
        {
          int x = 0;
        }
    }
  }

  /** some javadoc. */
  public static void test6() {
    int k = 0;
    switch (k) {
      case 1:
        {
          int x = 1;
        }
        break;
      case 2:
        {
          int x = 2;
        }
        break;
      default:
        {
          int a = 2;
        }
    }
  }

  /** some javadoc. */
  public static void test7() {
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
    }
  }

  /** some javadoc. */
  public static void test8() {
    int k = 0;
    switch (k) {
      case 1:
        {
          int x = 1;
          break;
        }
      case 2:
        {
          int x = 2;
        }
        break;
      default:
        {
          int x = 2;
        }
    }
  }
}
