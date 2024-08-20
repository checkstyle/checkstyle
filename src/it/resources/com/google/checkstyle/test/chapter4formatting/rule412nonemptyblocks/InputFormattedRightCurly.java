package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** some javadoc. */
public class InputFormattedRightCurly {
  /** some javadoc. */
  public static void main(String[] args) {
    boolean after = false;
    try {
      /* foo */
    } finally {
      after = true;
    }
  }
}
