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
        boolean c;
        boolean d;

        test(clazz);
        c = test(clazz);
        d = (test(clazz)); // 2 violations

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

    public static int getOne() {
        return 1;
    }

    public static void testOne() {
        int a = 1;
        int b, c, d, f;
        b = a + getOne();
        c=(getOne()+getOne());// violation 'Unnecessary parentheses around assignment right.*side'
        d = (a + getOne()); // violation 'Unnecessary parentheses around assignment right.*side'
        f = (1 + (getOne())); // 2 violations
    }

}
