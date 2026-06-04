/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = LITERAL_WHILE


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

public class InputNoWhitespaceAfterWhile {

    void test() {

        while (false) { // violation, ''while' is followed by whitespace.'
        }

        while(false) {
        }
    }
}
