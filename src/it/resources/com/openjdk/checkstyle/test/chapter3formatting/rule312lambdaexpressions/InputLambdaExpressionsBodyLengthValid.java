package com.openjdk.checkstyle.test.chapter3formatting.rule312lambdaexpressions;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Test input for lambda body length with no violations.
 */
public class InputLambdaExpressionsBodyLengthValid {

    /**
     * Valid block body within the limit.
     */
    public void sumLegal() {
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
     * A short, single-expression lambda without braces.
     */
    public void testShortExpressionLambda() {
        Function<Integer, Integer> square = x -> x * x;
    }

    /**
     * Simple legal method.
     */
    public void doLegal() {
        Runnable r = () -> {
            int a = 1;
            int b = 2;
            int c = 3;
            int d = 4;
            int e = 5;
            int f = 6;
            int g = 7;
            int h = 8;
            int i = 9;
        };
    }
}
