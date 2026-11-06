/*
ParenPad
option = (default)nospace
tokens = (default)ANNOTATION, ANNOTATION_FIELD_DEF, CTOR_CALL, CTOR_DEF, DOT, ENUM_CONSTANT_DEF, \
         EXPR, LITERAL_CATCH, LITERAL_DO, LITERAL_FOR, LITERAL_IF, LITERAL_NEW, LITERAL_SWITCH, \
         LITERAL_SYNCHRONIZED, LITERAL_WHILE, METHOD_CALL, METHOD_DEF, QUESTION, \
         RESOURCE_SPECIFICATION, SUPER_CTOR_CALL, LAMBDA, RECORD_DEF, RECORD_PATTERN_DEF, ASSIGN, \
         PLUS_ASSIGN, MINUS_ASSIGN, STAR_ASSIGN, DIV_ASSIGN, MOD_ASSIGN, SR_ASSIGN, BSR_ASSIGN, \
         SL_ASSIGN, BAND_ASSIGN, BXOR_ASSIGN, BOR_ASSIGN, LAND, LOR, BAND, BOR, BXOR, EQUAL, \
         NOT_EQUAL, LT, LE, GT, GE, SL, SR, BSR, PLUS, MINUS, STAR, DIV, MOD, UNARY_PLUS, \
         UNARY_MINUS, LNOT, BNOT, INC, DEC, POST_INC, POST_DEC, INDEX_OP, METHOD_REF, \
         LITERAL_INSTANCEOF, TYPECAST


*/

// Java21
package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

import java.util.HashMap;

import org.w3c.dom.Node;

public class InputParenPadCheckWhenExpression {
    void test(Object o) {
        if (o instanceof String a && ( a.length() == 9)) { }
        // violation above ''(' is followed by whitespace'

        switch (o) {
            case Integer i when ( i == 2) -> {}
            // violation above ''(' is followed by whitespace'
            case Integer i when (i == 2 ) -> {}
            // violation above '')' is preceded with whitespace'
            case Point(int x, int y) when ( x == 1 ) -> {}
            // 2 violations above:
            //                   ''(' is followed by whitespace'
            //                   '')' is preceded with whitespace'
            default -> {}
        }
    }

    record Point(int x, int y) { }
}
