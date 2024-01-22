/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN
*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

public class InputCommentsIndentationInSwitchBlockThree {

    public void foo8() {
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
                s.toString().toString().toString();
                // comment
            case 4:
      // violation '.* incorrect .* level 6, expected is 12, 12, .* same .* as line 28, 30.'
            default:
        }
    }

    public void foo9() {
        int a = 5;
        switch (a) {
            case 1:
            case 2:
                // comment
        }
    }

    public void foo10() {
        int a = 5;
        switch (a) {
            case 1:
            default:
                // comment
        }
    }

    public void foo11() {
        int a = 5;
        switch (a) {
            case 1:
            case 2:
                // comment
        }
    }

    public void foo12() {
        int a = 5;
        switch (a) {
            // comment
            case 1:
        }
    }

    public void foo13() {
        int a = 5;
        switch (a) {
            case 1:
                /* comment */
            case 2:
                hashCode();
           /* // violation '.* incorrect .* level 11, expected is 16, 12, .* as line 75, 79.'
            violation
            */
            case 3: // comment
                hashCode();
           // violation '.* incorrect .* level 11, expected is 16, 12,.* same .* as line 80, 82.'
            case 4: // comment
                if (true) {}
                else {}
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

    private static void foo1() {
        if (true) {
            switch(1) {
                case 0:

                case 1:
                        // violation '.* incorrect .* level 24, expected is 20,.*same.* as line 109.'
                    int b = 10;
                default:
                 // comment
            }
        }
    }
}
