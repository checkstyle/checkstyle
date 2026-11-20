/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldPattern = (default)serialVersionUID

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

import java.util.function.Function;
import java.util.function.Supplier;

public class InputUnusedPrivateFieldSwitchLambda {

    public int switchBeforeField(int value) {
        return switch (value) {
            case 1 -> 10;
            default -> 0;
        };
    }

    private int early; // violation 'Unused private field'

    private int late; // violation 'Unused private field'

    public int switchAfterField(int value) {
        return switch (value) {
            case 1 -> {
                yield 100;
            }
            default -> {
                yield 200;
            }
        };
    }

    public void switchStatement(int value) {
        switch (value) {
            case 1 -> System.out.println("one");
            default -> System.out.println("other");
        }
    }

    private int text; // violation 'Unused private field'

    public int patternLabel(Object obj) {
        return switch (obj) {
            case String str -> str.length();
            case Integer num -> num;
            default -> 0;
        };
    }

    private int n; // violation 'Unused private field'

    public int lambdaInSwitchCase(int value) {
        return switch (value) {
            case 1 -> {
                Function<Integer, Integer> fn = n -> n + 1;
                yield fn.apply(1);
            }
            default -> 0;
        };
    }

    private int v; // violation 'Unused private field'

    public Function<Integer, Supplier<Integer>> nestedLambda = v -> () -> v;

    private int selector; // ok, private field is used

    public int useSelector() {
        return switch (selector) {
            case 1 -> 5;
            default -> 6;
        };
    }
}
