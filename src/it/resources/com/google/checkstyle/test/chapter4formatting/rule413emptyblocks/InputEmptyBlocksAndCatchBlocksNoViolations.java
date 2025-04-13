///
// Test case file for checkstyle.
///

package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

import java.io.IOException;

/** some javadoc. */
public class InputEmptyBlocksAndCatchBlocksNoViolations {
  private void foo6() {
    try {
      throw new IOException();
    } catch (IOException expected) { // This is expected
      int k = 0;
    }
  }

  /** some javadoc. */
  public void testTryCatch() {
    try {
      int y = 0;
      int u = 8;
      int e = u - y;
      return;
    } catch (Exception e) {
      System.identityHashCode(e);
      return;
    } finally {
      return;
    }
  }

  /** some javadoc. */
  public void testTryCatch3() {
    try {
      int y = 0;
      int u = 8;
      int e = u - y;
    } catch (IllegalArgumentException e) {
      System.identityHashCode(e); // some comment
      return;
    } catch (IllegalStateException ex) {
      System.identityHashCode(ex);
      return;
    }
  }

  /** some javadoc. */
  public void testTryCatch4() {
    int y = 0;
    int u = 8;
    try {
      int e = u - y;
    } catch (IllegalArgumentException e) {
      System.identityHashCode(e);
      return;
    }
  }

  /** some javadoc. */
  public void setFormats() {
    try {
      int k = 4;
    } catch (Exception e) {
      Object k = null;
      if (k != null) {
        k = "ss";
      } else {
        return;
      }
    }
  }

  /** some javadoc. */
  public void testIfElse() {
    if (true) {
      return;
    } else {
      return;
    }
  }

  /** some javadoc. */
  public void testIfElseIfLadder() {
    if (true) {
      return;
    } else if (false) {
      return;
    } else {
      return;
    }
  }

  /** some javadoc. */
  public void testSwtichCase() {
    switch (1) {
      case 1:
        return;
      case 2:
        return;
      default:
        return;
    }
  }
}
