/*
NoWhitespaceBefore
allowLineBreaks = (default)false
tokens = (default)COMMA, SEMI, POST_INC, POST_DEC, ELLIPSIS, LABELED_STAT


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebefore;

public class InputNoWhitespaceBeforeEmptyForLoop {

    public static void f() {
        for (; ; ) { // OK
            break;
        }
        for (int x = 0; ; ) { // OK
            break;
        }
        for (int x = 0 ; ; ) { // violation
            break;
        }
        for (int x = 0; x < 10; ) { // OK
            break;
        }
        for (int x = 0; x < 10 ; ) { // violation
            break;
        }
    }
}
