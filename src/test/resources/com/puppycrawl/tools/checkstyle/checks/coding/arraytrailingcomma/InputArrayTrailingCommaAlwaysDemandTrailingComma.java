/*
ArrayTrailingComma
alwaysDemandTrailingComma = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.arraytrailingcomma;

public class InputArrayTrailingCommaAlwaysDemandTrailingComma {
    public int[] test() {
        if (true) {
            return new int[]{0,};
        }
        return new int[]{0}; // violation 'Array should contain trailing comma.'
    }

    public int[] test2() {
        if (true) {
            return new int[]{0, 1,};
        }
        return new int[]{0, 1}; // violation 'Array should contain trailing comma.'
    }

    public void test3() {
        int[] a = new int[]
            {0}; // violation 'Array should contain trailing comma.'
        int[] b = new int[]
            {0, 1}; // violation 'Array should contain trailing comma.'
        int[] c = new int[]
            {0, 1,
                2, 3 // violation 'Array should contain trailing comma.'
            };
        int[] d = new int[]
            {
                1,
                2,
                3 // violation 'Array should contain trailing comma.'
            };
        int[] e = new int[]
            {
                1,
                5,
                6,
            };
        int[] f = {1,
            2 // violation 'Array should contain trailing comma.'
        };
        int[] g = {1,
            2,
        };
        int[][] empty2d = {{}}; // violation 'Array should contain trailing comma.'
        int[][] multiDimensionalArray = {
            {1, 2}, // violation 'Array should contain trailing comma.'
            {1,},
            {3, 2, 1,} // violation 'Array should contain trailing comma.'
        };
    }
}
