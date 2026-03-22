package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

import java.util.Arrays;

// some

public class InputCommentsIndentationSurroundingCode2Two {


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

        /* empty */
        hashCode();
    }

    public void foo12() {
        /* empty */
        hashCode();
    }

    public void foo13() {
        hashCode();
        /* empty */
    }

    public void foo14() {
        hashCode();
        /*

        Test
        */
        // Test
    }

    public InputCommentsIndentationSurroundingCode2Two() {
    }
}// The Check should not throw NPE here!
// The Check should not throw NPE here!
