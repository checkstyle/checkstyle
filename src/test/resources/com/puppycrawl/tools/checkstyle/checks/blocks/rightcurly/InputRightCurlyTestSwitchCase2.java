/*
RightCurly
option = ALONE_OR_SINGLELINE
tokens = LITERAL_SWITCH, LITERAL_IF, LITERAL_TRY, LITERAL_CATCH


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestSwitchCase2 {

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
    }

    public static void method8() {
        int mode = 0;
        switch (mode) { case 0: int x = 1; break; case 80: x = 1; break; }
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
        int num = 5;
        try {
            switch (num) {
                case 1:
                try {
                        System.out.println("Number is 1");
                        break;
                }
                catch (Exception ignored) {}
            }
        } catch (IllegalArgumentException e) { // violation 'at column 9 should be alone on a line'
            System.err.println(e.getMessage());
        }
    }
}
