/*
OneStatementPerLine
treatTryResourcesAsStatement = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

import java.util.function.Function;

public class InputOneStatementPerLineMethodCalls {
    public void test() {
        combine(
                wrap(y -> { return y + 1; }), x -> { return x + 1; }
        );

        ; combine(
                wrap(y -> { return y + 1; }),
                x -> { return x + 1; }
        );
    }

    private int wrap(Function<Integer, Integer> f) {
        return f.apply(10);
    }

    private void combine(int val, Function<Integer, Integer> f) {
        System.out.println(val + f.apply(5));
    }

    static class Base {
        Base() {
        }

        Base(int x) {
            this();
        }
    }

    static class Inner extends Base {
        Inner() {
            super();
        }

        Inner(int x) {
        }

        Inner(int x, int y) {
            this(x); System.out.println(x + y);
            // violation above 'Only one statement per line allowed.'
        }

        Inner(int x, int y, int z) {
            super(x + y + z); int a = 1;
            // violation above 'Only one statement per line allowed.'
        }
    }
}
