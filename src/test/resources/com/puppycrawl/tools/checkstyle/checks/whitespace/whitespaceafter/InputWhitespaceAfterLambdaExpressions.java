/*
WhitespaceAfter
tokens = LAMBDA

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

import java.util.function.Function;

public class InputWhitespaceAfterLambdaExpressions {

    static Runnable r1 = () -> {
        String.valueOf("Hello world one!");
    };

    static Runnable r2 = () ->String.valueOf(""); // violation ''->' is not followed by whitespace'

    Runnable r3 = () ->{String.valueOf("");}; // violation ''->' is not followed by whitespace'

    static Runnable r4 = () ->
    {
        String.valueOf("Hello world one!");
    };

    public void foo() {
        Function<Object, String> function =
                (o) ->o.toString(); // violation ''->' is not followed by whitespace'
    }
}
