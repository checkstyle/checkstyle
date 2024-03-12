/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

public class InputCommentsIndentationInSwitchBlockOne {

    private static void fooSwitch() {
        switch("") {
            case "0": //some comment
            case "1":
                // my comment
                foo1();
                break;
            case "2":
                // my comment
                //comment
                foo1();
                // comment
                break;
            case "3":
                // violation below '.* incorrect .* level 12, expected is 16,.* same.* as line 28.'
            /* violation */
                foo1();
                /* com */
                break;
            case "5":
                foo1();
                   // violation '.* incorrect .* level 19, expected is 16, 12, .* as line 32, 34.'
            case "6":
                int k = 7;
                // fall through
            case "7":
                if (true) {}
                   // violation '.* incorrect .* level 19, expected is 16, 12, .* as line 38, 40.'
            case "8":
                break;
            case "9":
                foo1();
                // fall through
            case "10": {
                if (true) {}
                // fall through
            }
            case "11": {
            // fall through
            }
            case "28": {
                // fall through
            }
            case "12": {
      // violation '.* incorrect .* level 6, expected is 16, .* same .* as line 57.'
                int i;
            }
            case "13": {
                       // some comment in empty case block
            }
            case "14": {
        // violation '.* incorrect .* level 8, expected is 12, .* same .* as line 64.'
            }
            case "15": {
                foo1();
                      // violation '.* incorrect .* level 22, expected is 16,.* same.* as line 66.'
            }
            case "16": {
                int a;
            }
            // fall through
            case "17": {
                int a;
            }
              // violation '.* incorrect .* level 14, expected is 12, 16,.* same.* as line 73, 77.'
                case "18": { System.lineSeparator();
                }   // trailing comment
            case "19":
                // comment
            case "20":
            // comment
            case "21":
            default:
                // comment
                break;
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
