/*
LambdaBodyLength
max = (default)10


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.lambdabodylength;

import java.util.function.Function;
import java.util.function.Supplier;

public class InputLambdaBodyLengthSwitchRuleLambda {

    Supplier<String> longBlockLambda(int x) {
        return switch (x) {
            case 1 -> () -> { // violation 'Lambda body length is 12 lines (max allowed is 10).'
                System.out.println(1);
                System.out.println(2);
                System.out.println(3);
                System.out.println(4);
                System.out.println(5);
                System.out.println(6);
                System.out.println(7);
                System.out.println(8);
                System.out.println(9);
                return "a";
            };
            default -> () -> "b";
        };
    }

    Function<String, String> shortBlockLambda(int x) {
        return switch (x) {
            case 1 -> (String s) -> {
                String result = s;
                return result;
            };
            default -> s -> s;
        };
    }

    Supplier<String> longExpressionLambda(int x) {
        return switch (x) {
            case 1 -> () -> "a" // violation 'Lambda body length is 12 lines (max allowed is 10).'
                    .concat("b")
                    .concat("c")
                    .concat("d")
                    .concat("e")
                    .concat("f")
                    .concat("g")
                    .concat("h")
                    .concat("i")
                    .concat("j")
                    .concat("k")
                    .concat("l");
            default -> () -> "z";
        };
    }
}
