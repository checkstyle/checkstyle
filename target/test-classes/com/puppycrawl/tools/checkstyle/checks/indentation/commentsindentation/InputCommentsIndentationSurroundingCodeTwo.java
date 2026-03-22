/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

// comment
package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

// some

public class InputCommentsIndentationSurroundingCodeTwo {

    public void foo8() {
        String s = new String(); // comment
                                 // ...
                                 // block
                                 // ...
                                 // violation '.* incorrect.*level 33, expected is 8,.*as line 21.'
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
        // violation below '.* incorrect .* level 12, expected is 8, .* same .* as line 43.'
            /* empty */
        hashCode();
    }

    public void foo12() {
        // violation below '.* incorrect .* level 4, expected is 8, .* same .* as line 49.'
    /* empty */
        hashCode();
    }

    public void foo13() {
        hashCode();
        // violation below '.* incorrect .* level 4, expected is 8, .* same .* as line 53.'
    /* empty */
    }

    public void foo14() {
        hashCode();
        /*

        Test
        */
        // Test
    }

    public InputCommentsIndentationSurroundingCodeTwo() {
    }

    // Test
}// The Check should not throw NPE here!
// The Check should not throw NPE here!
