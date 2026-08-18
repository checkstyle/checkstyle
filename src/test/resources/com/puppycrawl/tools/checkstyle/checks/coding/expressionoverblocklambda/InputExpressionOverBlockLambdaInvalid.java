/*
ExpressionOverBlockLambda


*/

package com.puppycrawl.tools.checkstyle.checks.coding.expressionoverblocklambda;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Test input for expression lambda preferred over single-line block lambda.
 */
public class InputExpressionOverBlockLambdaInvalid {

    /**
     * Single void statement block lambda - should be expression lambda.
     */
    public void testVoidBlockLambda() {
        Runnable a = () -> { System.out.println("hello"); };
        // violation above 'Expression lambdas are preferred over single-line block lambdas.'
    }

    /**
     * Single return block lambda - should be expression lambda.
     */
    public void testReturnBlockLambda() {
        Function<Integer, Integer> b = x -> { return x + 1; };
        // violation above 'Expression lambdas are preferred over single-line block lambdas.'
    }

    /**
     * Single return block lambda with multiple parameters.
     */
    public void testReturnBlockLambdaMultiParam() {
        BiFunction<Integer, Integer, Integer> c =
            (a, b) -> { return a + b; };
        // violation above 'Expression lambdas are preferred over single-line block lambdas.'
    }

    /**
     * Block lambda in method chain - should be expression lambda.
     */
    public void testMethodChain(List<Integer> list) {
        list.stream().forEach(s -> { System.out.println(s); });
        // violation above 'Expression lambdas are preferred over single-line block lambdas.'
    }

    /**
     * Multiple violations in one line.
     */
    public void testMultipleViolations(List<Integer> list) {
        list.stream()
            .filter(s -> { return s % 2 == 0; })
            // violation above 'Expression lambdas are preferred over single-line block lambdas.'
            .forEach(s -> { System.out.println(s); });
        // violation above 'Expression lambdas are preferred over single-line block lambdas.'
    }

}
