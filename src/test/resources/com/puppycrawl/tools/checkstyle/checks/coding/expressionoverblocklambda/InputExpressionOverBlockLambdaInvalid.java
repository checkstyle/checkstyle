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
        Runnable a = () -> { System.out.println("hello"); }; // violation 'Expression lambdas'
    }

    /**
     * Single return block lambda - should be expression lambda.
     */
    public void testReturnBlockLambda() {
        Function<Integer, Integer> b = x -> { return x + 1; }; // violation 'Expression lambdas'
    }

    /**
     * Single return block lambda with multiple parameters.
     */
    public void testReturnBlockLambdaMultiParam() {
        BiFunction<Integer, Integer, Integer> c =
            (a, b) -> { return a + b; }; // violation 'Expression lambdas'
    }

    /**
     * Block lambda in method chain - should be expression lambda.
     */
    public void testMethodChain(List<Integer> list) {
        list.stream().forEach(s -> { System.out.println(s); }); // violation 'Expression lambdas'
    }

    /**
     * Multiple violations in one line.
     */
    public void testMultipleViolations(List<Integer> list) {
        list.stream()
            .filter(s -> { return s % 2 == 0; }) // violation 'Expression lambdas'
            .forEach(s -> { System.out.println(s); }); // violation 'Expression lambdas'
    }

}
