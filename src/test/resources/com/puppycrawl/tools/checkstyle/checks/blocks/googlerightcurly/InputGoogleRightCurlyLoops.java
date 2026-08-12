/*
GoogleRightCurly

*/

package com.puppycrawl.tools.checkstyle.checks.blocks.googlerightcurly;

public class InputGoogleRightCurlyLoops {

    private int total;
    private int counter;
    private boolean flag;

    void method() {

        int[] values = {4, 8, 15, 16, 23, 42};

        for (int i = 0; i < values.length; i++) {
            int current = values[i];
            if (current % 2 == 0) {
                total += current;
            } else {
                total -= current;
            }
            counter++; } int j = 0;
        // violation above ''}' at column 24 should be alone on a line'

        while (j < values.length) {
            int squared = values[j] * values[j];
            total += squared;
            j++; } int p = 0;
        // violation above ''}' at column 18 should be alone on a line'

        while (p < values.length) {
            if (values[p] < 0) {
                flag = true;
                break;
            }
            total -= values[p];
            p++; }
        // violation above ''}' at column 18 should be alone on a line'

        int k = 0;
        do {
            total += k * 3;
            k++;
        }
        while (k < values.length);
        // violation 2 lines above ''}' at column 9 should be on the same line as .*/while'

        for (int i = 0; i < 3; i++) {
            for (int n = 0; n < 3; n++) {
                if (n == i) {
                    total += n * i;
                } else {
                    total -= n;
                }
            }
        }
    }
}
