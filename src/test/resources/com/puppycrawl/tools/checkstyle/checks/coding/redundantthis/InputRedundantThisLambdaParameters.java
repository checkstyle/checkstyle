package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/*
* Config = default
*/
public class InputRedundantThisLambdaParameters {
    private String s1 = "foo1";
    int z=0;

    void foo1() {
        final List<String> strings = new ArrayList<>();
        strings.add("foo1");
        strings.stream().filter( (s1) -> {
            this.s1 = s1; // no violation
            return s1.contains("f");
        }).count();
    }

    class FirstLevel {
        int x;

        void methodOneInFirstLevel(int y) {
            Consumer<Integer> myConsumer = (x) ->
            {
                new String("FirstLevel.this.x = " + this.x); // no violation
                z= x + z++;
            };
            myConsumer.accept(x);
        }
    }
}
