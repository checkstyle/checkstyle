package com.openjdk.checkstyle.test.chapterformatting.rulelambdaexpressions;

// violation first line 'Header mismatch'
import java.util.function.Function;

public class InputLambdaExpressionsOverSingleLineLambda {
    public void foo() {
        // violation below 'preferred over single-line'
        Runnable nonValid1 = () -> { System.out.println("hello"); };
        // violation above ''{' at column 36 should have line break after.'

        Runnable valid1 = () -> System.out.println("hello");

        // violation below 'preferred over single-line'
        Function<Integer, Integer> nonValid2 = x -> { return x + 1; };
        // violation above ''{' at column 53 should have line break after.'

        Function<Integer, Integer> valid2 = x -> x + 1;

        Runnable valid3 = () -> {
            System.out.println("hello");
        };

        Runnable valid4 = () -> {
            System.out.println("hello");
            System.out.println("world");
        };
    }

}
