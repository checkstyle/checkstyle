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
        // violation 2 lines above 'Only one statement per line allowed.'

        ; combine(
                wrap(y -> { return y + 1; }),
                x -> { return x + 1; }
        );
        // violation above 'Only one statement per line allowed.'
    }

    private int wrap(Function<Integer, Integer> f) {
        return f.apply(10);
    }

    private void combine(int val, Function<Integer, Integer> f) {
        System.out.println(val + f.apply(5));
    }
}
