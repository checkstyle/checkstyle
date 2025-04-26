/*
NoWhitespace
allowLineBreaks = yes
tokens = SEMI


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespace;

public class InputNoWhitespaceEmptyForLoop {

    public static void f() {
        for (; ; ) {
            break;
        }
        for (int x = 0; ; ) {
            break;
        }
        for (int x = 0 ; ; ) { // violation
            break;
        }
        for (int x = 0; x < 10; ) {
            break;
        }
        for (int x = 0; x < 10 ; ) { // violation
            break;
        }
    }
}
