/*
ArrayBracketNoWhitespace

*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketnowhitespace;

public class InputArrayBracketNoWhitespaceNextToken {
    void testImmediatelyAdjacentTokens(int[] arr) {
        int a = arr[0]+1; // violation ''\]' is not followed by whitespace.'
        int b = arr[0]-1; // violation ''\]' is not followed by whitespace.'
        int c = arr[0]*2; // violation ''\]' is not followed by whitespace.'
    }

    void testEarlierLineHighColumnTokens(
            int[] someVeryLongNamedArray) {
        int result = someVeryLongNamedArray[0]+1; // violation ''\]' is not followed by whitespace.'
    }

    void testTokenOnNextLine(int[] arr) {
        int x = arr[0]
            + 1;
        int y = arr[0]
            ;

        int[][
                  ] // violation ''\]' is preceded with whitespace.'
                   a
                    [] // violation ''\[' is preceded with whitespace.'
                          [] ;
        // 2 violations above:
        // ''\[' is preceded with whitespace.'
        // ''\]' is followed by whitespace.'

        for (int i = 0; i < arr[0] ; i++) {} // violation ''\]' is followed by whitespace.'
    }

    void testGetNextTokenFromParent(int[] arr) {
        int a = arr[0]++ + 1;
        int b = arr[0]-- + 1;
        int c = arr[0]++ - 1;
        int d = arr[0]-- - 1;
    }

    void testSiblingLineGuard(int[] arr) {
        int a =
                arr[0]
                      + 1;
        int b =
                arr[0]
                      - 1;
    }

    public void method1() { int a[]; }

    public void method2() { int a[];
    }

    public void method3() {
        int a[] = new int[10];
        int b = a[0]
                     +
                        a[1];
        int[][] c = { new int[10],
                                  new int[20]
                    };

        int len = new int[10]
                                .
                                    length;
    }

    void testMultilineMultiplicationChain(double[] a, double[] b, double[] c) {
        double result = a[0] * b[0] *
                        a[1] * b[1] * c[0];
    }

    void method4(String[] ... params) { // violation ''\]' is followed by whitespace'
    }

    void method5(String[]... params) {
    }
}
