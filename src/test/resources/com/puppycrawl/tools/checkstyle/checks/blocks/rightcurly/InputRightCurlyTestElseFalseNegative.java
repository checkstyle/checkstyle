/*
RightCurly
option = same
tokens = LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE
*/
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

/** some javadoc. */
public class InputRightCurlyTestElseFalseNegative {

    /** some javadoc. */
    void foo() {
        int a = 18;

        if (a == 18) {
        } else {} // violation '}' at column 13

        if (a == 18) {
        } else { } // violation '}' at column 14

        if (a == 18) {} else {} // violation '}' at column 27

        if (a == 18) {
        } // ok - no else

        if (a == 19) {} // ok - no else
    }

    void bar() {} // ok
}
