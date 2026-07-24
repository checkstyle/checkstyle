/*
ArrayBracketNoWhitespace

*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketnowhitespace;

public class InputArrayBracketNoWhitespaceExpressions {
    void testComplexExpressions() {
        int[] arr = new int[10];

        // Nested array access with complex expressions
        int[][] matrix = new int[5][5];
        int result1 = matrix[arr[0]][arr[1]];
        int result2 = matrix[ arr[0]][arr[1 ]];
        // 2 violations above:
        // ''\[' is followed by whitespace'
        // ''\]' is preceded with whitespace'

        // Array access in ternary operator
        int result3 = arr[0] > 0 ? arr[1] : arr[2];

        boolean result4 = arr[0] == arr[1]
            && arr[2] == arr[3];

        // Array access in method call
        System.out.println(arr[0]);

        // Array access with cast
        int result5 = (int) arr[0];

        // Array access in lambda
        java.util.function.IntSupplier supplier = () -> arr[0];

        // Multiple array accesses on same line
        int result6 = arr[0] + arr[1] + arr[2];

        // Array in synchronized block
        synchronized (arr) {
            int x = arr[0];
        }

        // Array access in assert
        assert arr[0] > 0;
    }

    int getFirst(int[] arr) {
        return arr[0];
    }

    void throwException(int[] arr) {
        if (arr[0] < 0) throw new RuntimeException("negative");
    }

    void testEdgeCasesWithWhitespace() {
        int[] arr = new int[10];

        // Array access at end of line with various following tokens
        int b = arr[0]
            + 1;

        int c = arr[0]
            * 2;

        boolean i = arr[0]
            > 0;

        boolean m = arr[0]
            == 0;

        int o = arr[0]
            + arr[1];

        int q = arr[0]+ arr[1] // violation ''\]' is not followed by whitespace'
            + arr[2];
    }
}
