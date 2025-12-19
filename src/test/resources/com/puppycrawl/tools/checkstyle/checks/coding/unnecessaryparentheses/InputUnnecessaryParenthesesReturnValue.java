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

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

import java.util.function.Function;

public class InputUnnecessaryParenthesesReturnValue {
    int foo() {
        Function<Integer, Integer> addOne
                = x -> { return (x + 1); }; // violation 'Unnecessary paren.* around return'
        return (1 + 1); // violation 'Unnecessary paren.* around return'
    }
    boolean compare() {
        return (9 <= 3); // violation 'Unnecessary paren.* around return'
    }
    boolean bar() {
        return (true && 7 > 3); // violation 'Unnecessary paren.* around return'
    }
    boolean bigger() {
        return (Integer.parseInt("5") > 7 || // violation 'Unnecessary paren.* around return'
                Integer.parseInt("2") > 3 ||
                "null" != null);
    }
    int ternary() {
        return (true ? 0 : 1); // violation 'Unnecessary paren.* around return'
    }
    boolean ok() {
        return 5 > 7 ||
                6 < 4;
    }
    int okternary() {
        return true ? 0 : 1;
    }
}
