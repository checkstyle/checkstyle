package com.puppycrawl.tools.checkstyle.checks.coding.arraytrailingcomma;

/**
 * Config:
 * alwaysDemandTrailingComma = true
 */
public class InputArrayTrailingCommaAlwaysDemandTrailingComma {
    public int[] test() {
        if (true) {
            return new int[]{0,};
        }
        return new int[]{0}; // violation
    }

    public int[] test2() {
        if (true) {
            return new int[]{0, 1,};
        }
        return new int[]{0, 1}; // violation
    }

    public void test3() {
        int[] a = new int[]
            {0}; // violation
        int[] b = new int[]
            {0, 1}; // violation
        int[] c = new int[]
            {0, 1,
                2, 3
            }; // violation
        int[] d = new int[]
            {
                1,
                2,
                3
            }; // violation
        int[] e = new int[]
            {
                1,
                5,
                6,
            };
        int[] f = {1,
            2
        }; // violation
        int[] g = {1,
            2,
        };
        int[][] empty2d = {{}}; // violation
        int[][] multiDimensionalArray = {
            {1, 2}, // violation
            {1,},
            {3, 2, 1,}
        }; // violation
    }
}
