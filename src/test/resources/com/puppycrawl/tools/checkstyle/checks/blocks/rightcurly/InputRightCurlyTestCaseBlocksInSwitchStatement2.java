/*
RightCurly
option = ALONE
tokens = LITERAL_CASE

*/
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestCaseBlocksInSwitchStatement2 {
     public static void test9() {
        int mode = 0;
        switch (mode) {
            case 0: int x = 1;
            case 1: x = 1; break;
            case 2: {
                x = 1; break;
            } default : x = 5;         // violation '}' at column 13 should be alone on a line'
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

            } default : x = 5;            // violation '}' at column 13 should be alone on a line'
        }
    }
    public static void test12() {
        int mode = 0;
        switch (mode) {
            case 0: {

            } case 1: {int x = 1;} break;  // 2 violations
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
            case 0: {

            } case 1: {  }         // 2 violations
            default : {break;}
        }
    }

    public static void test15() {
        int mode = 0;
        switch (mode) {case 0: { } case 1: {  } } // 2 violations
    }
}
