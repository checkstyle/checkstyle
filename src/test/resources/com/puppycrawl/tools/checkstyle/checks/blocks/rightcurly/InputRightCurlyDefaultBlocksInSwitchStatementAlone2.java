/*
RightCurly
option = ALONE
tokens = LITERAL_DEFAULT

*/
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyDefaultBlocksInSwitchStatementAlone2 {
     public static void test10() {
        int mode = 0;
        switch (mode) {
            case 0: int x = 1;
            case 1: x = 1; break;
            default: {
                x =
            1;} case 2 : x = 5;         // violation '}' at column 15 should be alone on a line'
        }
    }

    public static void test11() {
        int mode = 0;
        switch (mode) {
            case 0: {
                int x = 0;
            }
            default: {

            } case 1: int x = 1; break;    // violation '}' at column 13 should be alone on a line'
        }
    }

    public static void test12() {
        int mode = 0;
        switch (mode) {
            case 0: {
                int x = 0;
            }
            default: {

            int x = 1; } case 1: break;  // violation '}' at column 24 should be alone on a line'
        }
    }

    public static void test13() {
        int mode = 0;
        switch (mode) {
            case 0: { int x = 1;

            }
            default: {

            } case 1:  // violation '}' at column 13 should be alone on a line'
                break;
        }
    }

    public static void test14() {
        int mode = 0;
        switch (mode) {
            case 0: {

            }
            default: {  }         // violation '}' at column 25 should be alone on a line'
            case 1: {break;}
        }
    }

    public static void test15() {
        int mode = 0; // violation below, '}' at column 35 should be alone on a line'
        switch (mode) {default: { } case 1: {  } }
    }

    public static void test16() {
        int mode = 0;
        switch (mode) {
            default: int x = 1; { } break;
            case 1: { } int y = 1; break;
        }
    }

    public static void test17() {
        int mode = 0;
        switch (mode) {
            case 0:
            int x = 1;
            {  }
            default:
            mode++;
            {

            } int y;
            case 3:
            {

            } int z = 1;
        }
    }

    public static void test18() {
        int mode = 0;
        switch (mode) {
            default: {
            }
            int x;
            case 1:
            int z;
            {

            }break; case 2: break;
        }
    }
}
