/*
UnnecessaryParentheses
tokens = EXPR, IDENT, NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG, \
         STRING_LITERAL, LITERAL_NULL, LITERAL_FALSE, LITERAL_TRUE, ASSIGN, \
         BAND_ASSIGN, BOR_ASSIGN, BSR_ASSIGN, BXOR_ASSIGN, DIV_ASSIGN, \
         MINUS_ASSIGN, MOD_ASSIGN, PLUS_ASSIGN, SL_ASSIGN, SR_ASSIGN, STAR_ASSIGN, \
         LAMBDA, TEXT_BLOCK_LITERAL_BEGIN, LAND, LITERAL_INSTANCEOF, GT, LT, GE, \
         LE, EQUAL, NOT_EQUAL, UNARY_MINUS, UNARY_PLUS, INC, DEC, LNOT, BNOT, \
         POST_INC, POST_DEC, INDEX_OP, DOT, LITERAL_NEW, LOR


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

public class InputUnnecessaryParenthesesFieldMethodAccess {

    private static int foo;
    private static Main INSTANCE;

    private static class Main {
        private String name;

        private boolean check() {
            return false;
        }
    }

    public static void main(String[] args) {
        foo = (args[0]).length();
        // violation above 'Unnecessary parentheses around expression.'
        foo = (String.valueOf(foo)).length();
        // violation above 'Unnecessary parentheses around expression.'
        foo = (INSTANCE.name).length();
        // violation above 'Unnecessary parentheses around expression.'
        foo = (new StringBuilder()).length();
        // violation above 'Unnecessary parentheses around expression.'
        boolean b = INSTANCE.check() ? (INSTANCE.check()) : true;
        // violation above 'Unnecessary parentheses around expression.'
        foo = args[0].length();
        foo = String.valueOf(foo).length();
        foo = INSTANCE.name.length();
        foo = new StringBuilder().length();
        foo = (INSTANCE.check()) ? 1 : 2;
        System.out.println(foo);
    }

}
