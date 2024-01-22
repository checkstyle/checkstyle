/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN
*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

public class InputCommentsIndentationInSwitchBlockTwo {

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
               // violation '.* incorrect.*level 15, expected is 17, 12,.*same.* as line 120, 122.'
            case 1:
            case 2:
                i--;
                // no break here
            case 3: { }
            // fall through
        }

        String breaks = ""
        // violation '.* incorrect .* level 8, expected is 12, .* same .* as line 134.'
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
    // violation '.* incorrect .* level 4, expected is 8, .* same .* as line 147.'
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
                  // violation '.* incorrect .* level 18, expected is 16, 12, .* as line 164, 166.'
            default:
        }
    }

    public void foo7() {
        int a = 2;
        String s = "";
        switch (a) {
            // comment
            // comment
            // comment
            case 1:
            case 2:
                // comment
                // comment
                foo1();
                // comment
            case 3:
                // comment
                // comment
                // comment
            case 4:
    // violation '.* incorrect .* level 4, expected is 12, 12, .* same .* as line 207, 209.'
            case 5:
                s.toString().toString().toString();
                      // violation '.*incorrect.* level 22, expected is 16, 12,.*as line 210, 214.'
                    // violation '.* incorrect .* level 20, expected is 16, 12,.*as line 210, 214.'
                 // violation '.* incorrect .* level 17, expected is 16, 12, .* as line 210, 214.'
            default:
        }
    }

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
}
