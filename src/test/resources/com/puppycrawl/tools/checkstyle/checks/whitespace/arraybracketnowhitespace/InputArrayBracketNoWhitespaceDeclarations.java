/*
ArrayBracketNoWhitespace
*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketnowhitespace;

/**
 * Test input focused on array declarations.
 */
public class InputArrayBracketNoWhitespaceDeclarations {

    // ========== PRIMITIVE TYPES ==========

    // Correct declarations
    byte[] bytes;
    short[] shorts;
    int[] ints;
    long[] longs;
    float[] floats;
    double[] doubles;
    char[] chars;
    boolean[] booleans;

    // Violations: space before [
    byte []badBytes; // violation ''[' is preceded with whitespace.'
    short []badShorts; // violation ''[' is preceded with whitespace.'
    int []badInts; // violation ''[' is preceded with whitespace.'
    long []badLongs; // violation ''[' is preceded with whitespace.'
    float []badFloats; // violation ''[' is preceded with whitespace.'
    double []badDoubles; // violation ''[' is preceded with whitespace.'
    char []badChars; // violation ''[' is preceded with whitespace.'
    boolean []badBooleans; // violation ''[' is preceded with whitespace.'

    // ========== MULTIDIMENSIONAL ARRAYS ==========

    // Correct
    int[][] matrix2D;
    int[][][] matrix3D;
    int[][][][] matrix4D;

    // Violations: space before first [
    int [][]badMatrix2D; // violation ''[' is preceded with whitespace.'
    int [][][]badMatrix3D; // violation ''[' is preceded with whitespace.'

    // Violations: space between brackets
    int[] []badMatrix2D_2; // violation '']' is followed by whitespace.'
    int[][] []badMatrix3D_2; // violation '']' is followed by whitespace.'
    int[] [][] badMatrix3D_3; // violation '']' is followed by whitespace.'

    // Added as per instruction: whitespace after ]
    int[]arr, goodCase1; // violation '']' is not followed by whitespace.'
    int[]arr2 ; // violation '']' is not followed by whitespace.'

    // ========== MIXED BRACKET POSITIONS ==========

    // Correct: brackets after type
    int[] afterType1;
    int[][] afterType2;

    // Correct: brackets after variable
    int afterVar1[];
    int afterVar2[][];

    // Correct: mixed (allowed in Java)
    int[] mixedStyle1[];
    int[][] mixedStyle2[];

    // Violations: space before brackets after variable
    int badAfterVar1 []; // violation ''[' is preceded with whitespace.'
    int badAfterVar2 [][]; // violation ''[' is preceded with whitespace.'

    // Violations: space in mixed style
    int[] badMixedStyle1 []; // violation ''[' is preceded with whitespace.'
    int badMixedStyle2[] []; // violation ''[' is preceded with whitespace.'

    // ========== OBJECT TYPES ==========

    // Correct
    String[] strings;
    Object[] objects;
    Integer[] integers;
    java.util.List[] lists;

    // Violations: space before [
    String []badStrings; // violation ''[' is preceded with whitespace.'
    Object []badObjects; // violation ''[' is preceded with whitespace.'
    Integer []badIntegers; // violation ''[' is preceded with whitespace.'

    // ========== GENERIC TYPES WITH ARRAYS ==========

    // Correct
    java.util.List<String>[] listArray1;
    java.util.Map<String, Integer>[] mapArray1;

    // Violations: space before [
    java.util.List<String> []badListArray1; // violation ''[' is preceded with whitespace.'
    java.util.Map<String, Integer> []badMapArray1; // violation ''[' is preceded with whitespace.'

    // ========== METHOD DECLARATIONS ==========

    // Correct return types
    int[] method1() { return null; }
    int[][] method2() { return null; }
    String[] method3() { return null; }

    // Violations: space before [ in return type
    int []badMethod1() { return null; } // violation ''[' is preceded with whitespace.'
    int [][]badMethod2() { return null; } // violation ''[' is preceded with whitespace.'
    String []badMethod3() { return null; } // violation ''[' is preceded with whitespace.'

    // Correct: brackets after method name
    int method4()[] { return null; }
    int method5()[][] { return null; }

    // Violations: space before brackets after method name
    int method6() [] { return null; } // violation ''[' is preceded with whitespace.'
    int method7() [][] { return null; } // violation ''[' is preceded with whitespace.'

    // ========== METHOD PARAMETERS ==========

    // Correct parameters
    void param1(int[] arr) {}
    void param2(int[][] matrix) {}
    void param3(String[] strings) {}

    // Case: no whitespace after ]
    void param4(int[]arr) {} // violation '']' is not followed by whitespace.'

