/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

public class InputCommentsIndentationInSwitchBlock {

    private static void foo1() {
        if (true) {
            switch(1) {
                case 0:

                case 1:
                        // violation '.* incorrect .* level 24, expected is 20,.*same.* as line 19.'
                    int b = 10;
                default:
                 // comment
            }

        }
    }

    public void foo13() {
        int a = 5;
        switch (a) {
            case 1:
                /* comment */
            case 2:
                hashCode();
           /* // violation '.* incorrect .* level 11, expected is 16, 12, .* as line 33, 37.'
            violation
            */
            case 3: // comment
                hashCode();
           // violation '.* incorrect .* level 11, expected is 16, 12,.* same .* as line 38, 40.'
            case 4: // comment
                if (true) {

                }
                else {

                }
                // comment
            case 5:
                String s = ""
                    + 1
                    + "123";
                break;
                // comment
            case 6:
                String q = ""
                    + 1
                    + "123";
                // comment
            case 7:
                break;
        }
    }

    public void foo14() {
        int a = 1;
        switch (a) {
            case 1:
            default:
// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 70.'
        }
    }

    public void foo15() {
        int a = 1;
        switch (a) {
            case 1:
            default:
        // comment
        }
    }
}
