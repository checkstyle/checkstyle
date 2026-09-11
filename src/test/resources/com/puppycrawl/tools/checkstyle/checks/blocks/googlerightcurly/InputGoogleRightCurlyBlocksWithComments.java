/*
GoogleRightCurly

*/

package com.puppycrawl.tools.checkstyle.checks.blocks.googlerightcurly;

public class InputGoogleRightCurlyBlocksWithComments {

    /** Class has a comment. */
    class NotEmpty {
        // some comment
    }
    /** Test Class. */
    class SomeClass {
        int a = 1;
        /* some comment */ }
    // violation above ''}' at column 28 should be alone on a line'

    class FollowedByComment {
        int a = 1;
    } // some comment
    // violation above ''}' at column 5 should be alone on a line'
    void method() {
        int a = 2;
    }
    // comment
    class FollowedByJavadoc {
        int a = 1;
    } /** Some javadoc. */
    public void method2() {}
    // violation 2 lines above ''}' at column 5 should be alone on a line'

    class NotFollowedByJavadoc {
        int a = 1;
    }
    /** Some javadoc. */
    public void method3() {
        int a = 1;
        if (a == 1) {
            int b = a;
        }
        // some comment
        else if (a == 2) {

        }
        // some comment
        else {
            a = 0;
        }
    }

    void method4() {
        int a = 1;
        if (a == 1) {
            int b = 2;
        } /* Some Comment. */
        // violation above ''}' at column 9 should be alone on a line'

        if (a == 2) {
            int b = 1;
        } // Some comment.
        // violation above ''}' at column 9 should be alone on a line'
    }
}
