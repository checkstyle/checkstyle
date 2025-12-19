/*
UnnecessaryParentheses
tokens = (default)EXPR, IDENT, NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG, \
         STRING_LITERAL, LITERAL_NULL, LITERAL_FALSE, LITERAL_TRUE, ASSIGN, \
         BAND_ASSIGN, BOR_ASSIGN, BSR_ASSIGN, BXOR_ASSIGN, DIV_ASSIGN, \
         MINUS_ASSIGN, MOD_ASSIGN, PLUS_ASSIGN, SL_ASSIGN, SR_ASSIGN, STAR_ASSIGN, \
         LAMBDA, TEXT_BLOCK_LITERAL_BEGIN, LAND, LITERAL_INSTANCEOF, GT, LT, GE, \
         LE, EQUAL, NOT_EQUAL, UNARY_MINUS, UNARY_PLUS, INC, DEC, LNOT, BNOT, \
         POST_INC, POST_DEC, INDEX_OP, DOT, LOR


*/

// Java21
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

public class InputUnnecessaryParenthesesWhenExpressions {

    void test(Object o) {

        switch (o) {
            case Integer i when (i > 0) -> {}  // violation 'Unnecessary paren.* around expression'

            case String s when (!s.isEmpty()) -> {}
            // violation above 'Unnecessary paren.* around expression'

            case Point(boolean x, boolean y, boolean z)
                    when (x && y) || z -> System.out.println();
            // violation above 'Unnecessary paren.* around expression'

            case Point p when ((p.x && p.y) || p.z) -> System.out.println();
            // 2 violations above:
            //                   'Unnecessary paren.* around expression'
            //                   'Unnecessary paren.* around expression'

            // until https://github.com/checkstyle/checkstyle/issues/15317
            case Point2(int x, int y) when ((x >= 0 && y >= 0)) -> {}
            // violation above 'Unnecessary paren.* around expression'

            case Point2(int x, int y) when (x >= 5 && y >= 5) -> {}
            // violation above 'Unnecessary paren.* around expression'

            default -> {}
        }

        switch (o) {
            case Integer i when i > 0 -> {}
            case String s when !s.isEmpty() -> {}
            case Point(boolean x, boolean y, boolean z) when x && y || z -> System.out.println();
            case Point p when p.x && p.y || p.z -> System.out.println();
            case Point2(int x, int y) when x >= 0 && y >= 0 -> {}
            case Point2(int x, int y) when x >= 5 && y >= 5 -> {}
            default -> {}
        }
    }

    record Point(boolean x, boolean y, boolean z) { }
    record Point2(int x, int y) { }
}