    // Violations: space before [ in parameters
    void badParam1(int []arr) {} // violation ''[' is preceded with whitespace.'
    void badParam2(int [][]matrix) {} // violation ''[' is preceded with whitespace.'
    void badParam3(String []strings) {} // violation ''[' is preceded with whitespace.'

    // ========== CONSTRUCTOR PARAMETERS ==========

    // Correct
    InputArrayBracketNoWhitespaceDeclarations(int[]arr) {} // violation '']' is not followed by whitespace.'
    InputArrayBracketNoWhitespaceDeclarations(int[][] matrix, String[] strings) {}

    // Violations: space before [ in constructor parameters
    InputArrayBracketNoWhitespaceDeclarations(byte []arr) {} // violation ''[' is preceded with whitespace.'
    InputArrayBracketNoWhitespaceDeclarations(short [][]matrix, char []chars) {} // 2 violations
    // violation above ''[' is preceded with whitespace.'
    // violation above ''[' is preceded with whitespace.'

    // ========== LOCAL VARIABLES ==========

    void localVariables() {
        // Correct
        int[] local1 = new int[10];
        int[][] local2 = new int[5][5];
        String[] local3 = new String[20];

        // Case: no whitespace after ]
        int[]local6 = new int[10]; // violation '']' is not followed by whitespace.'

        // Violations: space before [
        int []badLocal1 = new int[10]; // violation ''[' is preceded with whitespace.'
        int [][]badLocal2 = new int[5][5]; // violation ''[' is preceded with whitespace.'
        String []badLocal3 = new String[20]; // violation ''[' is preceded with whitespace.'

        // Correct: brackets after variable name
        int local4[] = new int[10];
        int local5[][] = new int[5][5];

        // Violations: space before brackets after variable name
        int badLocal4 [] = new int[10]; // violation ''[' is preceded with whitespace.'
        int badLocal5 [][] = new int[5][5]; // violation ''[' is preceded with whitespace.'
    }

    // ========== VARARGS ==========

    // Correct
    void varargs1(int... numbers) {}
    void varargs2(String... strings) {}
    void varargs3(Object... objects) {}

    // Note: varargs don't use brackets, so no violations here

    // ========== ANONYMOUS ARRAYS ==========

    void anonymousArrays() {
        // Correct
        int[] arr1 = new int[]{1, 2, 3};
        String[] arr2 = new String[]{"a", "b"};

        // Violations: space before [ in anonymous array
        int[] arr3 = new int []{1, 2, 3}; // violation ''[' is preceded with whitespace.'
        String[] arr4 = new String []{"a", "b"}; // violation ''[' is preceded with whitespace.'

        // Violations: space after [ in anonymous array
        int[] arr5 = new int[ ]{1, 2, 3}; // violation ''[' is followed by whitespace.'
        String[] arr6 = new String[  ]{"a", "b"}; // violation ''[' is followed by whitespace.'

        // Violations: space before ] in anonymous array
        int[] arr7 = new int[] {1, 2, 3}; // violation '']' is followed by whitespace.'
        String[] arr8 = new String[]  {"a", "b"}; // violation '']' is followed by whitespace.'
    }

    // ========== CAST EXPRESSIONS ==========

    void castExpressions() {
        Object obj = new int[10];

        // Correct
        int[] arr1 = (int[]) obj;
        int[][] arr2 = (int[][]) obj;

        // Case: no whitespace after ]
        int[]arr5 = (int[]) obj; // violation '']' is not followed by whitespace.'

        // Violations: space before [ in cast
        int[] arr3 = (int []) obj; // violation ''[' is preceded with whitespace.'
        int[][] arr4 = (int [][]) obj; // violation ''[' is preceded with whitespace.'
    }

    // ========== INSTANCEOF ==========

    void instanceofChecks(Object obj) {
        // Correct
        boolean b1 = obj instanceof int[];
        boolean b2 = obj instanceof String[];
        boolean b3 = obj instanceof int[][];

        // Violations: space before [
        boolean b4 = obj instanceof int []; // violation ''[' is preceded with whitespace.'
        boolean b5 = obj instanceof String []; // violation ''[' is preceded with whitespace.'
        boolean b6 = obj instanceof int [][]; // violation ''[' is preceded with whitespace.'
    }

    // ========== CLASS LITERALS ==========

    void classLiterals() {
        // Correct
        Class<?> cls1 = int[].class;
        Class<?> cls2 = String[].class;
        Class<?> cls3 = int[][].class;

        // Violations: space before [
        Class<?> cls4 = int [].class; // violation ''[' is preceded with whitespace.'
        Class<?> cls5 = String [].class; // violation ''[' is preceded with whitespace.'
        Class<?> cls6 = int [][].class; // violation ''[' is preceded with whitespace.'
    }
}
