/*
OperatorWrap
option = \tEOL
tokens = QUESTION, COLON, EQUAL, NOT_EQUAL, DIV, PLUS, MINUS, STAR, MOD, \
         SR, BSR, GE, GT, SL, LE, LT, BXOR, BOR, LOR, BAND, LAND, TYPE_EXTENSION_AND, \
         LITERAL_INSTANCEOF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

public class InputOperatorWrapWithTrimOptionProperty {

        int x = (1 < 2) ?
                false ? "".substring(0,
                        0).length()
                    : false // violation '':' should be on the previous line.'
                    ? 2 : 3 : 4; // violation ''?' should be on the previous line.'

}
