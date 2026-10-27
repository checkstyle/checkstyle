/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = (default)ARRAY_INIT, AT, INC, DEC, UNARY_MINUS, UNARY_PLUS, BNOT, LNOT, \
         DOT, ARRAY_DECLARATOR, INDEX_OP


*/

package org.checkstyle.suppressionxpathfilter.whitespace.nowhitespaceafter;

public class InputXpathNoWhitespaceAfter {
    int bad = - 1; // violation "'-' is followed by whitespace."
    int good = -1;
}
