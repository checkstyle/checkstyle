/*
NoWhitespaceAfter
allowLineBreaks = false
tokens = (default)ARRAY_INIT, AT, INC, DEC, UNARY_MINUS, UNARY_PLUS, BNOT, LNOT, \
         DOT, ARRAY_DECLARATOR, INDEX_OP


*/

package org.checkstyle.suppressionxpathfilter.whitespace.nowhitespaceafter;

public class InputXpathNoWhitespaceAfterLineBreaks {
    public void test() {
        java.lang
            . String s = "Test"; // violation "'.' is followed by whitespace."
    }
}
