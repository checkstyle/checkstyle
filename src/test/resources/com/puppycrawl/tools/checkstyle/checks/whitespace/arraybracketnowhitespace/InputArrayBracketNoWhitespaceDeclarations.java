/*
ArrayBracketNoWhitespace

*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketnowhitespace;

public class InputArrayBracketNoWhitespaceDeclarations {

    // ===== PRIMITIVE TYPES =====

    int[] ints;
    int [] badInts; // violation ''\[' is preceded with whitespace.'
    int[]arr; // violation ''\]' is not followed by whitespace.'

    // ===== MULTIDIMENSIONAL ARRAYS =====

    int[][] matrix2D;
    int [][] badMatrix2D; // violation ''\[' is preceded with whitespace.'
    int[] [] badMatrix2D_2;
    // 2 violations above:
    // ''\]' is followed by whitespace.'
    // ''\[' is preceded with whitespace.'

    // ===== MIXED BRACKET POSITIONS =====

    int[] afterType;
    int afterVar[];
    int[] mixedStyle1[];
    int badAfterVar1 []; // violation ''\[' is preceded with whitespace.'
    int[] badMixedStyle1 []; // violation ''\[' is preceded with whitespace.'

    // ===== OBJECT & GENERIC ARRAYS =====

    String[] strings;
    String [] badStrings; // violation ''\[' is preceded with whitespace.'
    java.util.List<String>[] listArray1;
    java.util.List<String> [] badListArray1; // violation ''\[' is preceded with whitespace.'

    // ===== METHODS =====

    int[] method1() { return null; }
    int [] badMethod1() { return null; } // violation ''\[' is preceded with whitespace.'
    int method2()[] { return null; }
    int method3() [] { return null; } // violation ''\[' is preceded with whitespace.'

    void param1(int[] arr) {}
    void param2(int[]arr) {} // violation ''\]' is not followed by whitespace.'
    void badParam1(int [] arr) {} // violation ''\[' is preceded with whitespace.'

    InputArrayBracketNoWhitespaceDeclarations(
        int[]arr) {} // violation ''\]' is not followed by whitespace.'
    InputArrayBracketNoWhitespaceDeclarations(
        byte [] arr) {} // violation ''\[' is preceded with whitespace.'

    // ===== LOCAL VARIABLES =====

    void localVariables() {
        int[] local1 = new int[10];
        int [] badLocal1 = new int[10]; // violation ''\[' is preceded with whitespace.'
        int[]local2 = new int[5]; // violation ''\]' is not followed by whitespace.'
        int local3[] = new int[10];
        int badLocal4 [] = new int[10]; // violation ''\[' is preceded with whitespace.'
    }

    // ===== ANONYMOUS ARRAYS =====

    void anonymousArrays() {
        int[] arr1 = new int[] {1};
        int[] arr2 = new int [] {1}; // violation ''\[' is preceded with whitespace.'
        int[] arr3 = new int[ ] {1};
        // 2 violations above:
        // ''\[' is followed by whitespace.'
        // ''\]' is preceded with whitespace.'
    }

    // ===== CASTS AND INSTANCEOF =====

    void castExpressions() {
        Object obj = new int[1];
        int[] arr1 = (int[]) obj;
        int[]arr2 = (int[]) obj; // violation ''\]' is not followed by whitespace.'
        int[] arr3 = (int []) obj; // violation ''\[' is preceded with whitespace.'
        int[] arr4 = (int[] ) obj; // violation ''\]' is followed by whitespace.'
    }

    void instanceofChecks(Object obj) {
        boolean b1 = obj instanceof int[];
        boolean b2 = obj instanceof int []; // violation ''\[' is preceded with whitespace.'
    }

    // ===== CLASS LITERALS =====

    void classLiterals() {
        Class<?> cls1 = int[].class;
        Class<?> cls2 = int [].class; // violation ''\[' is preceded with whitespace.'
    }
}
