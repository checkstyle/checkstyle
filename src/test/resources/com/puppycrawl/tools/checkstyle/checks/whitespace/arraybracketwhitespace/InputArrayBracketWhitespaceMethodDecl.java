/*
ArrayBracketWhitespace


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketwhitespace;

public class InputArrayBracketWhitespaceMethodDecl {

    int[] getArray() { return new int[0]; }
    int [] getArray2() { return new int[0]; } // violation ''\[' is preceded with whitespace.'

    void process(int[] data) { }
    void process2(int [] data) { } // violation ''\[' is preceded with whitespace.'

    int[][] getMatrix() { return new int[0][0]; }
    int[] [] getMatrix2() { return new int[0][0]; }
    // 2 violations above:
    //  ''\]' is followed by whitespace.'
    //  ''\[' is preceded with whitespace.'

    void varargs(int... args) { }

    Object[] objects = new Object[5];
    Object [] objects2 = new Object[5]; // violation ''\[' is preceded with whitespace.'

    int[] a, b, c;

    int[][] matrix = new int[5][5];
    void matrixAccess() {
        int val = matrix[0][1];
        int val2 = matrix [0][1]; // violation ''\[' is preceded with whitespace.'
        int val3 = matrix[0] [1];
        // 2 violations above:
        //  ''\]' is followed by whitespace.'
        //  ''\[' is preceded with whitespace.'
    }

    @SuppressWarnings({ "unchecked" })
    void annotationTest() { }

    java.util.List<int[]> list1;
    java.util.List<int []> list2; // violation ''\[' is preceded with whitespace.'
}
