package com.puppycrawl.tools.checkstyle.checks.coding;

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
        3
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
        {5, 6,}
    };

    int[] e1 = new int[] {
    };
}
