/*
ArrayBracketWhitespace


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketwhitespace;

public class InputArrayBracketWhitespaceDefault {

    int [] arr1; // violation ''\[' is preceded with whitespace.'
    int[] arr2;
    int [ ] arr3;
    // 3 violations above:
    //  ''\[' is followed by whitespace.'
    //  ''\[' is preceded with whitespace.'
    //  ''\]' is preceded with whitespace.'
    int[]arr4; // violation ''\]' is not followed by whitespace.'
    int[][] arr5;
    int[] [] arr6;
    // 2 violations above:
    //  ''\]' is followed by whitespace.'
    //  ''\[' is preceded with whitespace.'

    void method(int[] p) {
        int i = p [0]; // violation ''\[' is preceded with whitespace.'
        int j = p[ 0]; // violation ''\[' is followed by whitespace.'
        int k = p[0 ]; // violation ''\]' is preceded with whitespace.'
        int x = p[0] + 1;
        p[0]++;
        p[0] ++; // violation ''\]' is followed by whitespace.'
        p[0] = 1;
        p[0]+= 1; // violation ''\]' is not followed by whitespace.'
        int len = p[0] ; // violation ''\]' is followed by whitespace.'
        int[] pos = new int[2];
        Object v = new Rect(pos[0], pos[1]);
    }
    static class Rect { Rect(int a, int b) {} }
}
