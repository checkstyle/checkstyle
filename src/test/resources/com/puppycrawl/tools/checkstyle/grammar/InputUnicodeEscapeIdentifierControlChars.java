/*
com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAfterCheck
tokens = (default)COMMA, SEMI, TYPECAST, LITERAL_IF, LITERAL_ELSE, LITERAL_WHILE, \
         LITERAL_DO, LITERAL_FOR, LITERAL_FINALLY, LITERAL_RETURN, LITERAL_YIELD, \
         LITERAL_CATCH, DO_WHILE, ELLIPSIS, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, \
         LITERAL_TRY, LITERAL_CASE, LAMBDA, LITERAL_WHEN

*/

package com.puppycrawl.tools.checkstyle.grammar;

public class InputUnicodeEscapeIdentifierControlChars {

    void nul(int a\u0000b,int c) { } // violation "is not followed by whitespace"
    void bell(int a\u0007b,int c) { } // violation "is not followed by whitespace"
    void trailing(int zero\u0000zero\u0000,int c) { } // violation "is not followed by whitespace"
}
