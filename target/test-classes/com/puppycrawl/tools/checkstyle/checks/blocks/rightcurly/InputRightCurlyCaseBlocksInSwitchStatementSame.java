/*
RightCurly
option = SAME
tokens = LITERAL_CASE

*/
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyCaseBlocksInSwitchStatementSame {
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
            default: {
                break;
            }
        }
    }

    public static void test() {
        int mode = 0;
        switch (mode) {
            case 1:{
                int x

            =1;} default :          // violation '}' at column 16 should have line break before'
                int x = 0;
        }
    }

    public static void test1() {
        int k = 0;
        switch (k) {
            case 1:{
                 int x = 1;
                 break;
            } case 2:        // violation '}' at column 13 should be alone on a line'
                 int x = 2;
                 break;
        }
    }

    public static void test2() {
         int k = 0;
         switch (k) {
            case 1:{
                 int x = 1;
                 break;
            }
            case 2:
                 int x = 2;
                 break;
         }
    }

    public static void test3() {
         int k = 0;
         switch (k) {
            case 1:{
                 int x = 1;
                 break;
            }
            case 2:{
                    int x = 2;
                    break;
            } }      // violation '}' at column 13 should be alone on a line'
    }

    public static void test4() {
        int mode = 0;
        switch (mode) { case 0: {int x = 1; break;} default : int x = 5; }
        // violation above '}' at column 51 should be alone on a line'
    }

    public static void test5() {
        int mode = 0;
        switch (mode) { case 0: int x = 1;}
    }

    public static void test6() {
        int mode = 0;
        switch (mode) {
            case 0: {int x = 1; break;}  // ok , single line
            default : break;
        }
    }

    public static void test7() {
        int mode = 0;
        switch (mode) { case 0: {int x = 1;}     // ok, single line
            break; default : break; }
    }

    public static void test8() {
        int mode = 0;
        int x = 0;
        switch (mode) {
            case 0:{
            int y = 1;} // violation '}' at column 23 should have line break before'
            case 1: {x = 1;
            }
            case 2: {x = 1; break;} // ok, single line
            default : x = 5;
        }
    }

}
