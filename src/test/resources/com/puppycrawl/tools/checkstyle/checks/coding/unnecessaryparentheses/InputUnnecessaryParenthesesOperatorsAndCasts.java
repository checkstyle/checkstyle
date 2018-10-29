package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;
public class InputUnnecessaryParenthesesOperatorsAndCasts {
    int f1() {
        int x = 0;
        for (int i = (0+1); ((i) < (6+6)); i += (1+0)) {
            x += (i + 100);
            (x) += (i + 100/**comment test*/);
            x = (x + i + 100);
            (x) = (x + i + 100);
        }

        for (int i = (0+1); (i) < ((6+6)); i += (1+0)) {
            System.identityHashCode("hi");
        }

        return (0);
    }

    private int f2(int arg1, double arg2) {
        int x, a, b, c, d;
        String e, f;

        x = 0;
        a = 0;
        b = 0;
        c = (a + b);
        d = c - 1;

        int i = (int) arg2;
        i = ((int) arg2);

        x += (i + 100 + arg1);
        a = (a + b) * (c + d);
        b = ((((a + b) * (c + d))));
        c = (((a) <= b)) ? 0 : 1;
        d = (a) + (b) * (600) / (int) (12.5f) + (int) (arg2);
        e = ("this") + ("that") + ("is" + "other");
        f = ("this is a really, really long string that should be truncated.");

        return (x + a + b + d);
    }

    private boolean f3() {
        int x = f2((1), (13.5));
        boolean b = (true);
        return (b);
    }

    public static int f4(int z, int a) {
        int r = (z * a);
        r = (a > z) ? a : z;
        r = ((a > z) ? a : z);
        r = (a > z) ? a : (z + z);
        return (r * r - 1);
    }

    public void f5() {
        int x, y;
        x = 0;
        y = 0;
        if (x == y) {
            print(x);
        }
        if ((x > y)) {
            print(y);
        }

        while ((x < 10)) {
            print(x++);
        }

        do {
            print((y+=100));
        } while (y < (4000));
    }

    private void f6(TypeA a) {
        TypeB b = (TypeB) a;
        TypeC c = ((TypeC) a);
        int r = 12345;
        r <<= (3);
        TypeParameterized<String> d = ((TypeParameterized<String>) a);
    }

    private void print(int arg)
    {
        System.identityHashCode("arg = " + arg);
    }

    private int f7() {
        String f;

        f = ("12345678901234567890123");

        return 0;
    }

    static class TypeParameterized<T> {}
    static class TypeA extends TypeParameterized<String> {}
    static class TypeB extends TypeA {}
    static class TypeC extends TypeA {}
}
