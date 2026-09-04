/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = LITERAL_WHILE


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

public class InputNoWhitespaceAfterWhile {

    boolean condition = false;

    void test() {

        while (condition) {
        // violation above, ''while' is followed by whitespace.'
        }

        while(condition) {
        }
    }
}
