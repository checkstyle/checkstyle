package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** some javadoc. */
public class InputFormattedTryCatchIfElse2 {
  /** some javadoc. */
  public static void main(String[] args) {
    boolean after = false;
    try {
      /* foo */
    } finally {
      after = true;
    }

    try {
      /* foo */
    } catch (NullPointerException e) {
      // 2 violations 3 lines below:
      //  ''}' at column 5 should be alone on a line.'
      //  'Empty catch block.'
    } catch (Exception e) {
    } finally {
      // violation above ''}' at column 5 should be alone on a line.'
      after = true;
    }

    if (after) {
      System.out.println("after");
    } else {
    }

    if (after) {
      System.out.println("after");
    } else if (after) {
      System.out.println("before");
    } else if (after) {
      System.out.println("before");
    } else {
      System.out.println("before");
    }

    if (after) {
      System.out.println("after");
    } else if (after) {
    } else if (after) {
      System.out.println("before");
    } else if (!after) {
      /* foo */
    } else {
      System.out.println("before");
    }
  }
}
