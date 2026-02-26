/*
RightCurly
option = ALONE
tokens = LITERAL_DEFAULT

*/
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyDefaultBlocksInSwitchStatementAlone {
    public static void test0() {
        int mode = 0;
        switch (mode) {
            case 1: {
                int x = 1;
                break;
            }
            default: {
                int x = 0;
                break;
            }
        }
    }

    public static void test1() {
        int mode = 0;
        switch (mode) {
            case 1:{
                int x = 1;
                break;
            }
            default:{
                int x = 0;
            } case 2 :          // violation '}' at column 13 should be alone on a line'
                int x = 0;
        }
    }

    public static void test2() {
        int k = 0;
        switch (k) {
            default:{
                 int x = 2;
                 break;
            } case 1:        // violation '}' at column 13 should be alone on a line'
                 int x = 2;
                 break;
        }
    }

    public static void test3() {
         int k = 0;
         switch (k) {
            default:{
                 int x = 1;
                 break;
            }
            case 1:
                 int x = 2;
                 break;
         }
    }

    public static void test4() {
         int k = 0;
         switch (k) {
            case 1:{
                 int x = 1;
                 break;
            }
            default:{
                    int x = 2;
                    break;
            } }      // violation '}' at column 13 should be alone on a line'
    }

    public static void test5() {
        int mode = 0;
        switch (mode) { case 0: int x = 1; break; default : {x = 5;} }
        // violation above '}' at column 68 should be alone on a line'
    }

    public static void test6() {
        int mode = 0;
        switch (mode) { default: }
    }

    public static void test7() {
        int mode = 0;
        switch (mode) {
            default: {int x = 1; break;}  // violation '}' at column 40 should be alone on a line'
            case 0 : break;
        }
    }

    public static void test8() {
        int mode = 0;
        switch (mode) {default:{int x = 1;} // violation '}' at column 43 should be alone on a line'
            break; case 0 : break; }
    }

    public static void test9() {
        int mode = 0;
        int x = 0;
        switch (mode) {
            case 0:
            case 1: x = 1; break;
            default: {x = 1; break;} // violation '}' at column 36 should be alone on a line'
        }
    }
}
