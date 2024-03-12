/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

public class InputCommentsIndentationInSwitchBlockTwo {

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

    public void fooDotInCaseBlock() {
        int i = 0;
        String s = "";

        switch (i) {
            case -2:
                // what
                i++;
                // no break here
            case 0:
                // what
                s.indexOf("ignore");
                // no break here
            case -1:
                 // what
                 s.indexOf("no way");
               // violation '.* incorrect.*level 15, expected is 17, 12,.*same.* as line 42, 44.'
            case 1:
            case 2:
                i--;
                // no break here
            case 3: { }
            // fall through


        }

        String breaks = ""
        // violation '.* incorrect .* level 8, expected is 12, .* same .* as line 56.'
            + "</table>"
            // middle
            + ""
        // end
        ;
    }

    public void foo2() {
        int a = 1;
        switch (a) {
            case 1:
            default:
    // violation '.* incorrect .* level 4, expected is 8, .* same .* as line 69.'
        }
    }

    public void foo3() {
        int a = 1;
        switch (a) {
            case 1:
            default:

                // comment
        }
    }

    public void foo4() {
        int a = 1;
        switch (a) {
            case 1:
                int b;
                  // violation '.* incorrect .* level 18, expected is 16, 12, .* as line 86, 88.'
            default:
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
}
