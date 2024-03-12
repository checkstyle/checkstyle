/*
RightCurly
option = ALONE
tokens = LITERAL_SWITCH, LITERAL_IF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestSwitchCase {

    public static void method0() {
        int mode = 0;
        switch (mode) {
            case 1:
                int x = 1;
                break;
            default :
                x = 0; } // violation '}' at column 24 should be alone on a line'
    }

    public static void method1() {
        int mode = 0;
        switch (mode) {
        default :
               int x = 0; } // violation '}' at column 27 should be alone on a line'
    }

    public static void method2() {
        int mode = 0;
        switch (mode) {
            case 1:
                int x = 1;
                break;
            default:
                x = 0;
        }
    }

    public static void method3() {
        int mode = 0;
        switch (mode) {
        default :
               int x = 0;
        }
    }

    public static void method4() {
        int mode = 0;
        switch (mode) { default : int x = 0; }
        // violation above '}' at column 46 should be alone on a line'
    }

    public static void method5() {
        int mode = 0;
        switch (mode) { default : int x = 0;
        }
    }

    public static void method6() {
        int mode = 0;
        switch (mode) { case 0: int x = 1; break; default : x = 5;
        }
    }

    public static void method7() {
        int mode = 0;
        switch (mode) { case 0: int x = 1; break; default : x = 5; }
        // violation above '}' at column 68 should be alone on a line'
    }

    public static void method8() {
        int mode = 0;
        switch (mode) { case 0: int x = 1; break; case 80: x = 1; break; }
        // violation above '}' at column 74 should be alone on a line'
    }

    public static void method9() {
        int mode = 0;
        switch (mode) { case 0: int x = 1; break; default : x = 5;
        }
    }

    public static void method10() {
        int mode = 0;
        switch (mode) { case 0: int x = 1; break; case 80: x = 1; break;
        }
    }

    public static void method11() {
        int mode = 0;
        int x = 0;
        switch (mode) {
            case 0:
                if(0>9) {
                    x = 9;
                }
                break;
            case 80:
                x = 1;
                break;
        }
    }
    public static void method12() {
        int mode = 0;
        int x = 0;
        switch (mode) {
            case 0:
                if(0>9) {
                    x = 9;
                } else { // violation ''}' at column 17 should be alone on a line'
                }
                break;
        }
        switch (x) {}; // violation ''}' at column 21 should be alone on a line'
        switch (x) {
        }
    }
}
