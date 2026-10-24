/*
UnnecessaryParentheses
tokens = (default)EXPR, IDENT, NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG, \
         STRING_LITERAL, LITERAL_NULL, LITERAL_FALSE, LITERAL_TRUE, ASSIGN, \
         BAND_ASSIGN, BOR_ASSIGN, BSR_ASSIGN, BXOR_ASSIGN, DIV_ASSIGN, \
         MINUS_ASSIGN, MOD_ASSIGN, PLUS_ASSIGN, SL_ASSIGN, SR_ASSIGN, STAR_ASSIGN, \
         LAMBDA, TEXT_BLOCK_LITERAL_BEGIN, LAND, LITERAL_INSTANCEOF, GT, LT, GE, \
         LE, EQUAL, NOT_EQUAL, UNARY_MINUS, UNARY_PLUS, INC, DEC, LNOT, BNOT, \
         POST_INC, POST_DEC, INDEX_OP, DOT, LOR, TYPECAST


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;
public class InputUnnecessaryParenthesesCasts {
    public void f5() {
        int x, y;
        x = 0;
        y = 0;
        if (x == y) {
            print(x);
        }
        if ((x > y)) { // violation 'Unnecessary parentheses around expression'
            print(y);
        }

        while ((x < 10)) { // violation 'Unnecessary parentheses around expression'
            print(x++);
        }

        do {
            print((y+=100)); // violation 'Unnecessary parentheses around expression'
        } while (y < (4000)); // violation 'Unnecessary parentheses around literal '4000''
    }

    private void f6(TypeA a) {
        TypeB b = (TypeB) a;
        TypeC c = ((TypeC) a); // violation 'Unnecessary parentheses around assignment right.*side'
        int r = 12345;
        r <<= (3);
        // 2 violations above:
        //  'Unnecessary parentheses around assignment right-hand side'
        //  'Unnecessary parentheses around literal '3''
        GenT<String> d = ((GenT<String>) a); // violation 'paren.* around assignment right.*side'
    }

    private void print(int arg)
    {
        System.identityHashCode("arg = " + arg);
    }

    private int f7() {
        String f;

        f = ("12345678901234567890123");
        // 2 violations above:
        //  'Unnecessary parentheses around assignment right-hand side'
        //  'Unnecessary parentheses around string "12345678901234567890123"'

        return 0;
    }

    static class GenT<T> {}
    static class TypeA extends GenT<String> {}
    static class TypeB extends TypeA {}
    static class TypeC extends TypeA {}
}
