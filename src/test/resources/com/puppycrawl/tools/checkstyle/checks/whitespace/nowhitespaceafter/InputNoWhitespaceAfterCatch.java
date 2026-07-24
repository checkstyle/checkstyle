/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = LITERAL_CATCH


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

public class InputNoWhitespaceAfterCatch {

    void test() {

        try {
        } catch (Exception e) {
        // violation above, ''catch' is followed by whitespace.'
        }

        try {
        } catch(Exception e) {
        }
    }

}
