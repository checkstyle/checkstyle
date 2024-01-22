/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

public class InputCommentsIndentationInSwitchBlockFour {

    private static void foo1() {
        if (true) {
            switch(1) {
                case 0:

                case 1:
                        // violation '.* incorrect .* level 24, expected is 20,.*same.* as line 97.'
                    int b = 10;
                default:
                 // comment
            }

        }
    }

    public void foo5() {
        int a = 1;
        switch (a) {
            case 1:
                int b;
            // comment
            default:
        }
    }

    public void foo6() {
            int a = 1;
            switch (a) {
                case 1:
                    int b;
                    // comment
                default:
            }
        }

     public void foo14() {
        int a = 1;
        switch (a) {
            case 1:
            default:
// violation '.* incorrect .* level 0, expected is 8, .* same .* as line 320.'
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
