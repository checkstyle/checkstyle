/*
UnnecessaryParentheses
tokens = (default)EXPR, IDENT, NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG, \
         STRING_LITERAL, LITERAL_NULL, LITERAL_FALSE, LITERAL_TRUE, ASSIGN, \
         BAND_ASSIGN, BOR_ASSIGN, BSR_ASSIGN, BXOR_ASSIGN, DIV_ASSIGN, \
         MINUS_ASSIGN, MOD_ASSIGN, PLUS_ASSIGN, SL_ASSIGN, SR_ASSIGN, STAR_ASSIGN, \
         LAMBDA, TEXT_BLOCK_LITERAL_BEGIN, LAND, LITERAL_INSTANCEOF, GT, LT, GE, \
         LE, EQUAL, NOT_EQUAL, UNARY_MINUS, UNARY_PLUS, INC, DEC, LNOT, BNOT, \
         POST_INC, POST_DEC, INDEX_OP, DOT, LOR, TYPECAST


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

public class InputUnnecessaryParenthesesNestedRecordPattern {

    int a;
    int b;

    boolean method1(Object obj) {
        return obj instanceof Point();
    }

    int method2(Object obj) {
        return switch (obj) {
            case Point() -> 1;
            case Pair(int x, int y) -> x + ((a + b));
            // violation above 'Unnecessary parentheses around expression'
            default -> 0;
        };
    }

    int method3(Object obj) {
        return switch (obj) {
            case Pair(int x, int y) -> x + (a + b);
            default -> a + (b * a);
        };
    }
}

record Point() {
}

record Pair(int x, int y) {
}
