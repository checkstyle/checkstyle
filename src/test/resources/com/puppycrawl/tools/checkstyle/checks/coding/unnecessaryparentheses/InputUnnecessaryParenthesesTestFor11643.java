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

public class InputUnnecessaryParenthesesTestFor11643 {
    public static boolean is(Class<?> clazz) {
        return true
            && (test(clazz)); // violation 'Unnecessary parentheses around identifier 'test'.'
    }

    public int square(int a, int b){
    int square = (a * b); // violation 'Unnecessary parentheses around assignment right-hand side'
    return (square);      // violation 'Unnecessary parentheses around identifier 'square''
    }

    public static boolean test(Class<?> clazz) {
        return true;
    }

    public static boolean newTest(Class<?> clazz) {
        return true
            && test((clazz)); // violation 'Unnecessary parentheses around identifier 'clazz''
    }

    public static boolean newTest2(Class<?> clazz) {
        return true
            && ((test((clazz)))); // 2 violations
    }

    public static boolean newTest3(Class<?> clazz) {
        return true
            && test((clazz)); // violation 'Unnecessary parentheses around identifier 'clazz''
    }

    public String getMarkerNumber() {
        String markerNumber = "someText";
        return markerNumber;
    }
}
