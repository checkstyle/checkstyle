package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** Some javadoc. */
public class InputFormattedRightCurlySwitchCasesBlocks {

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
        }
      default:
        int x = 0;
    }
  }

  /** Some javadoc. */
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

  /** Some javadoc. */
  public static void test4() {
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

  /** Some javadoc. */
  public static void test5() {
    int k = 0;
    switch (k) {
      case 1:
        {
          int x = 1;
        }
        break;
      default:
        {
          int a = 2;
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
      case 2:
        {
          break;
        }
      default:
    }
  }

  /** Some javadoc. */
  public static void test7() {
    int k = 0;
    switch (k) {
      case 1:
        {
          int x = 1;
          break;
        }
      default:
        {
          int x = 2;
        }
    }
  }
}
