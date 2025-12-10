/*
ArrayBracketNoWhitespace

*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketnowhitespace;

import java.util.function.IntUnaryOperator;

public class InputArrayBracketNoWhitespaceDefault {

    // ========== ARRAY DECLARATIONS ==========

    // Correct array declarations
    int[] goodArray1;
    int[][] goodArray2;
    String[] goodArray3;
    Object[] goodArray4;
    byte[] bytes;
    char[][] matrix;

    // wrong cases: space before [
    int [] badArray1; // violation ''\[' is preceded with whitespace.'
    int [][] badArray2; // violation, ''\[' is preceded with whitespace.'
    String [] badArray3; // violation ''\[' is preceded with whitespace.'
    Object [] badArray4; // violation ''\[' is preceded with whitespace.'

    // Array declarations with brackets after variable name - correct
    int goodArray5[];
    int goodArray6[][];
    String goodArray7[];

    // wrong cases: space before [ after variable name
    int badArray8 []; // violation ''\[' is preceded with whitespace.'
    int badArray9 [][]; // violation ''\[' is preceded with whitespace.'
    String badArray10 []; // violation ''\[' is preceded with whitespace.'

    // ========== ARRAY CREATION ==========

    void testArrayCreation() {
        // Correct
        int[] arr1 = new int[10];
        int[][] arr2 = new int[5][5];
        String[] arr3 = new String[20];

        // wrong cases: space before [
        int[] arr4 = new int [10]; // violation ''\[' is preceded with whitespace.'
        int[][] arr5 = new int [5][5]; // violation ''\[' is preceded with whitespace.'
        String[] arr6 = new String [20]; // violation ''\[' is preceded with whitespace.'

        // wrong cases: space after [
        int[] arr7 = new int[ 10]; // violation ''\[' is followed by whitespace.'
        int[] arr8 = new int[  10]; // violation ''\[' is followed by whitespace.'

        // wrong cases: space before ]
        int[] arr9 = new int[10 ]; // violation ''\]' is preceded with whitespace.'
        int[] arr10 = new int[10  ]; // violation ''\]' is preceded with whitespace.'

        // wrong cases: space inside brackets on both sides
        int[] arr11 = new int[ 10 ];
        // 2 violations above:
        // ''\[' is followed by whitespace.'
        // ''\]' is preceded with whitespace.'
    }

    // ========== ARRAY ACCESS ==========

    void testArrayAccess() {
        int[] arr = new int[10];

        // Correct
        int x = arr[0];
        int y = arr[5];
        arr[0] = 10;

        // wrong cases: space before [
        int z = arr [0]; // violation ''\[' is preceded with whitespace.'
        int w = arr  [5]; // violation ''\[' is preceded with whitespace.'
        arr [0] = 10; // violation ''\[' is preceded with whitespace.'

        // wrong cases: space after [
        int a = arr[ 0]; // violation ''\[' is followed by whitespace.'
        int b = arr[  5]; // violation ''\[' is followed by whitespace.'

        // wrong cases: space before ]
        int c = arr[0 ]; // violation ''\]' is preceded with whitespace.'
        int d = arr[5  ]; // violation ''\]' is preceded with whitespace.'

        // wrong cases: space on both sides
        int e = arr[ 0 ]; // 2 violations
        // 2 voilations above:
        // ''\[' is followed by whitespace.'
        // ''\]' is preceded with whitespace.'
    }

    // ========== RIGHT BRACKET FOLLOWED BY ANOTHER BRACKET ==========

    void testMultidimensionalArrays() {
        // Correct: no space between ][
        int[][] mat1 = new int[5][5];
        int[][][] mat2 = new int[3][3][3];
        int x = mat1[0][0];

        // wrong cases: space between ][
        int[] [] badMat1 = new int[5][5];
        // 2 violations above:
        // ''\]' is followed by whitespace.'
        // ''\[' is preceded with whitespace'
        int[][] [] badMat2 = new int[3][3][3];
        // 2 violations above:
        // ''\]' is followed by whitespace.'
        // ''\[' is preceded with whitespace'
        int[] [][] badMat3 = new int[2][2][2];
        // 2 violations above:
        // ''\]' is followed by whitespace.'
        // ''\[' is preceded with whitespace'

        // Nested array access - correct
        int[][] matrix = new int[5][5];
        int val = matrix[0][0];
        int val2 = matrix[1][2];

        // wrong cases: space in nested access
        int val3 = matrix[0] [0];
        // 2 violations above:
        // ''\]' is followed by whitespace.'
        // ''\[' is preceded with whitespace.'
        int val4 = matrix[1] [2];
        // 2 violations above:
        // ''\]' is followed by whitespace.'
        // ''\[' is preceded with whitespace.'

        // correct cases: nested with method
        int[] innerArray = matrix[0];
        int innerVal = innerArray[0];
    }

    // ========== RIGHT BRACKET FOLLOWED BY DOT ==========

    void testMemberAccess() {
        int[] arr = new int[10];
        String[] strings = new String[] {"hello", "world"};

        int len1 = arr[0];
        String str1 = strings[0].toString();
        int len2 = strings[0].length();
        Class<?> cls = strings[0].getClass();

        int len3 = arr[0];
        String str2 = strings[0] .toString(); // violation ''\]' is followed by whitespace.'
        int len4 = strings[0] .length(); // violation ''\]' is followed by whitespace.'

        // correct cases: chained member access
        String result = strings[0].substring(0, 1).toUpperCase();

        // wrong cases: space in chain
        String result2 = strings[0] .substring(0, 1).toUpperCase(); // violation ''\]' is followed by whitespace.'
    }

    // ========== RIGHT BRACKET FOLLOWED BY COMMA ==========

    void testComma() {
        int[] arr = new int[] {1, 2, 3, 4, 5};

        // correct cases: no space between ],
        int max1 = Math.max(arr[0], arr[1]);
        System.out.println(arr[0], arr[1]);
        method(arr[0], arr[1], arr[2]);

        // wrong cases: space between ],
        int max2 = Math.max(arr[0] , arr[1]); // violation ''\]' is followed by whitespace.'
        System.out.println(arr[0] , arr[1]); // violation ''\]' is followed by whitespace.'
        method(arr[0] , arr[1], arr[2]); // violation ''\]' is followed by whitespace.'
        method(arr[0], arr[1] , arr[2]); // violation ''\]' is followed by whitespace.'

        // Array initializer - correct
        int[] arr2 = {arr[0], arr[1], arr[2]};

        // Array initializer - incorrect
        int[] arr3 = {arr[0] , arr[1], arr[2]}; // violation ''\]' is followed by whitespace.'
    }

    void method(int a, int b, int c) {}

    // ========== RIGHT BRACKET FOLLOWED BY SEMICOLON ==========

    void testSemicolon() {
        int[] arr = new int[10];

        // correct cases: no space between ];
        int x = arr[0];
        arr[1] = 5;
        System.out.println(arr[2]);

        // wrong cases: space between ];
        int y = arr[0] ; // violation ''\]' is followed by whitespace.'

        // For loop - correct
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }

        // For loop - violation
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i ;
            int val = arr[i] ; // violation ''\]' is followed by whitespace.'
        }
    }

    // ========== TESTS FOR MUTILINE ARRAY ACCESS (NOT EXPECTING VIOLATIONS) ==========

    void testMultilineArrayAccess() {
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

    // ========== RIGHT BRACKET FOLLOWED BY POSTFIX OPERATORS ==========

    void testPostfixOperators() {
        int[] arr = new int[] {0, 1, 2, 3, 4};

        // correct cases: no space between ]++ and ]--
        arr[0]++;
        arr[1]--;
        int x = arr[2]++;
        int y = arr[3]--;

        // wrong cases: space between ]++ and ]--
        arr[0] ++; // violation ''\]' is followed by whitespace.'
        arr[1] --; // violation ''\]' is followed by whitespace.'
        int z = arr[2] ++; // violation ''\]' is followed by whitespace.'
        int w = arr[3] --; // violation ''\]' is followed by whitespace.'

        // Multiple postfix - correct
        arr[0]++;
        arr[1]++;

        // Multiple postfix - violations
        arr[2] ++; // violation ''\]' is followed by whitespace.'
        arr[3] ++; // violation ''\]' is followed by whitespace.'
    }

    // ========== RIGHT BRACKET FOLLOWED BY BINARY/COMPOUND OPERATORS ==========

    void testBinaryOperators() {
        int[] arr = new int[] {1, 2, 3, 4, 5};

        // correct cases: space after ] before binary operator
        int x = arr[0] + arr[1];
        int y = arr[0] - arr[1];
        int z = arr[0] * arr[1];
        int w = arr[0] / arr[1];
        int m = arr[0] % arr[1];
        boolean b1 = arr[0] == arr[1];
        boolean b2 = arr[0] != arr[1];
        boolean b3 = arr[0] < arr[1];
        boolean b4 = arr[0] > arr[1];

        // wrong cases: no space after ] before binary operator
        int x2 = arr[0]+ arr[1]; // violation ''\]' is not followed by whitespace.'
        int y2 = arr[0]- arr[1]; // violation ''\]' is not followed by whitespace.'
        int z2 = arr[0]* arr[1]; // violation ''\]' is not followed by whitespace.'
        int w2 = arr[0]/ arr[1]; // violation ''\]' is not followed by whitespace.'
        int m2 = arr[0]% arr[1]; // violation ''\]' is not followed by whitespace.'
        boolean b5 = arr[0]== arr[1]; // violation ''\]' is not followed by whitespace.'
        boolean b6 = arr[0]!= arr[1]; // violation ''\]' is not followed by whitespace.'
        boolean b7 = arr[0]< arr[1]; // violation ''\]' is not followed by whitespace.'
        boolean b8 = arr[0]> arr[1]; // violation ''\]' is not followed by whitespace.'

        // correct cases: compound assignment operators (need space)
        arr[0] += 1;
        arr[1] -= 1;
        arr[2] *= 2;
        arr[3] /= 2;
        arr[4] %= 2;

        // wrong cases: no space before compound operator
        arr[0]+= 1; // violation ''\]' is not followed by whitespace.'
        arr[1]-= 1; // violation ''\]' is not followed by whitespace.'
        arr[2]*= 2; // violation ''\]' is not followed by whitespace.'
        arr[3]/= 2; // violation ''\]' is not followed by whitespace.'
        arr[4]%= 2; // violation ''\]' is not followed by whitespace.'
    }

    // ========== RIGHT BRACKET FOLLOWED BY CLOSING CONSTRUCTS ==========

    void testClosingConstructs() {
        int[] arr = new int[10];

        // correct cases: no space between ])
        int x = (arr[0]);
        System.out.println(arr[0]);
        method(arr[0], arr[1], arr[2]);
        if (arr[0] > 0) {}

        // wrong cases: space between ])
        int y = (arr[0] ); // violation ''\]' is followed by whitespace.'
        System.out.println(arr[0] ); // violation ''\]' is followed by whitespace.'
        method(arr[0] , arr[1], arr[2]); // violation ''\]' is followed by whitespace.'
        if (arr[0]  > 0) {}

        // correct cases: closing brace
        int[] arr2 = {arr[0], arr[1]};

        // wrong cases: space before }
        int[] arr3 = {arr[0], arr[1] }; // violation ''\]' is followed by whitespace.'

        // Nested brackets - correct
        int[][] matrix = new int[5][5];
        int val = matrix[arr[0]][arr[1]];

        // Nested brackets - wrong cases
        int val2 = matrix[arr[0] ][arr[1]];
        // 2 violations above:
        // ''\]' is followed by whitespace.'
        // ''\]' is preceded with whitespace'
        int val3 = matrix[arr[0]][arr[1] ];
        // 2 violations above:
        // ''\]' is followed by whitespace.'
        // ''\]' is preceded with whitespace'
    }

    // ========== COMPLEX SCENARIOS ==========

    void testComplexScenarios() {
        int[] arr = new int[10];
        int[][] matrix = new int[5][5];
        String[] strings = new String[] {"a", "b", "c"};

        // correct cases: method chaining
        String result = strings[0].toUpperCase().substring(0, 1);

        // correct cases: nested array access with operations
        int val = matrix[arr[0]][arr[1]] + matrix[arr[2]][arr[3]];

        // correct cases: array access in expressions
        boolean check = arr[0] > 0 && arr[1] < 10;

        // correct cases: array access with ternary
        int x = arr[0] > 0 ? arr[0] : arr[1];

        // wrong cases: multiple issues
        int y = matrix[arr[0] ][arr[1]] + matrix[arr[2]][arr[3] ];
        // 4 violations above:
        // ''\]' is followed by whitespace.'
        // ''\]' is preceded with whitespace.'
        // ''\]' is followed by whitespace.'
        // ''\]' is preceded with whitespace.'

        // Lambda with array access - correct
        IntUnaryOperator op = i -> arr[i] * 2;

        // Lambda with array access - violation
        IntUnaryOperator op2 = i -> arr[i]* 2; // violation ''\]' is not followed by whitespace.'
    }

    // ========== ARRAY INITIALIZERS ==========

    void testArrayInitializers() {
        // correct cases
        int[] arr1 = {1, 2, 3};
        int[] arr2 = new int[] {1, 2, 3};
        int[][] mat1 = {{1, 2}, {3, 4}};

        // wrong cases in initialization
        int[] arr3 = new int [] {1, 2, 3}; // violation ''\[' is preceded with whitespace.'
        int[] arr4 = new int[ ] {1, 2, 3};
        // 2 violations above:
        // ''\[' is followed by whitespace.'
        // ''\]' is preceded with whitespace.'
    }

    // ========== VARARGS ==========

    void testVarargs(int... numbers) {
        // correct cases
        int x = numbers[0];
        numbers[0] = 10;

        // wrong cases
        int y = numbers [0]; // violation ''\[' is preceded with whitespace.'
        numbers [0] = 10; // violation ''\[' is preceded with whitespace.'
    }

    void callVarargs() {
        testVarargs(1, 2, 3);
    }

    // ========== GENERICS WITH ARRAYS ==========

    <T> void genericMethod(T[] items) {
        // correct cases
        T item = items[0];

        // wrong cases
        T item2 = items [0]; // violation ''\[' is preceded with whitespace.'
        T item3 = items[ 0]; // violation ''\[' is followed by whitespace.'
    }

    // ========== INSTANCEOF WITH ARRAYS ==========

    void testInstanceof(Object obj) {
        // correct cases
        if (obj instanceof int[]) {}
        if (obj instanceof String[]) {}

        // wrong cases
        if (obj instanceof int []) {} // violation ''\[' is preceded with whitespace.'
        if (obj instanceof String []) {} // violation ''\[' is preceded with whitespace.'
    }
}
