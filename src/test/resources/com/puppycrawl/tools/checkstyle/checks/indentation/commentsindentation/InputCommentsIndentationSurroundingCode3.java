/*
CommentsIndentation
tokens = BLOCK_COMMENT_BEGIN


*/

// comment
package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

import java.util.Arrays;

// some
public class InputCommentsIndentationSurroundingCode3
{
    private void foo1() {
        if (true) {
            // here initialize some variables
            int k = 0; // trailing comment
            int b = 10;
            // sss
        }
    }

    private void foo2() {
        if (true) {
            /* some */
            int k = 0;
            // violation below '.* incorrect .* level 16, expected is 12, .* same .* as line 31.'
                /* some comment */
            int b = 10;
                /* // violation '.* incorrect .* level 16, expected is 12, .* same .* as line 34.'
                 * */
            double d; /* trailing comment */
                /* // violation '.* incorrect .* level 16, expected is 12, .* same .* as line 38.'
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
                       /* // violation '.* incorrect .* level 23, expected is 36, .* as line 59.'
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
        String someStr = new String();
    }

    public void foo7() {
             // comment
             // ...
             // block
        // comment
        String someStr = new String();
    }

    public void foo8() {
        String s = new String(); // comment
                                 // ...
                                 // block
                                 // ...
        String someStr = new String();
    }


    public String foo9(String s1, String s2, String s3) {
        return "";
    }

    public void foo10()
        throws Exception {

        final String pattern = "^foo$";

        final String[] expected = {
            "7:13: " + foo9("", "", ""),
            // comment
        };
    }

    public void foo11() {
        // violation below '.* incorrect .* level 12, expected is 8, .* same .* as line 134.'
            /* some comment */
        hashCode();
    }

    public void foo12() {
        // violation below '.* incorrect .* level 4, expected is 8, .* same .* as line 140.'
    /* some comment */
        hashCode();
    }

    public void foo13() {
        hashCode();
        // violation below '.* incorrect .* level 4, expected is 8, .* same .* as line 144.'
    /* some comment */
    }

    public void foo14() {
        hashCode();
        /*

        Test
        */
        // Test
    }

    public InputCommentsIndentationSurroundingCode3() {
    }

    // Test
} // The Check should not throw NPE here!
// The Check should not throw NPE here!
