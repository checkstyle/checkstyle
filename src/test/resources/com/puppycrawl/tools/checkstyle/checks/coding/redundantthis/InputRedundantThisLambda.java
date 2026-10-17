/*
RedundantThis
checkMethods=(default)false
allowAdjacentToRequiredThis=false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class InputRedundantThisLambda {
    private int value;
    private String text;

    public void validShadowInLambda() {
        Consumer<Integer> c1 = value -> {
            this.value = value;
        };

        Consumer<Integer> c2 = (value) -> {
            this.value = value;
        };

        Consumer<Integer> c3 = (Integer value) -> {
            this.value = value;
        };

        BiConsumer<Integer, String> c4 = (value, text) -> {
            this.value = value;
            this.text = text;
        };

        Consumer<Integer> c5 = value -> this.value = value;
    }

    public void redundantThisInLambda() {
        Consumer<String> c6 = str -> {
            this.value = 10;
            // violation above, 'Redundant "this", field 'value' can be accessed directly.'
        };

        Consumer<String> c7 = str -> this.value = 10;
        // violation above, 'Redundant "this", field 'value' can be accessed directly.'
    }

    public void switchRule(int x) {
        switch (x) {
            case 1 -> {
                this.value = 10;
                // violation above, 'Redundant "this", field 'value' can be accessed directly.'
            }
            default -> {
            }
        }
    }
}
