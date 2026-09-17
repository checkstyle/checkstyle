/*
CommentsIndentation
tokens = (default)SINGLE_LINE_COMMENT, BLOCK_COMMENT_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

public class InputCommentsIndentationSwitchWhen {

    void testColonSwitch(Object obj) {
        switch (obj) {
            case ColoredPoint(int a, int b, boolean c)
                   // violation '.* incorrect .* level 19, expected is 20,.* as line 16.'
                    when (a >= b) :
                 // violation '.* incorrect .* level 17, expected is 16,.* as line 18.'
                { } break;
            case ColoredPoint(int a, int b, boolean c) when (b >= 100) // comment 3
                    :
                     /* // violation '.* incorrect .* level 21, expected is 12,.* as line 24.'
                      *
                      */
            { }
             // violation '.* incorrect .* level 13, expected is 12,.* as line 26.'
            break;
            default : System.out.println("none"); // comment 6
        }
    }

    void testArrowSwitch(Object obj) {
        switch (obj) {
            case ColoredPoint(int a, int b, boolean c)
                   // violation '.* incorrect .* level 19, expected is 20,.* as line 35.'
                    when (a >= b)
                            // violation '.* incorrect .* level 28, expected is 20,.* as line 37.'
                    -> { }
            case ColoredPoint(int a, int b, boolean c) when (b >= 100) // comment 3
                    ->
                     /* // violation '.* incorrect .* level 21, expected is 12,.* as line 43.'
                      *
                      */
            { }
             // violation '.* incorrect .* level 13, expected is 12, 12,.* as line 43, 45.'
            default -> System.out.println("none");
        }
    }

    void testProperlyIndentedComments(Object obj) {
        switch (obj) {
            // comment
            case ColoredPoint(int a, int b, boolean c)
                    // comment
                    when (a >= b)
                    // comment
                    -> {
                // comment
                System.out.println(a);
                // comment
            }
            // comment
            case ColoredPoint(int a, int b, boolean c) when (b >= 100) ->
                    /* comment
                     *
                     */
                    System.out.println(b);
            // comment
            default -> {
            }
            // comment
        }
    }

    record ColoredPoint(int p, int x, boolean c) { }
}
