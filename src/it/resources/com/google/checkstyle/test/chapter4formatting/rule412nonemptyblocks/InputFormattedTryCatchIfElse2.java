package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** Some javadoc. */
public class InputFormattedTryCatchIfElse2 {
  /** Some javadoc. */
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
    } else if (after) {
    } else if (after) {
      System.out.println("before");
    } else if (!after) {
      /* foo */
    } else {
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
    } else if (after) {
      System.out.println("before");
    } else {
      System.out.println("before");
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
      System.out.println("before");
    } else {
      System.out.println("before");
    }

    if (after) {
      System.out.println("after");
    } else if (after) {
      System.out.println("before");
    } else {
      System.out.println("before");
    }
  }
}
