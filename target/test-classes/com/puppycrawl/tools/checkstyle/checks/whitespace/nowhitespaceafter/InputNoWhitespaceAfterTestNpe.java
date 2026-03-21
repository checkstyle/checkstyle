/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = (default)ARRAY_INIT, AT, INC, DEC, UNARY_MINUS, UNARY_PLUS, BNOT, LNOT, \
         DOT, ARRAY_DECLARATOR, INDEX_OP


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

public class InputNoWhitespaceAfterTestNpe
{
    private int[] getSome() {
        return new int[4];
    }
}
