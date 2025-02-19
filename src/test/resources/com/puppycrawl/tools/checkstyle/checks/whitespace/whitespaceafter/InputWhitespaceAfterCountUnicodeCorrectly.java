/*
WhitespaceAfter
tokens = (default)COMMA, SEMI, TYPECAST, LITERAL_IF, LITERAL_ELSE, LITERAL_WHILE, \
         LITERAL_DO, LITERAL_FOR, DO_WHILE


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterCountUnicodeCorrectly {
    String a = "  ";
    String b = "💩💩";
    String c = "💩💩";// message // violation '';' is not followed by whitespace'
    String d = "💩💩";
}
