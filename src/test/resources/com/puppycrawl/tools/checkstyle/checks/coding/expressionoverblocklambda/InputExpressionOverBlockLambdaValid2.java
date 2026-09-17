/*
ExpressionOverBlockLambda


*/

package com.puppycrawl.tools.checkstyle.checks.coding.expressionoverblocklambda;

/**
 * Test input for expression lambda preferred over single-line block lambda
 * - additional valid cases.
 */
public class InputExpressionOverBlockLambdaValid2 {

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
