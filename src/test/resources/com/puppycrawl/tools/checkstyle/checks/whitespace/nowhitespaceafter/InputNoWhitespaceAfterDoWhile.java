/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = DO_WHILE


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

public class InputNoWhitespaceAfterDoWhile {
    void test() {
        do {
        } while(false);
        do {
        } while (false); // violation, ''while' is followed by whitespace.'
        do {
        } while
            (false);
    }
}