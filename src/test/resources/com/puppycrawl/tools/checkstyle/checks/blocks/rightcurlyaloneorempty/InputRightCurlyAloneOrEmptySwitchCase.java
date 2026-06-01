/*
RightCurlyAloneOrEmpty
tokens = LITERAL_CASE, LITERAL_SWITCH

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
    }

    public static void test7() {
        int mode = 0;
        switch (mode) {case 0:{int x = 1;}
            break; default : break; } // violation '}' at column 37 should be alone on a line'
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
    }

    public static void test9() {
        int mode = 0;
        switch (mode) {}
    }
}
