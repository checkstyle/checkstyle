package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

public class InputRightCurlySwitchCasesBlocks {

     public static void test0() {
        int mode = 0;
        switch (mode) {
            case 1: {
                int x = 1;
                break;
            }
            case 2: {
                int x = 0;
                break;
            }
        }
    }

    public static void test() {
        int mode = 0;
        switch (mode) {
            case 1:{
                int x = 1;
                break;
            } default :          // warn
                int x = 0;
        }
    }

    public static void test1() {
        int k = 0;
        switch (k) {
            case 1:{
                 int x = 1;}   // warn
            case 2:
                 int x = 2;
                 break;
        }
    }

     public static void test2() {
        int mode = 0;
        switch (mode) {
            case 1: int x = 1;
            case 2: {
                break;
            }
        }
    }

     public static void test3() {
        int k = 0;
        switch (k) {
            case 1:{
                 int x = 1;
            }
            case 2: { int x = 2;}    // warn
        }
    }
}
