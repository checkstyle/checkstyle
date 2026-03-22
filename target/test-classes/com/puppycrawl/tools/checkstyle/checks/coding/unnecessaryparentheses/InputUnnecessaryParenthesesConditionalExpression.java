/*
UnnecessaryParentheses
tokens = EXPR, IDENT, NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG, \
         STRING_LITERAL, LITERAL_NULL, LITERAL_FALSE, LITERAL_TRUE, ASSIGN, \
         BAND_ASSIGN, BOR_ASSIGN, BSR_ASSIGN, BXOR_ASSIGN, DIV_ASSIGN, \
         MINUS_ASSIGN, MOD_ASSIGN, PLUS_ASSIGN, SL_ASSIGN, SR_ASSIGN, STAR_ASSIGN, \
         LAMBDA, TEXT_BLOCK_LITERAL_BEGIN, LAND, LITERAL_INSTANCEOF, GT, LT, GE, \
         LE, EQUAL, NOT_EQUAL, UNARY_MINUS, UNARY_PLUS, INC, DEC, LNOT, BNOT, \
         POST_INC, POST_DEC, QUESTION


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

public class InputUnnecessaryParenthesesConditionalExpression {
    void method() {
        int w = 5;
        int x = (w == 3) ? (3) : (4);
        // 3 violations above
        //  'Unnecessary parentheses around expression'
        //  'Unnecessary parentheses around literal '3''.
        //  'Unnecessary parentheses around literal '4''.
        int y = !(w>x) ? 3 : 4;
        int z1 = (!(y >= w)) ? 5 : 6; // violation 'Unnecessary parentheses around expression'

        int z2 = 5 > 3 ? 2 : 1;
        int z3 = (z2 > 0) ? 5 : (z2 < -10) ? 7 : 3;
        // 2 violations above
        //  'Unnecessary parentheses around expression'
        //  'Unnecessary parentheses around expression'

        Object o = "";
        // violation below 'Unnecessary parentheses around expression'
        boolean result = (o instanceof String) ?
                (o instanceof String) : (!(o instanceof String));
        // 2 violations above
        //  'Unnecessary parentheses around expression'
        //  'Unnecessary parentheses around expression'

    }
}
