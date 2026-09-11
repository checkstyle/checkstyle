/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = LITERAL_IF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

public class InputNoWhitespaceAfterLiteralIf {
    void test(boolean condition) {
        if(condition) {
        }
        if (condition) {
        // violation above, ''if' is followed by whitespace.'
        }
        if
            (condition) {
        }
    }
}
