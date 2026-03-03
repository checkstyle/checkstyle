/*
ArrayBracketNoWhitespace

*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketnowhitespace;

public class InputArrayBracketNoWhitespaceDeclarations {

    int[] ints;
    int [] badInts; // violation ''\[' is preceded with whitespace.'
    int[]arr; // violation ''\]' is not followed by whitespace.'

    int[][] matrix2D;
    int [][] badMatrix2D; // violation ''\[' is preceded with whitespace.'
    int[] [] badMatrix2D_2;
    // 2 violations above:
    // ''\]' is followed by whitespace.'
    // ''\[' is preceded with whitespace.'

    int[] afterType;
    int afterVar[];
    int badAfterVar1 []; // violation ''\[' is preceded with whitespace.'

    int[] method1() { return null; }
    int [] badMethod1() { return null; } // violation ''\[' is preceded with whitespace.'

    void param1(int[] arr) {}
    void param2(int[]arr) {} // violation ''\]' is not followed by whitespace.'
    void badParam1(int [] arr) {} // violation ''\[' is preceded with whitespace.'

    InputArrayBracketNoWhitespaceDeclarations(int[]arr) {}
    // violation above ''\]' is not followed by whitespace.'

    void localVariables() {
        int[] local1 = new int[10];
        int [] badLocal1 = new int[10]; // violation ''\[' is preceded with whitespace.'
        int local3[] = new int[10];
    }

    void anonymousArrays() {
        int[] arr1 = new int[] {1};
        int[] arr2 = new int [] {1}; // violation ''\[' is preceded with whitespace.'
        int[] arr3 = new int[ ] {1};
        // 2 violations above:
        // ''\[' is followed by whitespace.'
        // ''\]' is preceded with whitespace.'
    }

    void castExpressions() {
        Object obj = new int[1];
        int[] arr1 = (int[]) obj;
        int[]arr2 = (int[]) obj; // violation ''\]' is not followed by whitespace.'
        int[] arr3 = (int []) obj; // violation ''\[' is preceded with whitespace.'
    }

    void instanceofChecks(Object obj) {
        boolean b1 = obj instanceof int[];
        boolean b2 = obj instanceof int []; // violation ''\[' is preceded with whitespace.'
    }

    void classLiterals() {
        Class<?> cls1 = int[].class;
        Class<?> cls2 = int [].class; // violation ''\[' is preceded with whitespace.'
    }

    void testArrayInitializationWithArrayAccess() {
        byte[] bytes = new byte[10];
        int[] values = {bytes[0], bytes[1] };// violation '']' is followed by whitespace.'
        int[] invalid = {bytes[0], bytes[1]};
    }

    @java.lang.annotation.Target(java.lang.annotation.ElementType.TYPE_USE)
    @interface Ann {}

    int @Ann [] annotatedArr;
    int @Ann[] annotatedArr2; // violation ''\[' is not preceded with whitespace.'
    int @Ann [][] annotatedArr2D;
    void annotatedParam(@Ann int @Ann [] param) {}

    int @Ann [ ] badAnnotatedArr;
    // 2 violations above:
    // ''\[' is followed by whitespace.'
    // ''\]' is preceded with whitespace.'
}
