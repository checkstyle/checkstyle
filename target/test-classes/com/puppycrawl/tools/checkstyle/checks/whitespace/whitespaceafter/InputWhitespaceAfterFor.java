/*
WhitespaceAfter
tokens = (default)COMMA, SEMI, TYPECAST, LITERAL_IF, LITERAL_ELSE, LITERAL_WHILE, \
         LITERAL_DO, LITERAL_FOR, DO_WHILE


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

class InputWhitespaceAfterFor
{
    void method1()
    {
        for (int i = 0; i < 1; i++) {
        }

        for (int i = 0; i < 1;i++) { // violation '';' is not followed by whitespace'
        }

        for (int i = 0; i < 1;i++ ) { // violation '';' is not followed by whitespace'
        }

        for (int i = 0; i < 1; i++ ) {
        }

        for (int i = 0; i < 1;) {
            i++;
        }

        for (int i = 0; i < 1; ) {
            i++;
        }

        // test eol, there is no space after second SEMI
        for (int i = 0; i < 1;
            ) {
            i++;
        }
    }

    void method2()
    {
        for ( int i = 0; i < 1; i++ ) {
        }

        for ( int i = 0; i < 1; ) {
            i++;
        }

        int i = 0;
        for ( ; i < 1; i++ ) {
        }

        for (; i < 2; i++ ) {
        }

        for (
        ;; ) {
        }
    }
}
