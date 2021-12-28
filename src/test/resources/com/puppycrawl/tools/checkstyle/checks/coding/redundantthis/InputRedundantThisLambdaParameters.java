/*
RedundantThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class InputRedundantThisLambdaParameters {
    private String s1 = "foo1";
    int z=0;

    void foo1() {
        final List<String> strings = new ArrayList<>();
        strings.add("foo1");
        strings.stream().filter( (s1) -> {
            this.s1 = s1; // ok
            return s1.contains("f");
        }).count();
    }

    class FirstLevel {
        int x;

        void methodOneInFirstLevel(int y) {
            Consumer<Integer> myConsumer = (x) ->
            {
                new String("FirstLevel.this.x = " + this.x); // ok
                z= x + z++;
            };
            myConsumer.accept(x);
        }
    }
}
