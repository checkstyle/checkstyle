package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

/** some javadoc. */
public class InputRightCurly {
  /** some javadoc. */
  public static void main(String[] args) {
    boolean after = false;
    try {
      /* foo */
    } finally { after = true; }
    // violation above ''{' at column 15 should have line break after.'
  }
}
