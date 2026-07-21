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
public class InputUnnecessaryParenthesesOperatorsAndCasts {
    int f1() {
        int x = 0;
        for (int i = (0+1); ((i) < (6+6)); i += (1+0)) { // 4 violations
            x += (i + 100); // violation 'Unnecessary parentheses around assignment right-hand side'
            (x) += (i + 100/**comment test*/); // 2 violations
            x = (x + i + 100); // violation 'Unnecessary parentheses around assignment right.*side'
            (x) = (x + i + 100); // 2 violations
        }

        for (int i = (0+1); (i) < ((6+6)); i += (1+0)) { // 3 violations
            System.identityHashCode("hi");
        }

        return (0); // violation 'Unnecessary parentheses around literal '0''
    }

    private int f2(int arg1, double arg2) {
        int x, a, b, c, d;
        String e, f;

        x = 0;
        a = 0;
        b = 0;
        c = (a + b); // violation 'Unnecessary parentheses around assignment right-hand side'
        d = c - 1;

        int i = (int) arg2;
        i = ((int) arg2); // violation 'Unnecessary parentheses around assignment right-hand side'

        x += (i + 100 + arg1); // violation 'Unnecessary parentheses around assignment right.*side'
        a = (a + b) * (c + d);
        b = ((((a + b) * (c + d)))); // violation 'parentheses around assignment right.*side'
        c = (((a) <= b)) ? 0 : 1; // violation 'Unnecessary parentheses around identifier 'a''
        d = (a) + (b) * (600) / (int) (12.5f) + (int) (arg2); // 5 violations
        e = ("this") + ("that") + ("is" + "other"); // 2 violations
        f = ("this is a really, really long string that should be truncated."); // 2 violations

        return (x + a + b + d); // violation 'Unnecessary parentheses around return value'
    }

    private boolean f3() {
        int x = f2((1), (13.5)); // 2 violations
        boolean b = (true); // violation 'Unnecessary parentheses around literal 'true''
        return (b); // violation 'Unnecessary parentheses around identifier 'b''
    }

    public static int f4(int z, int a) {
        int r = (z * a); // violation 'Unnecessary parentheses around assignment right-hand side'
        r = (a > z) ? a : z;
        r = ((a > z) ? a : z); // violation 'Unnecessary parentheses around assignment right.*side'
        r = (a > z) ? a : (z + z);
        return (r * r - 1); // violation 'Unnecessary parentheses around return value'
    }

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
        r <<= (3); // 2 violations
        GenT<String> d = ((GenT<String>) a); // violation 'paren.* around assignment right.*side'
    }

    private void print(int arg)
    {
        System.identityHashCode("arg = " + arg);
    }

    private int f7() {
        String f;

        f = ("12345678901234567890123"); // 2 violations

        return 0;
    }

    static class GenT<T> {}
    static class TypeA extends GenT<String> {}
    static class TypeB extends TypeA {}
    static class TypeC extends TypeA {}
}
