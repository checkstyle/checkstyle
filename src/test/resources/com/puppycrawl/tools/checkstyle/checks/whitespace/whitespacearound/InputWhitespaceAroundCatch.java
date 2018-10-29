package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

public class InputWhitespaceAroundCatch {
    public int case1(int i) {
        int k = 1;
        try {
            k = 5 / i;
        } catch (ArithmeticException ex) {}
        return k;
    }

    public int case2(int i) {
        int k = 1;
        try {
            k = 10 / i;
        } catch (ArithmeticException ex) {   }
        return k;
    }

    public int case3(int i) {
        int k = 1;
        try {
            k = 15 / i;
        } catch (ArithmeticException ex) {

        }
        return k;
    }

    public int case4(int i) {
        int k = 1;
        try {
            k = 20 / i;
        } catch (ArithmeticException ex) {
            // This is expected
        }
        return k;
    }
}
