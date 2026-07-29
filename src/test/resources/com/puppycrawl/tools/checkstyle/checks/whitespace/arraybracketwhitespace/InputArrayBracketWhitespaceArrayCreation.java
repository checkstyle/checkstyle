/*
ArrayBracketWhitespace


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketwhitespace;

import java.util.function.IntFunction;

public class InputArrayBracketWhitespaceArrayCreation {

    int[] a1 = new int[5];
    int[] a2 = new int [5]; // violation ''\[' is preceded with whitespace.'
    int[] a3 = new int[ 5]; // violation ''\[' is followed by whitespace.'
    int[] a4 = new int[5 ]; // violation ''\]' is preceded with whitespace.'

    int[][] m1 = new int[5][10];
    int[][] m2 = new int [5][10]; // violation ''\[' is preceded with whitespace.'
    int[][] m3 = new int[5] [10];
    // 2 violations above:
    //  ''\]' is followed by whitespace.'
    //  ''\[' is preceded with whitespace.'
    int[][] m4 = new int[5][10 ]; // violation ''\]' is preceded with whitespace.'

    int[] a5 = new int[]{1, 2, 3}; // violation ''\]' is not followed by whitespace.'
    int[] a6 = new int []{1, 2, 3};
    // 2 violations above:
    //  ''\[' is preceded with whitespace.'
    //  ''\]' is not followed by whitespace.'
    int[] a7 = new int[] {1, 2, 3};

    String[] s1 = new String[10];
    String[] s2 = new String [10]; // violation ''\[' is preceded with whitespace.'

    IntFunction<int[]> f1 = int[]::new;
    IntFunction<int[]> f2 = int []::new; // violation ''\[' is preceded with whitespace.'
    IntFunction<int[]> f3 = int[] ::new; // violation ''\]' is followed by whitespace.'
}
