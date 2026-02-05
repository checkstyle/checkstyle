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

public class InputUnnecessaryParenthesesArrayFieldMethodNew {
    private static int foo;
    private static InputUnnecessaryParenthesesArrayFieldMethodNew INSTANCE;
    private String name;

    public static void main(String[] args) {
        foo = (args[0]).length(); // violation 'Unnecessary parentheses around expression.'

        foo = (new String()).length(); // violation 'Unnecessary parentheses around expression.'

        // violation 2 lines below 'Unnecessary parentheses around expression.'
        // violation below 'Unnecessary parentheses around expression.'
        foo = (args[0]).length() + (args[1]).length();

        foo = args[0].length();
        foo = new String().length();

        int result = (1 + 2) * 3;
    }

    public int getValue() {
        return 42;
    }
}
