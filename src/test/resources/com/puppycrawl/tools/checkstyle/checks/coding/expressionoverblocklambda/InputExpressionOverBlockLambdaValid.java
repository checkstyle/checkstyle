/*
ExpressionOverBlockLambda


*/

package com.puppycrawl.tools.checkstyle.checks.coding.expressionoverblocklambda;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Test input for expression lambda preferred over single-line block lambda - valid cases.
 */
public class InputExpressionOverBlockLambdaValid {

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

    /**
     * Single-statement multi-line block lambda - NOT a single-line block lambda.
     */
    public void testSingleStatementMultiLineBlock() {
        Runnable f = () -> {
            System.out.println("hello");
        };
        Function<Integer, Integer> g = x -> {
            return x + 1;
        };
    }

    /**
     * Method chain with expression lambdas.
     */
    public void testMethodChain(List<Integer> list) {
        list.stream().forEach(s -> System.out.println(s));
    }

    /**
     * Method chain with multi-line block lambda.
     */
    public void testMethodChainMultiLine(List<Integer> list) {
        list.stream().forEach(s -> {
            System.out.println(s);
        });
    }

    /**
     * Single-line bare return - cannot be expression lambda.
     */
    public void testSingleLineBareReturn() {
        Runnable h = () -> { return; };
    }

    /**
     * Single-line multi-statement block - cannot be expression lambda.
     */
    public void testSingleLineMultiStatementBlock() {
        Runnable i = () -> { System.out.println("a"); System.out.println("b"); };
    }

    /**
     * Switch rule lambda - not flagged by this check.
     */
    public void testSwitchRule(int x) {
        switch (x) {
            case 1 -> System.out.println("one");
            default -> System.out.println("other");
        }
    }

    /** Single-line block with variable declaration. */
    public void testSingleLineVariableDeclaration() {
        Runnable j = () -> { int x = 1; };
    }

    /** Empty block lambda - no statements to convert. */
    public void testEmptyBlock() {
        Runnable k = () -> {};
    }

    /** Block with IF statement - not convertible to expression lambda. */
    public void testBlockWithIfStatement() {
        Runnable r = () -> { if (true) { System.out.println("hello"); } };
    }

}
