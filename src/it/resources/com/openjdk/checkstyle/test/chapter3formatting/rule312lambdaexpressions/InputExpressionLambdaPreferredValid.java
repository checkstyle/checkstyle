package com.openjdk.checkstyle.test.chapter3formatting.rule312lambdaexpressions;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Test input for expression lambda preferred over single-line block lambda with no violations.
 */
public class InputExpressionLambdaPreferredValid {

    /**
     * Expression lambda - void call, no braces.
     */
    public void testExpressionLambdaVoid() {
        Runnable a = () -> System.out.println("hello");
    }

    /**
     * Expression lambda - return value, no braces.
     */
    public void testExpressionLambdaReturn() {
        Function<Integer, Integer> b = x -> x + 1;
    }

    /**
     * Expression lambda with multiple parameters.
     */
    public void testExpressionLambdaMultiParam() {
        BiFunction<Integer, Integer, Integer> c = (a, b) -> a + b;
    }

    /**
     * Void return block lambda - cannot be expression lambda.
     */
    public void testVoidReturn() {
        Runnable d = () -> { return; };
    }

    /**
     * Multi-statement block lambda - cannot be expression lambda.
     */
    public void testMultiStatementBlock() {
        Runnable e = () -> {
            System.out.println("first");
            System.out.println("second");
        };
    }

}
