/*
UnnecessaryParentheses
tokens = EXPR, IDENT, NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG, \
         STRING_LITERAL, LITERAL_NULL, LITERAL_FALSE, LITERAL_TRUE, ASSIGN, \
         BAND_ASSIGN, BOR_ASSIGN, BSR_ASSIGN, BXOR_ASSIGN, DIV_ASSIGN, \
         MINUS_ASSIGN, MOD_ASSIGN, PLUS_ASSIGN, SL_ASSIGN, SR_ASSIGN, STAR_ASSIGN, \
         LAMBDA, TEXT_BLOCK_LITERAL_BEGIN, LAND, LITERAL_INSTANCEOF, GT, LT, GE, \
         LE, EQUAL, NOT_EQUAL, UNARY_MINUS, UNARY_PLUS, INC, DEC, LNOT, BNOT, \
         POST_INC, POST_DEC, INDEX_OP, LITERAL_NEW


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

public class InputUnnecessaryParenthesesArrayNewMutation {
    private static int[] arr = {1, 2, 3};

    public void testIndexOpSurrounded() {
        int a = (arr[0]); // violation 'Unnecessary parentheses around expression.'
    }

    public void testLiteralNewSurrounded() {
        Object obj = (new Object()); // violation 'Unnecessary parentheses around expression.'
    }

    public void testIndexOpNotSurrounded() {
        int b = arr[0];
    }

    public void testLiteralNewNotSurrounded() {
        Object obj2 = new Object();
    }

    public void testNestedIndexOpSurrounded() {
        int[][] matrix = {{1, 2}, {3, 4}};
        int c = (matrix[0])[1]; // violation 'Unnecessary parentheses around expression.'
    }

    public void testNewWithMethodCall() {
        // violation below 'Unnecessary parentheses around expression.'
        String s = (new String("test")).toUpperCase();
    }

    public void testIndexOpWithMethodCall() {
        String[] strings = {"hello"};
        int len = (strings[0]).length(); // violation 'Unnecessary parentheses around expression.'
    }

    public void testMultipleIndexOpSurrounded() {
        int d = (arr[0]) + (arr[1]); // 2 violations
    }

    public void testMixedSurrounded() {
        String result = (arr[0]) + (new String()); // 2 violations
    }
}
