// comment

package com.google.checkstyle.test.chapter4formatting.rule4861blockcommentstyle;

import java.util.Arrays;

// some

/** Some javadoc. */
public class InputFormattedCommentsIndentationSurroundingCode {
  private void foo1() {
    if (true) {
      // here initialize some variables
      int k = 0; // trailing comment
      // odd indentation comment

      int b = 10;
      // sss
    }
  }

  private void foo2() {
    if (true) {
      /* some */
      int k = 0;
      /* // odd indentation comment
       */

      int b = 10;
      /* // odd indentation comment
       * */

      double d; /* trailing comment */
      /* // odd indentation comment
       *
       */

      boolean bb;
      /* my comment*/
      /*
       *
       *
       *  some
       */
      /*
       * comment
       */
      boolean x;
    }
  }

  private void foo3() {
    int a = 5;
    int b = 3;
    int v = 6;
    if (a == b && v == b
        || (a == 1
            /// // odd indentation comment
            /* // odd indentation comment
             * one fine day ... */

            && b == 1)) {
      /* foo */
    }
  }

  private static void com() {
    /* here's my weird trailing comment */ boolean b = true;
  }

  private static final String[][] mergeMatrix = {
    // This example of trailing block comments was found in PMD sources.
    /* TOP */ {
      "",
    },
    /* ALWAYS */ {
      "", "",
    },
    /* NEVER */ {
      "NEVER", "UNKNOWN", "NEVER",
    },
    /* UNKNOWN */ {"UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN"},
  };

  private void foo4() {
    if (!Arrays.equals(new String[] {""}, new String[] {""}) /* wierd comment */) {
      /* foo */
    }
  }

  /** Some javadoc. */
  private static void testing() {}

  /** Some javadoc. */
  public void foid5() {
    String s = "";
    s.toString().toString().toString();
    // comment
  }

  /** Some javadoc. */
  public void foo6() {
    // comment
    // ...
    // block
    // ...
    // odd indentation comment

    String someStr = new String();
  }

  /** Some javadoc. */
  public void foo7() {
    // comment
    // ...
    // block
    // odd indentation comment
    // comment

    String someStr = new String();
  }

  /** Some javadoc. */
  public void foo8() {
    String s = new String(); // comment
    // ...
    // block
    // ...
    // odd indentation comment

    String someStr = new String();
  }

  /** Some javadoc. */
  public String foo9(String s1, String s2, String s3) {
    return "";
  }

  /** Some javadoc. */
  public void foo10() throws Exception {

    final String pattern = "^foo$";

    final String[] expected = {
      "7:13: " + foo9("", "", ""),
      // comment
    };
  }
} // The Check should not throw NPE here!
// The Check should not throw NPE here!
