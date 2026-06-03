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

    // violation 2 lines below ''->' should be on the previous line.'
    Runnable r1 = ()
        -> {};

    Runnable r2 = () -> {};

    Supplier<String> s1 = () ->
            "hello";

    // violation 2 lines below ''->' should be on the previous line.'
    Function<String, String> f1 = x
        -> x.toUpperCase();

    Function<String, String> f2 = x -> x.toUpperCase();

    void testStreamLambda() {
        List<String> list = Arrays.asList("a", "b");
        // violation 3 lines below ''->' should be on the previous line.'
        list.stream()
            .map(x
                -> x.toString());

        list.stream()
            .map(x -> x.toString());
    }

    void testLambdaWithBlock() {
        // violation 2 lines below ''->' should be on the previous line.'
        Runnable r = ()
            -> {
              System.out.println("hello");
            };

        Runnable r2 = () -> {
            System.out.println("hello");
        };
    }

    String testSwitchRule(int day) {
        return switch (day) {
            // violation 2 lines below ''->' should be on the previous line.'
            case 1
                -> "Monday";

            case 2 ->
                "Tuesday";
            case 3 -> "Wednesday";
            case 4 -> {
                yield "Thursday";
            }
            // violation 2 lines below ''->' should be on the previous line.'
            case 5
                -> {
                yield "Friday";
            }
            default -> throw new IllegalArgumentException();
        };
    }

    int testSwitchMultipleLabels(int val) {
        return switch (val) {
            // violation 2 lines below ''->' should be on the previous line.'
            case 1, 2, 3
                -> val * 2;
            case 4, 5 -> val * 3;
            default -> 0;
        };
    }
}
