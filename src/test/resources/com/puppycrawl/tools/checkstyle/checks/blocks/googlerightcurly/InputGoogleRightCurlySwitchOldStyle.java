/*
GoogleRightCurly

*/

package com.puppycrawl.tools.checkstyle.checks.blocks.googlerightcurly;

public class InputGoogleRightCurlySwitchOldStyle {
    public static void test1() {
        int mode = 0;
        switch (mode) {
            case 0: int x = 1;
            case 1: x = 1; break;
            case 2: {
                x =
            1;} default : x = 5;
            // violation above ''}' at column 15 should be alone on a line'
        }
    }

    public static void test2() {
        int mode = 0;
        switch (mode) {
            case 0: {
                int b = 0;
            } {
            // violation above ''}' at column 13 should be alone on a line'
               int c = 1;
            } case 1: int x = 1;
            // violation above ''}' at column 13 should be alone on a line'
            break;
            case 2: {
            } default : x = 5;
            // violation above 'Empty block should be concise {}'
        }
    }

    public static void test3() {
        int mode = 0;
        // violation 4 lines below ''}' at column 13 should be alone on a line'
        switch (mode) {
            case 0: { int x = 1;
                int m = 1;
            } case 1: {
                String ans = "";
                int j = 1;
            }
            default :
        }
    }

    public static void test4() {
        int mode = 0;
        switch (mode) {
            case 0: {

            }
            // violation above 'Empty block should be concise {}'
            case 1: {  } // violation 'Empty block should be concise {}'
            default : {break;}
            // violation above ''}' at column 30 should be alone on a line'
        } int a = 1;
        // violation above ''}' at column 9 should be alone on a line'
    }

    public static void test5() {
        int mode = 0;
        switch (mode) {
            case 0: {}
            int x;
            {
            int a = 1;}
            // violation above ''}' at column 23 should be alone on a line'
            case 1:
                int z;
            {

            } break; default: break;
            // violation above 'Empty block should be concise {}'
        }
    }
}
