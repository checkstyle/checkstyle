/*
GoogleRightCurly

*/

package com.puppycrawl.tools.checkstyle.checks.blocks.googlerightcurly;

public class InputGoogleRightCurlySwitchNewStyle {

    public void method() {
        int x = 0;
        int mode = 0;

        switch (mode) {
            case 1 -> x = 1;
            case 2 -> {}
            case 3 -> { x = 3; }
            // violation above ''}' at column 32 should be alone on a line'
            default -> x = 0;
        }

        int result = switch (mode) {
            case 1 -> 1;
            case 2 -> { yield 2; }
            // violation above ''}' at column 34 should be alone on a line'
            default -> { yield
                    0; }
            // violation above ''}' at column 24 should be alone on a line'
        };

    }

    void method2() {
        int y = 7;

        switch (y) {
            case 1, 2, 3 -> {y =
                    1 + 2 + 3;}
            // violation above ''}' at column 31 should be alone on a line'
            case 5, 6 -> {
                y = 5 + 6
            ;}
            // violation above ''}' at column 14 should be alone on a line'
            case 8 -> {}
            default -> {
                y++;
            }
        }
    }

    void method3() {
        int mode = 2;
        int result = switch (mode) {
            case 1 -> 1;
            case 2 -> {
                yield 2;
            }
            default -> {
                yield 0;
            }
        };
    }
}
