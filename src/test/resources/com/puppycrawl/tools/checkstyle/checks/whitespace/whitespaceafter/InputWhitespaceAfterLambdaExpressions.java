/*
WhitespaceAfter
tokens = LAMBDA

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

import java.util.function.Function;

public class InputWhitespaceAfterLambdaExpressions {

    static Runnable r1 = () -> { // ok
        String.valueOf("Hello world one!");
    };

    static Runnable r2 = () ->String.valueOf("Hello world two!"); // violation

    static Runnable r3 = () ->{String.valueOf("Hello world two!");}; // violation

    static Runnable r4 = () -> // ok
    {
        String.valueOf("Hello world one!");
    };

    public void foo() {
        Function<Object, String> function = (o) ->o.toString(); // violation
    }
}
