/*
ArrayBracketNoWhitespace

*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketnowhitespace;

public class InputArrayBracketNoWhitespaceBranchCoverage {

    // Edge cases to cover missing branches in findClosestTokenAfter

    void testComplexExpressions() {
        int[] arr = new int[10];

        // Nested array access with complex expressions
        int[][] matrix = new int[5][5];
        int result1 = matrix[arr[0]][arr[1]];

        // Array access in ternary operator
        int result2 = arr[0] > 0 ? arr[1] : arr[2];

        // Array access in method call
        System.out.println(arr[0]);

        // Array access with cast
        int result3 = (int) arr[0];

        // Array access in lambda
        java.util.function.IntSupplier supplier = () -> arr[0];

        // Multiple array accesses on same line
        int result6 = arr[0] + arr[1] + arr[2];

        // Array access followed by method call chain
        String result7 = arr[0] > 0 ? "positive" : "negative";

        // Array in synchronized block
        synchronized (arr) {
            int x = arr[0];
        }

        // Array access in assert
        assert arr[0] > 0;
    }

    int getFirst(int[] arr) {
        // Array access in return statement
        return arr[0];
    }

    void throwException(int[] arr) {
        // Array access in throw statement
        if (arr[0] < 0) {
            throw new RuntimeException("negative");
        }
    }

    void testEdgeCasesWithWhitespace() {
        int[] arr = new int[10];

        // Array access at end of line with various following tokens
        int b = arr[0]
            + 1;

        int c = arr[0]
            * 2;

        int d = arr[0]
            / 2;

        int e = arr[0]
            % 2;

        // Array access with bitwise operators
        int f = arr[0]
            & 1;

        int g = arr[0]
            | 1;

        int h = arr[0]
            ^ 1;

        // Array access with comparison operators
        boolean i = arr[0]
            > 0;

        boolean j = arr[0]
            < 0;

        boolean k = arr[0]
            >= 0;

        boolean l = arr[0]
            <= 0;

        boolean m = arr[0]
            == 0;

        boolean n = arr[0]
            != 0;
    }
}
