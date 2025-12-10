/*
ArrayBracketNoWhitespace

*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketnowhitespace;

import java.util.function.IntUnaryOperator;

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
        arr[0]+=1; // violation ''\]' is not followed by whitespace.'
        int a = arr[0] + arr[1];
        int b = arr[0]+ arr[1]; // violation ''\]' is not followed by whitespace.'
        int c = arr[0] - arr[1];
        int d = arr[0]- arr[1]; // violation ''\]' is not followed by whitespace.'
    }

    void testColonWhitespace() {
        int[][] arr = {{1, 2, 3}};
        Runnable r = arr[]::clone;
        Runnable r2 = arr[0] ::clone; // violation ''\]' is followed by whitespace.'
    }

    void testComplex() {
        int[] arr = {1};
        int[][] m = new int[1][1];
        IntUnaryOperator op = i -> arr[i] * 2;
        IntUnaryOperator op2 = i -> arr[i]* 2; // violation ''\]' is not followed by whitespace.'
        int x = m[arr[0] ][arr[0]];
        // 2 violations above:
        // ''\]' is followed by whitespace.'
        // ''\]' is preceded with whitespace.'
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
