package com.puppycrawl.tools.checkstyle.checks.coding.arraytrailingcomma;

public class InputArrayTrailingComma
{
    int[] a1 = new int[]
    {
        1,
        2,
        3,
    };

    int[] a2 = new int[]
    {
        1,
        2,
        3 // violation
    };

    int[] b1 = new int[] {1, 2, 3,};
    int[] b2 = new int[] {1, 2, 3};

    int[][] c1 = new int[][] {{1, 2,}, {3, 3,}, {5, 6,},};
    int[][] c2 = new int[][] {{1, 2}, {3, 3,}, {5, 6,}};

    int[][] d1 = new int[][]
    {
        {1, 2,},
        {3, 3,},
        {5, 6,},
    };
    int[][] d2 = new int[][]
    {
        {1,
         2},
        {3, 3,},
        {5, 6,} // violation
    };

    int[] e1 = new int[] {
    };

    int[] f1 = new int[] {0, 1
    };

    int[][] f2 = new int[][]
    {
        {1,
         2,},
    };

    int[] f3 = new int[]{
            1,
            2
            ,
    };

    int[] f4 = new int[]{
            1,
            2
            ,};

    Object[][] g1 = new Object[][]
    {
        { 1, 1 },
        {
           null,
           new int[] { 2,
                   3 }, }, };

    Object[][] g2 = new Object[][]
    {
        { 1, 1 },
        {
           null,
           new int[] { 2,
                   3 } } }; // violation
}
