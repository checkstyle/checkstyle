/*
OperatorWrap
option = eol
tokens = (default)QUESTION, COLON, EQUAL, NOT_EQUAL, DIV, PLUS, MINUS, STAR, MOD, \
         SR, BSR, GE, GT, SL, LE, LT, BXOR, BOR, LOR, BAND, LAND, TYPE_EXTENSION_AND, \
         LITERAL_INSTANCEOF
shouldWrapAtHigherLevel = true


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

public class InputOperatorWrapHigherLevelEol {

    void m() {
        int a = 1, b = 2, c = 3, d = 4;

        // 1. EOL mode: inner op (*) wraps (is at EOL) when outer op (+) on same line: report on +
        int x1 = a + b *
                c; // violation above ''\+' should be on the previous line.'

        // 2. EOL mode: inner op (+) wraps (is at EOL) when outer op (/) on same line: report on /
        int x2 = a / b + // valid
                c;

        // 3. EOL mode: parent op (+) already wrapped (is at EOL)
        int x3 = a +
                b * c; // valid: parent wraps at higher level

        // 4. EOL mode: no wrapping at all
        int x4 = a * b + c;

        // 5. EOL mode: parent op (+) and inner op (*) both wrap
        int x5 = a +
                b *
                c;
    }
}
