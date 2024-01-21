package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

public class InputRightCurlySwitchCase {

    public static void method0() {
        int mode = 0;
        switch (mode) {
            case 1:
                int x = 1;
                break;
            default :
                x = 0; } // warn
    }

    public static void method1() {
        int mode = 0;
        switch (mode) {
        default :
               int x = 0; } // warn
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
        switch (mode) {
            case 1 :
                int  y = 2;
            default :
               int x = 0;
        }
    }
}
