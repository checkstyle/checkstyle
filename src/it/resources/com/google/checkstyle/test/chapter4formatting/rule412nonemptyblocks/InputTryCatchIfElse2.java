package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** Some javadoc. */
public class InputTryCatchIfElse2 {
  /** Some javadoc. */
  public static void main(String[] args) {
    boolean after = false;

    try {
      /* foo */
    } catch (NullPointerException e) {
      /* foo */
    } catch (Exception e) {
      /* foo */
    } finally {
      after = true;
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
    } else if (after) {} else if (after) {
      System.out.println("before");
    } else if (!after) { /* foo */ } else {
      System.out.println("before");
    }

    if (after) {
      System.out.println("foo");
    } else if (false) {
      System.out.println("foo");
    } else {
      System.out.println("foo");
    }

    if (after) {
      System.out.println("after");
      // violation below ''}' at column 5 should be on the same line as .* multi-block statement'
    }
    else if (after) {
      System.out.println("before");
      // violation below ''}' at column 5 should be on the same line as .* multi-block statement'
    }
    else {
      System.out.println("before");
    }

    if (after) {
      System.out.println("after");
      // violation below ''}' at column 5 should be on the same line as .* multi-block statement'
    }
    else if (after) {
      System.out.println("before");
      // violation below ''}' at column 5 should be on the same line as .* multi-block statement'
    }
    else if (after) {
      System.out.println("before");
      // violation below ''}' at column 5 should be on the same line as .* multi-block statement'
    }
    else {
      System.out.println("before");
    }

    if (after) {
      System.out.println("after");
      // violation below ''}' at column 5 should be on the same line as .* multi-block statement'
    }
    else if (after) {
      System.out.println("before");
    } else {
      System.out.println("before");
    }

    if (after) {
      System.out.println("after");
    } else if (after) {
      System.out.println("before");
      // violation below ''}' at column 5 should be on the same line as .* multi-block statement'
    }
    else {
      System.out.println("before");
    }
  }
}
