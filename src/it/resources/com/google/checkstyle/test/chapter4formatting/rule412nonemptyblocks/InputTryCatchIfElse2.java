package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** some javadoc. */
public class InputTryCatchIfElse2 {
  /** some javadoc. */
  public static void main(String[] args) {
    boolean after = false;
    try {
      /* foo */
    } finally { after = true; }
    // violation above ''{' at column 15 should have line break after.'

    try {
      /* foo */
    } catch (NullPointerException e) {
      // violation below 'Empty catch block.'
    } catch (Exception e) {
    } finally {
      after = true;
    }

    if (after) {
      System.out.println("after");
    } else {}

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
    } else if (after) {} else if (after) {
      System.out.println("before");
    } else if (!after) { /* foo */ } else {
      System.out.println("before");
    }
  }
}
