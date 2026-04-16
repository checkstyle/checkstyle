package com.openjdk.checkstyle.test.chapter3formatting.rule312lambdaexpressions;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.Assert.*;

/** Test case for Lambda Body length */
public class InputLambdaExpressionsBodyLengthValid {

    public void sumLegal() {

        // Valid block body with multiple lines of logic
        BiFunction<Integer, Integer, String> checkSum = (a, b) -> {
            int sum = a + b;
            if (sum > 100) {
                return "That is a large number: " + sum;
            } else {
                return "That is a small number: " + sum;
            }
        };
    }

    /**
     * A short, single-expression lambda should be written without braces.
     * This represents the preferred and most readable form.
     */
    public void testShortExpressionLambda() {
        Function<Integer, Integer> square = x -> x * x;
        assertEquals(9, (int) square.apply(3));
    }

    /**
     * Simple legal method.
     */
    public void doLegal() {
        Runnable r = () -> {   // line 1 (opening brace)
            int a = 1;
            int b = 2;
            int c = 3;
            int d = 4;
            int e = 5;
            int f = 6;
            int g = 7;
            int h = 8;
            int i = 9;
        };  // line 10 (closing brace) → length 10, allowed
    }
    // Lambda expression are under 10 lines count
}
