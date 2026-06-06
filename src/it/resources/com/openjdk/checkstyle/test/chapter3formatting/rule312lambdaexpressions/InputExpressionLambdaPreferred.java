package com.openjdk.checkstyle.test.chapter3formatting.rule312lambdaexpressions;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Test input for expression lambda preferred over single-line block lambda with violations.
 */
public class InputExpressionLambdaPreferred {

    /**
     * Single void statement block lambda - should be expression lambda.
     */
    public void testVoidBlockLambda() {
        // violation below 'Expression lambdas are preferred over single-line block lambdas.'
        Runnable a = () -> { System.out.println("hello"); };
    }

    /**
     * Single return block lambda - should be expression lambda.
     */
    public void testReturnBlockLambda() {
        // violation below 'Expression lambdas are preferred over single-line block lambdas.'
        Function<Integer, Integer> b = x -> { return x + 1; };
    }

    /**
     * Single return block lambda with multiple parameters.
     */
    public void testReturnBlockLambdaMultiParam() {
        // violation below 'Expression lambdas are preferred over single-line block lambdas.'
        BiFunction<Integer, Integer, Integer> c = (a, b) -> { return a + b; };
    }

}
