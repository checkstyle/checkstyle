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

public class InputUnnecessaryParenthesesCheckPatterns {
    void method() {
        Object o = "";

        boolean result = (o instanceof String a) ?
                (o instanceof String x) : (!(o instanceof String y));

        boolean f =  (o instanceof String x);
        // violation above, 'Unnecessary parentheses around assignment right-hand side.'

        boolean b = (!(o instanceof Rectangle(_, _)));
        // violation above, 'Unnecessary parentheses around assignment right-hand side.'
        boolean c = !(o instanceof Rectangle(_, _));

        if ((!(o instanceof Rectangle(_, _)))) {}
        // violation above, 'Unnecessary parentheses around expression.'
        if (!(o instanceof Rectangle( _, _))) {}

    }

    record Rectangle(int x, int y) { }
}
