/*
CommentsIndentation
tokens = BLOCK_COMMENT_BEGIN


*/

// comment
package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

import java.util.Arrays;

// some
public class InputCommentsIndentationSurroundingCode3Two
{

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
        // violation below '.* incorrect .* level 12, expected is 8, .* same .* as line 44.'
            /* some comment */
        hashCode();
    }

    public void foo12() {
        // violation below '.* incorrect .* level 4, expected is 8, .* same .* as line 50.'
    /* some comment */
        hashCode();
    }

    public void foo13() {
        hashCode();
        // violation below '.* incorrect .* level 4, expected is 8, .* same .* as line 56.'
    /* some comment */
    }

    public void foo14() {
        hashCode();
        /*

        Test
        */
        // Test
    }

    public InputCommentsIndentationSurroundingCode3Two() {
    }

    // Test
} // The Check should not throw NPE here!
// The Check should not throw NPE here!
