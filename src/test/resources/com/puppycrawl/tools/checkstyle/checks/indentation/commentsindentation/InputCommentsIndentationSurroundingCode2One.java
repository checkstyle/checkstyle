/*
CommentsIndentation
tokens = SINGLE_LINE_COMMENT


*/

// comment
package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

import java.util.Arrays;

// some
public class InputCommentsIndentationSurroundingCode2One
{
    private void foo1() {
        if (true) {
            // here initialize some variables
            int k = 0; // trailing comment
              // violation '.* incorrect .* level 14, expected is 12, .* same .* as line 21.'
            int b = 10;
            // sss
        }
    }

    private void foo2() {
        if (true) {
            /* some */
            int k = 0;
                /* violation */
            int b = 10;
                /* violation
                 * */
            double d; /* trailing comment */
                /* violation
             *
                */
            boolean bb;
            /***/
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
        int a = 5, b = 3, v = 6;
        if (a == b
            && v == b || ( a ==1
                           /// violation '.* incorrect .* level 27, expected is 36, .* as line 60.'
                       /* violation
                        * one fine day ... */
                                    && b == 1)   ) {
        }
    }

    private static void com() {
        /* here's my weird trailing comment */ boolean b = true;
    }

    private static final String[][] mergeMatrix = {
        // This example of trailing block comments was found in PMD sources.
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
     * some javadoc
     */
    private static void l() {
    }

    public void foid5() {
        String s = "";
        s.toString().toString().toString();
        // comment
    }

    public void foo6() {
              // comment
              // ...
              // block
              // ...
              // violation '.* incorrect .* level 14, expected is 8, .* same .* as line 98.'
        String someStr = new String();
    }

    public void foo7() {
             // comment
             // ...
             // block
             // violation '.* incorrect .* level 13, expected is 8, .* same .* as line 107.'
        // comment
        String someStr = new String();
    }

    // Test
} // The Check should not throw NPE here!
// The Check should not throw NPE here!
