/*
ArrayBracketNoWhitespace

*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketnowhitespace;

public class InputArrayBracketNoWhitespaceCreation {
    int[] good; int bad []; // violation ''\[' is preceded with whitespace.'

    void testArrayCreation() {
        int[] arr2 = new int [10]; // violation ''\[' is preceded with whitespace.'
        int[] arr3 = new int[ 10]; // violation ''\[' is followed by whitespace.'
        int[] arr4 = new int[10 ]; // violation ''\]' is preceded with whitespace.'
        int []arr5 = new int[10];
        // 2 violations above:
        // ''\[' is preceded with whitespace'
        // ''\]' is not followed by whitespace'
        int[] arr6 = new int[ 10 ];
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
}
