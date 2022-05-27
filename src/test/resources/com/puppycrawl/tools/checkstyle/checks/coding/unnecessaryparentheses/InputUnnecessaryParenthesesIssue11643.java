/*
UnnecessaryParentheses
tokens = (default)EXPR, IDENT, NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG, \
         STRING_LITERAL, LITERAL_NULL, LITERAL_FALSE, LITERAL_TRUE, ASSIGN, \
         BAND_ASSIGN, BOR_ASSIGN, BSR_ASSIGN, BXOR_ASSIGN, DIV_ASSIGN, \
         MINUS_ASSIGN, MOD_ASSIGN, PLUS_ASSIGN, SL_ASSIGN, SR_ASSIGN, STAR_ASSIGN, \
         LAMBDA, TEXT_BLOCK_LITERAL_BEGIN, LAND, LITERAL_INSTANCEOF, GT, LT, GE, \
         LE, EQUAL, NOT_EQUAL, UNARY_MINUS, UNARY_PLUS, INC, DEC, LNOT, BNOT, \
         POST_INC, POST_DEC


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;
public class InputUnnecessaryParenthesesIssue11643 {
    public static boolean is(Class<?> clazz) {

        boolean a = true;
        boolean b = false;
        if(a && (test(clazz)) ){ // violation 'Unnecessary parentheses around identifier 'test''

        };
        return true
            && (test(clazz)); // violation 'Unnecessary parentheses around identifier 'test''
    }

    public static boolean test(Class<?> clazz) {
        int a, b, c;
        a = 0;
        b = 0;
        c = (a + 1); // violation 'Unnecessary parentheses around assignment right.*side'
        return true;
    }
}
