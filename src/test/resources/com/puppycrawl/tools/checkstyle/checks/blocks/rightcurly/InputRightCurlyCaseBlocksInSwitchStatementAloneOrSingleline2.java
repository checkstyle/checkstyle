/*
RightCurly
option = ALONE_OR_SINGLELINE
tokens = LITERAL_CASE

*/
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyCaseBlocksInSwitchStatementAloneOrSingleline2 {

    public static void test9() {
        int mode = 0;
        switch (mode) {
            case 0: int x = 1;
            case 1: x = 1; break;
            case 2: {
                x =
            1;} default : x = 5;         // violation '}' at column 15 should be alone on a line'
        }
    }

    public static void test10() {
        int mode = 0;
        switch (mode) {
            case 0: {

            } case 1: int x = 1; break;    // violation '}' at column 13 should be alone on a line'
            case 2: {

            }
            default : x = 5;
        }
    }
     public static void test11() {
        int mode = 0;
        switch (mode) {
            case 0: {

            } case 1: int x = 1; break;   // violation '}' at column 13 should be alone on a line'
            case 2: {
                int
            y;} default : x = 5;         // violation '}' at column 15 should be alone on a line'
        }
    }
    public static void test12() {
        int mode = 0;
        switch (mode) {
            case 0: {

            int x = 1;} case 1: {int x = 1; break;}
            // violation above '}' at column 23 should be alone on a line'
        }
    }
    public static void test13() {
        int mode = 0;
        switch (mode) {
            case 0: {

            }
            case 1: {

            }
            default : break;
        }
    }
     public static void test14() {
        int mode = 0;
        switch (mode) {
            case 0: { int x = 1;

            } case 1: {  }         // violation '}' at column 13 should be alone on a line'
            default : {break;}
        }
    }

     public static void test15() {
        int mode = 0;
        switch (mode) {
            case 0: int x = 1; { } break;
            case 1: { } int y = 1; break; // violation '}' at column 23 should be alone on a line'

            case 2: int t = 1; { };
        }
    }

    public static void test17() {
        int mode = 0;
        switch (mode) {
            case 0:
            int x = 1;
            {  }
            case 1:
            mode++;
            {

            } int y; // ok, the braces is not a first child of case
            case 3:
            {

            } int z = 1;  // violation '}' at column 13 should be alone on a line'
        }
    }

    public static void test18() {
        int mode = 0;
        switch (mode) {
            case 0: {

            }
            case 1:
            int z;
            {

            } break; default: break; // ok, the braces is not a first child of case
        }
    }
}
