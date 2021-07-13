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
public class InputUnnecessaryParenthesesOperatorsAndCasts {
    int f1() {
        int x = 0;
        for (int i = (0+1); ((i) < (6+6)); i += (1+0)) { // violation
            x += (i + 100); // violation
            (x) += (i + 100/**comment test*/); // violation
            x = (x + i + 100); // violation
            (x) = (x + i + 100); // violation
        }

        for (int i = (0+1); (i) < ((6+6)); i += (1+0)) { // violation
            System.identityHashCode("hi");
        }

        return (0); // violation
    }

    private int f2(int arg1, double arg2) {
        int x, a, b, c, d;
        String e, f;

        x = 0;
        a = 0;
        b = 0;
        c = (a + b); // violation
        d = c - 1;

        int i = (int) arg2;
        i = ((int) arg2); // violation

        x += (i + 100 + arg1); // violation
        a = (a + b) * (c + d);
        b = ((((a + b) * (c + d)))); // violation
        c = (((a) <= b)) ? 0 : 1; // violation
        d = (a) + (b) * (600) / (int) (12.5f) + (int) (arg2); // violation
        e = ("this") + ("that") + ("is" + "other"); // violation
        f = ("this is a really, really long string that should be truncated."); // violation

        return (x + a + b + d); // violation
    }

    private boolean f3() {
        int x = f2((1), (13.5)); // violation
        boolean b = (true); // violation
        return (b); // violation
    }

    public static int f4(int z, int a) {
        int r = (z * a); // violation
        r = (a > z) ? a : z;
        r = ((a > z) ? a : z); // violation
        r = (a > z) ? a : (z + z);
        return (r * r - 1); // violation
    }

    public void f5() {
        int x, y;
        x = 0;
        y = 0;
        if (x == y) {
            print(x);
        }
        if ((x > y)) { // violation
            print(y);
        }

        while ((x < 10)) { // violation
            print(x++);
        }

        do {
            print((y+=100)); // violation
        } while (y < (4000)); // violation
    }

    private void f6(TypeA a) {
        TypeB b = (TypeB) a;
        TypeC c = ((TypeC) a); // violation
        int r = 12345;
        r <<= (3); // violation
        TypeParameterized<String> d = ((TypeParameterized<String>) a); // violation
    }

    private void print(int arg)
    {
        System.identityHashCode("arg = " + arg);
    }

    private int f7() {
        String f;

        f = ("12345678901234567890123"); // violation

        return 0;
    }

    static class TypeParameterized<T> {}
    static class TypeA extends TypeParameterized<String> {}
    static class TypeB extends TypeA {}
    static class TypeC extends TypeA {}
}
