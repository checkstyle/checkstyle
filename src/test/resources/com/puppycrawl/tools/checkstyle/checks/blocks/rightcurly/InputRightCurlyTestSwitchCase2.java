/*
RightCurly
option = ALONE_OR_SINGLELINE
tokens = LITERAL_SWITCH


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
        } // ok
    }

    public static void method3() {
        int mode = 0;
        switch (mode) {
        default :
               int x = 0;
        } // ok
    }

    public static void method4() {
        int mode = 0;
        switch (mode) { default : int x = 0; } // ok
    }

    public static void method5() {
        int mode = 0;
        switch (mode) { default : int x = 0;
        } // ok
    }

    public static void method6() {
        int mode = 0;
        switch (mode) { case 0: int x = 1; break; default : x = 5;
        } // ok
    }

    public static void method7() {
        int mode = 0;
        switch (mode) { case 0: int x = 1; break; default : x = 5; } // ok
    }

    public static void method8() {
        int mode = 0;
        switch (mode) { case 0: int x = 1; break; case 80: x = 1; break; } // ok
    }

    public static void method9() {
        int mode = 0;
        switch (mode) { case 0: int x = 1; break; default : x = 5;
        } // ok
    }

    public static void method10() {
        int mode = 0;
        switch (mode) { case 0: int x = 1; break; case 80: x = 1; break;
        } // ok
    }
}
