/*
NoArrayTrailingComma


*/

package com.puppycrawl.tools.checkstyle.checks.coding.noarraytrailingcomma;

public class InputNoArrayTrailingComma {

    int[] t1 = new int[] {
            1,
            2,
            3
    };

    int[] t2 = new int[] {
            1,
            2,
            3, // violation 'Array should not contain trailing comma.'
    };

    int[] t3 = new int[] {1,2,3};

    int[] t4 = new int[] {1,2,3,}; // violation 'Array should not contain trailing comma.'

    int[][] t5 = new int[][] {{1, 2}, {3, 3}, {5, 6}};

    // violation below 'Array should not contain trailing comma.'
    int[][] t6 = new int[][] {{1, 2}, {3, 3}, {5, 6},};

    int[][] t7 = new int[][]
    {
        {1, 2},
        {3, 3},
        {5, 6}
    };

    int[][] t8 = new int[][]
    {
        {1,
         2},
        {3, 3},
        {5, 6}, // violation 'Array should not contain trailing comma.'
    };

    int[][] t9 = new int[][] {
            {1,2,}, // violation 'Array should not contain trailing comma.'
            {2,3}
    };

    int[][] t10 = new int[][] {
            {1,
            2,} // violation 'Array should not contain trailing comma.'
    };

    int[][] t11 = new int[][] {
            {1,
            2}
    };

    int[] t12 = new int[] {1};

    int[] t13 = new int[]{};

    int[][] t14 = new int[][]{};

    int[] t15 = new int[] {1,}; // violation 'Array should not contain trailing comma.'
}
