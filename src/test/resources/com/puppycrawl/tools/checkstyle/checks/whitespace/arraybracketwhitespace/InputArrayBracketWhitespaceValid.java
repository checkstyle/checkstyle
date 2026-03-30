/*
ArrayBracketWhitespace


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.arraybracketwhitespace;

public class InputArrayBracketWhitespaceValid {
    int[] arr1;
    int[][] arr2;
    int[] arr3 = new int[10];

    void method(int[] p) {
        int i = p[0];
        p[0]++;
        p[0]--;
        p[0] = 1;
        p[0] += 1;
        int len = p.length;
        char[] c = new char[] {'a', 'b'};
        int var = p[0];
    }

    void method2() {
        int
[]
        arr4 = new int[
                5];
        int m = arr4[0
]
;
        int j = arr4[0]
        ;
        int k = arr4[0] +
        1;
        int l = arr4[0] - 1;
        arr4[0]--;
    }

    void method3() {
        int[] arr = new int[5];
        java.util.List<int[]> list1 = null;
        int[][] matrix2 = new int[5][5];
        int[] o = matrix2[arr[0]];
        boolean[] bArr = {true, false};
        int z = bArr[0] ? 1 : 0;
    }
}
