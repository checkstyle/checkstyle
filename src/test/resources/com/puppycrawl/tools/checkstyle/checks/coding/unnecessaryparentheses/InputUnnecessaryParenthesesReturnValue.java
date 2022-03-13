/*
UnnecessaryParentheses
tokens = (default)EXPR, IDENT, NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG, \
         STRING_LITERAL, LITERAL_NULL, LITERAL_FALSE, LITERAL_TRUE, ASSIGN, \
         BAND_ASSIGN, BOR_ASSIGN, , BXOR_ASSIGN, DIV_ASSIGN, \
         MINUS_ASSIGN, MOD_ASSIGN, PLUS_ASSIGN, SL_ASSIGN, SR_ASSIGN, STAR_ASSIGN, \
         LAMBDA, TEXT_BLOCK_LITERAL_BEGIN, LAND, LITERAL_INSTANCEOF, GT, LT, GE, \
         LE, EQUAL, NOT_EQUAL, UNARY_MINUS, UNARY_PLUS, INC, DEC, LNOT, BNOT, \
         POST_INC, POST_DEC


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

import java.util.function.Function;

public class InputUnnecessaryParenthesesReturnValue {
    int foo() {
        Function<Integer, Integer> addOne
                = x -> { return (x + 1); }; // violation 'Unnecessary paren.* around return'
        return (1 + 1); // violation 'Unnecessary paren.* around return'
    }
}
