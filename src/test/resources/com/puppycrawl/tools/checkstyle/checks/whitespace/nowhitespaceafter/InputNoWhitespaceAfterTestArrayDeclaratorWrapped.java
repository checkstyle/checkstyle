/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = ARRAY_DECLARATOR, INDEX_OP


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

public class InputNoWhitespaceAfterTestArrayDeclaratorWrapped {
    int[] field;

    void method(int[] argument) {
        int[] a
            [];
        int[] b
            [] = argument;
        int c = field
            [0];
    }
}
