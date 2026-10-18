/*
LambdaBodyLength
max = 2


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.lambdabodylength;

import java.util.function.Supplier;

public class InputLambdaBodyLengthSwitchRules {

    // violation below 'Lambda body length is 4 lines (max allowed is 2).'
    Supplier<String> field = () -> {
        String s = "a";
        return s;
    };

    Supplier<String> rule(int x) {
        return switch (x) {
            // violation below 'Lambda body length is 4 lines (max allowed is 2).'
            case 1 -> () -> {
                String s = "a";
                return s;
            };
            case 2 -> () -> "b";
            // violation below 'Lambda body length is 3 lines (max allowed is 2).'
            case 3 -> () -> "c"
                + "d"
                + "e";
            default -> () -> "default";
        };
    }

    void switchStatement(int x) {
        switch (x) {
            case 1 -> {
                System.out.println(1);
                System.out.println(2);
                System.out.println(3);
            }
            default -> System.out.println("default");
        }
    }
}
