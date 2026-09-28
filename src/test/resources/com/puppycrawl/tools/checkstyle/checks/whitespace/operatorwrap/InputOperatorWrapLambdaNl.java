/*
OperatorWrap
option = NL
tokens = LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class InputOperatorWrapLambdaNl {

    void lambdaWithBlock() {
        // ok, the wrap is after '{', not at the arrow
        Runnable ok1 = () -> {
            int a = 1;
        };

        Runnable ok2 = ()
                -> {
            int a = 1;
        };

        Runnable bad1 = () -> // violation ''->' should be on a new line.'
                {
                    int a = 1;
                };

        Supplier<String> bad2 = () -> // violation ''->' should be on a new line.'
                "hello";

        Function<String, String> bad3 = x -> // violation ''->' should be on a new line.'
                x.trim();

        Supplier<String> ok3 = ()
                -> "hello";
    }

    void lambdaAsArgument(List<String> list) {
        // ok, the wrap is inside the block, not at the arrow
        list.forEach(item -> {
            String s = item;
        });
    }

    String switchRule(int day) {
        return switch (day) {
            // ok, the wrap is after '{', not at the arrow
            case 1 -> {
                yield "one";
            }
            case 2 -> // violation ''->' should be on a new line.'
                "two";
            default -> "other";
        };
    }
}
