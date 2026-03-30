/*
ArrayBracketWhitespace


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketwhitespace;

import java.util.function.IntFunction;

public class InputArrayBracketWhitespaceExceptions {
    int[] arr = new int[5];

    void method() {
        int len = arr[0];
        int v2 = new int[]{1}[0]; // violation ''\]' is not followed by whitespace.'
        method2(arr[0], arr[1]);
        int x = arr[0];
        method3(arr[0]);
        int[][] matrix = new int[5][5];
        int val = matrix[0][1];
        arr[0]++;
        arr[0]--;
        int[] init = new int[]{arr[0]}; // violation ''\]' is not followed by whitespace.'
        arr[0] = 1;
        arr[0]= 1; // violation ''\]' is not followed by whitespace.'
        arr[0] += 1;
        arr[0]+= 1; // violation ''\]' is not followed by whitespace.'
        arr[0] -= 1;
        arr[0]-= 1; // violation ''\]' is not followed by whitespace.'
        arr[0] *= 2;
        arr[0]*= 2; // violation ''\]' is not followed by whitespace.'
        arr[0] ++; // violation ''\]' is followed by whitespace.'
        arr[0] --; // violation ''\]' is followed by whitespace.'
        x = arr[0]
            ;
        boolean[] bArr = {true, false};
        int y = bArr[0] ? 1 : 0;
        int z = bArr[0]? 1 : 0; // violation ''\]' is not followed by whitespace.'
        int w = arr[0] == 0 ? 0 : arr[1];
        int v = arr[0] == 0 ? 0 :arr[1];
        int label = arr[0] == 0 ? 0 : arr[1];
        boolean flag1 = arr[0]> 0; // violation ''\]' is not followed by whitespace.'
        boolean flag2 = arr[0] > 0;
        java.util.List<int[]> list1 = null;
        java.util.List<int[] > list2 = null; // violation ''\]' is followed by whitespace.'
        int o = matrix[arr[0]][1];
        int p = matrix[arr[0] ][1];
        // 2 violations above:
        //  ''\]' is followed by whitespace.'
        //  ''\]' is preceded with whitespace.'
        IntFunction<int[]> a1 = int[]::new;
        IntFunction<int[]> a2 = int[] ::new; // violation ''\]' is followed by whitespace.'
        int ternary = true ? arr[0]: 1; // violation ''\]' is not followed by whitespace.'
        switch (arr[0]) {
            case 1: break;
        }
    }

    void method2(int a, int b) { }

    void method3(int a) { }

    public int[]
        getArray1() {
        return null;
    }

    public int[]
        getArray2() {
        return null;
    }

    void coverage() {
        int
[] a = new int
[
5
];
        int[] b = new int[5] ; // violation ''\]' is followed by whitespace.'
        int[] c = new int[1];
        int d = c[0]+1; // violation ''\]' is not followed by whitespace.'
        int e = c[0]-1; // violation ''\]' is not followed by whitespace.'
        int f = true ? c[0]: 1; // violation ''\]' is not followed by whitespace.'
        int g = c
        [0]+
        // 2 violations above:
        //  ''\[' is preceded with whitespace.'
        //  ''\]' is not followed by whitespace.'
        1;
        int[] h = new int[1] ; // violation ''\]' is followed by whitespace.'
        int[] i = new int[1];
        i[0]++;
        i[0]--;
        int k = i[0] + 1;
        int l = i[0]  ; // violation ''\]' is followed by whitespace.'
        int m = i[0];
        method2(i[0], i[0]);
        java.lang.Integer[] j = new java.lang.Integer[1];
        int hc = j[0].hashCode();
        int hc2 = j[0] .hashCode(); // violation ''\]' is followed by whitespace.'
        int n = i[0]+1; // violation ''\]' is not followed by whitespace.'
        int o = i[0]*+1; // violation ''\]' is not followed by whitespace.'
        int p = i[0]-1; // violation ''\]' is not followed by whitespace.'
        int q = i[0]*-1; // violation ''\]' is not followed by whitespace.'
        int r = true ? i[0]:1; // violation ''\]' is not followed by whitespace.'
        boolean flag = i[0]==0; // violation ''\]' is not followed by whitespace.'
        int[] pos = new int[2];
        V v = new V();
        Object s = new Rect(pos[0], pos[1], pos[0]+v.getWidth(), pos[1]+v.getHeight());
        // 2 violations above:
        //  ''\]' is not followed by whitespace.'
        //  ''\]' is not followed by whitespace.'
    }
    static class Rect { Rect(int a, int b, int c, int d) {} }
    static class V { int getWidth() { return 1; } int getHeight() { return 1; } }
}
