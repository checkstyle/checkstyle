package com.puppycrawl.tools.checkstyle.checks.coding.arraytrailingcomma;

public class InputInlineArrayTrailingComma
{
    int[] a1 = new int[] {};
    int[] a2 = new int[] {,};

    int[] b1 = new int[] {1, 2, 3,};
    int[] b2 = new int[] {1,
            2,
            3};
    int[] b3 = new int[] {
            1,
            2,
            3,};

    int[][] c1 = new int[][] {{1, 2,}, {3, 3,}, {5, 6,},};
    int[][] c2 = new int[][] {{1, 2}, {3, 3,}, {5, 6,}};

    boolean[] d1 = new boolean[] {true};
    boolean[] d2 = new boolean[] {true,};

}
