/*
RightCurlyAloneOrEmpty
tokens = LITERAL_CASE, LITERAL_SWITCH
allowMultiBlock = (default)false

*/
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyaloneorempty;

public class InputRightCurlyAloneOrEmptySwitchCase {
    public static void test0() {
        int mode = 0;
        switch (mode) {
            case 1: {
                int x = 1;
                break;
            }
            case 2:
            default:  {
                int x = 0;
            }break;
            case 3: {
                int x = 3;
            }
            // violation 4 lines above ''}' at column 13 should be alone on a line'
        }
    }

    public static void test() {
        int mode = 0;
        switch (mode) {
            case 1:{
                int x = 1;
                break;
            } default :
                int x = 0;
            // violation 2 lines above ''}' at column 13 should be alone on a line'
        }
    }

    public static void test1() {
        int k = 0;
        switch (k) {
            case 1:{
                 int x = 1;
                 break;
            } case 2:
                 int x = 2;
                 break;
        }
        // violation 4 lines above ''}' at column 13 should be alone on a line'
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
            } }
           // violation above ''}' at column 13 should be alone on a line'
    }

    public static void test5() {
        int mode = 0;
        switch (mode) { case 0: }  // violation '}' at column 33 should be alone on a line'
    }

    public static void test6() {
        int mode = 0;
        switch (mode) {
            case 0: {int x = 1; break;}
            default : break;
        }
        // violation 3 lines above ''}' at column 39 should be alone on a line'
    }

    public static void test7() {
        int mode = 0;
        // violation below ''}' at column 42 should be alone on a line'
        switch (mode) {case 0:{int x = 1;}
            break; default : break; }
        // violation above '}' at column 37 should be alone on a line'
    }

    public static void test8() {
        int mode = 0;
        int x = 0;
        switch (mode) {
            case 0:
            case 1: x = 1; break;
            case 2: {x = 1; break;}
            default : x = 5;
        }
        // violation 3 lines above ''}' at column 35 should be alone on a line'
    }

    public static void test9() {
        int mode = 0;
        switch (mode) {}
    }
}
