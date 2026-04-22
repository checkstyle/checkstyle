/*
OperatorWrap
option = EOL
tokens = LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class InputOperatorWrapLambda {


    Runnable r1 = () // violation below ''->' should be on the previous line.'
        -> {};

    Runnable r2 = () -> {};

    Supplier<String> s1 = () ->
            "hello";

    Function<String, String> f1 = x // violation below ''->' should be on the previous line.'
        -> x.toUpperCase();

    Function<String, String> f2 = x -> x.toUpperCase();

    void testStreamLambda() {
        List<String> list = Arrays.asList("a", "b");
        list.stream()
            .map(x
                // violation below ''->' should be on the previous line.'
                -> x.toString());

        list.stream()
            .map(x -> x.toString());
    }

    void testLambdaWithBlock() {
        Runnable r = () // violation below ''->' should be on the previous line.'
            -> {
              System.out.println("hello");
            };

        Runnable r2 = () -> {
            System.out.println("hello");
        };
    }

    String testSwitchRule(int day) {
        return switch (day) {
            case 1 // violation below ''->' should be on the previous line.'
                -> "Monday";

            case 2 ->
                "Tuesday";
            case 3 -> "Wednesday";
            case 4 -> {
                yield "Thursday";
            }
            case 5 // violation below ''->' should be on the previous line.'
                -> {
                yield "Friday";
            }
            default -> throw new IllegalArgumentException();
        };
    }

    int testSwitchMultipleLabels(int val) {
        return switch (val) {
            case 1, 2, 3 // violation below ''->' should be on the previous line.'
                -> val * 2;
            case 4, 5 -> val * 3;
            default -> 0;
        };
    }
}
