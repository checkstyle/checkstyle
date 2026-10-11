package com.google.checkstyle.test.chapter4formatting.rule4861blockcommentstyle;

/** Some javadoc. */
public class InputCommentsIndentationInEmptyBlock {

  private void foo1() {
    int a = 5;
    int b = 3;
    int v = 6;
    if (a == b && v == b
            || (a == 1
            // violation 2 lines below '.* should be the same level as line 18.'
            // violation 3 lines below '.* should be the same level as line 18.'
        /// // odd indentation comment
            //
              /*   // odd indentation comment
               * one fine day ... */
            && b == 1)) {
      // Cannot clearly detect user intention of explanation target.
    }
  }

  private void foo2() {
    int a = 5;
    int b = 3;
    int v = 6;
    if (a == b && v == b || (a == 1 && b == 1)) {

      // comment
    }
  }

  private void foo3() {
    int a = 5;
    int b = 3;
    int v = 6;
    if (a == b && v == b || (a == 1 && b == 1)) {
// odd indentation comment
    }
    // violation 2 lines above '.* indentation should be the same level as line 39.'
  }

  // Comments here should be ok by Check
  @SuppressWarnings("unused") // trailing
  private static void foo4() { // trailing
    if (true) { // trailing comment
      // some comment
    }
    if (true) { // trailing comment
    }
  }

  // Comments here should be ok by Check
  @SuppressWarnings("unused") // trailing
  private static void foo5() { // trailing
    if (true) { // trailing comment
      // violation below '.* indentation should be the same level as line 59.'
// odd indentation comment
    }
    if (true) { // trailing comment
    }
  }

  /** Some javadoc. */
  public void foo6() {
    try {
      /* foo */
    } catch (Exception e) {
// odd indentation comment
    }
    // violation 2 lines above '.* indentation should be the same level as line 70.'
  }

  /** Some javadoc. */
  public void foo7() {
    try {
      /* foo */
    } catch (Exception e) {
      // OOOO: handle exception here
    }
  }

  private static class MyClass extends Object {
    // no members
  }
}
