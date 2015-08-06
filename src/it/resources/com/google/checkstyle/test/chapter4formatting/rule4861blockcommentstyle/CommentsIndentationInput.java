 // warn (some comment)
package com.google.checkstyle.test.chapter4formatting.rule4861blockcommentstyle;

import java.util.*;

// ok (comment)
public class CommentsIndentationInput {

  private void foo() {
    if (true) {
      // ok (here initialize some variables)
      int k = 0; // ok (trailing comment)
        // warn (initialize b)
      int b = 10;
      // ok (sss)
    }
  }

  private void foo1() {
    if (true) {
    /* ok (some) */
    int k = 0;
        /* // warn (some) */
    int b = 10;
        /* // warn
         * */
    double d; /* ok (trailing comment) */
        /* // warn
     *
        */
    boolean bb;
    /***/
    /* ok (my comment) */
    /*
     *
     *
     * ok (some)
     */
    /*
     * ok (comment)
     */
    boolean x;
    }
  }

  private void foo3() {
    int a = 5, b = 3, v = 6;
    if (a == b
            && v == b || (a == 1
                       /// warn (What about that case ? the same for block comments)
                   /* // warn
                    * one fine day ... */
                                && b == 1)) {
          // ok (code)
    }
  }

  // ok (Comments here should be ok by Check)
  @SuppressWarnings("unused") // ok (trailing)
  private static void check() { // ok (trailing)
    if (true) // ok (trailing comment)
    {
       // ok (some comment)
    }
    if (true) { // ok (trailing comment)

    }
    /** ok
    *
    */
  }

  private static void com() {
  /* ok (here's my weird trailing comment) */
  boolean b = true;
  }


  private static void cases() {
    switch ("") {
      case "0": // some comment
      case "1":
        // my comment
        com();
        break;
      case "2":
      // my comment
        //comment
        check();
        // comment
        break;
      case "3":
      /* com */
      check();
        /* com */
        break;
      case "5":
        check();
        // fall through
      case "6":
        int k = 7;
        // fall through
      case "7":
        if (true) {}
        // fall through
      case "8":
        break;
      default:
        // comment
        break;
    }
  }

  private static final String[][] mergeMatrix = {
        // TOP ALWAYS NEVER UNKNOWN
        /* TOP */{ "", },
        /* ALWAYS */{ "", "", },
       /* NEVER */{ "NEVER", "UNKNOWN", "NEVER", },
       /* UNKNOWN */{ "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN" }, };

  private void foo4() {
    if (!Arrays.equals(new String[]{""}, new String[]{""})
              /* wierd trailing comment */) {
    }
  }

  /**
   * ok (some javadoc)
  */
  private static void l() {
  }
} // The Check should not throw NPE here!
/* The Check should not throw NPE here! */
