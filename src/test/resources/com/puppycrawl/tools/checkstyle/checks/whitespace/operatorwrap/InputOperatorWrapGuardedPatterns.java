/*
OperatorWrap
option = (default)nl
tokens = (default)QUESTION, COLON, EQUAL, NOT_EQUAL, DIV, PLUS, MINUS, STAR, MOD, \
         SR, BSR, GE, GT, SL, LE, LT, BXOR, BOR, LOR, BAND, LAND, TYPE_EXTENSION_AND, \
         LITERAL_INSTANCEOF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

public class InputOperatorWrapGuardedPatterns {
    String typeGuardAfterParenthesizedTrueIfStatement1(Object o) {
        if (o != null
                && o instanceof Integer i
                && i == 0) {
            return "true";
        } else if (o != null
                && o instanceof Integer i
                && i == 2
                && (o = i) != null) {
            return "second";
        } else {
            return "any";
        }
    }

    String typeGuardAfterParenthesizedTrueIfStatement2(Object o) {
        if (o != null && // violation ''&&' should be on a new line.'
                o instanceof Integer i && // violation ''&&' should be on a new line.'
                        i == 0) {
            return "true";
        } else if (o != null && // violation ''&&' should be on a new line.'
                o instanceof Integer i && // violation ''&&' should be on a new line.'
                        i == 2 && // violation ''&&' should be on a new line.'
                (o = i) != null) {
            return "second";
        } else {
            return "any";
        }
    }
}
