/*
ArrayBracketNoWhitespace

*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketnowhitespace;

import java.util.function.IntUnaryOperator;
import java.util.function.IntFunction;

public class InputArrayBracketNoWhitespaceDefault {
    int[] good; int bad []; // violation ''\[' is preceded with whitespace.'

    void testArrayCreation() {
        int[] arr2 = new int [10]; // violation ''\[' is preceded with whitespace.'
        int[] arr3 = new int[ 10]; // violation ''\[' is followed by whitespace.'
        int[] arr4 = new int[10 ]; // violation ''\]' is preceded with whitespace.'
        int[] arr5 = new int[ 10 ];
        // 2 violations above:
        // ''\[' is followed by whitespace.'
        // ''\]' is preceded with whitespace.'
    }

    void testArrayAccess() {
        int[] arr = new int[1];
        int a = arr[0] ; // violation ''\]' is followed by whitespace.'
        int b = arr [0]; // violation ''\[' is preceded with whitespace.'
        int c = arr[ 0]; // violation ''\[' is followed by whitespace.'
        int d = arr[0 ]; // violation ''\]' is preceded with whitespace.'
        int e = arr[ 0 ];
        // 2 violations above:
        // ''\[' is followed by whitespace.'
        // ''\]' is preceded with whitespace.'
    }

    void testMulti() {
        int[][] m = new int[1][2];
        int[] [] n = new int[1][2];
        // 2 violations above:
        // ''\]' is followed by whitespace.'
        // ''\[' is preceded with whitespace.'
        int v = m[0] [1];
        // 2 violations above:
        // ''\]' is followed by whitespace.'
        // ''\[' is preceded with whitespace.'
        int[] arr = {1, 2};
        int x = m[arr[0] ][arr[0]];
        // 2 violations above:
        // ''\]' is followed by whitespace.'
        // ''\]' is preceded with whitespace.'
    }

    void testMultiline() {
        int[] arr = new int
[5];
        int z = arr[1
];
        int a = arr[
        1];
        int b = arr[0]
        ;
        // Intentional trailing whitespace below
        // to test edge case handling in ArrayBracketNoWhitespaceCheck.
        int x[] 
        ;
    }

    void testMember() {
        String[] s = {"a"};
        String t1 = s[0].toString();
        String t2 = s[0] .toString(); // violation ''\]' is followed by whitespace.'
    }

    void testPostfixOperators() {
        int[] arr = {1, 2};
        int x = arr[0], u = arr[1];
        int y = arr[0] , v = arr[1]; // violation ''\]' is followed by whitespace.'
        arr[2] ++; // violation ''\]' is followed by whitespace.'
        arr[2]++;
        arr[2] --; // violation ''\]' is followed by whitespace.'
        arr[2]--;
        arr[0]+=1; // violation ''\]' is not followed by whitespace.'
        int a = arr[0] + arr[1];
        int b = arr[0]+ arr[1]; // violation ''\]' is not followed by whitespace.'
        int c = arr[0] - arr[1];
        int d = arr[0]- arr[1]; // violation ''\]' is not followed by whitespace.'
    }

    void testColonWhitespace() {
        IntFunction<int[]> f1 = int[]::new;
        IntFunction<int[] > f2 = int[]::new; // violation ''\]' is followed by whitespace.'
        IntFunction<int[]> f3 = int[] ::new; // violation ''\]' is followed by whitespace.'
    }

    void testAngleBracket() {
        int[] arr = new int[5];
        boolean result1 = arr[0]>=0; // violation ''\]' is not followed by whitespace.'
        boolean result2 = arr[0] >= 0;
        boolean result3 = arr[0]<=0; // violation ''\]' is not followed by whitespace.'
        boolean result4 = arr[0] <= 0;
        int result5 = arr[0]>>2;
        int result6 = arr[0] >>2; // violation ''\]' is followed by whitespace.'
        int result7 = arr[0]<<2;
        int result8 = arr[0] <<2; // violation ''\]' is followed by whitespace.'
        boolean result9 = arr[0]< 2; // violation ''\]' is not followed by whitespace'
    }

    void testArrayInit() {
        int[] arr3 = new int [] {1,2,3}; // violation ''\[' is preceded with whitespace.'
    }

    void testVar(int... nums) {
        int b = nums [0]; // violation ''\[' is preceded with whitespace.'
    }

    void testInstanceof(Object o) {
        if (o instanceof int []) {} // violation ''\[' is preceded with whitespace.'
    }
}
