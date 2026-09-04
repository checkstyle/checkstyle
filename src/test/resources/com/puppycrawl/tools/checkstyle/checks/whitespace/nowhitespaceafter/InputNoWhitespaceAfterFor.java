/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = LITERAL_FOR


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

public class InputNoWhitespaceAfterFor {

    void test() {

        for (int i = 0; i < 10; i++) {
        // violation above, ''for' is followed by whitespace.'
        }

        for(int i = 0; i < 10; i++) {
        }
    }

}
