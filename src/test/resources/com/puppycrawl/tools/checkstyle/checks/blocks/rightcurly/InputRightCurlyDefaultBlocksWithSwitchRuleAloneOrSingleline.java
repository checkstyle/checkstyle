/*
RightCurly
option = ALONE_OR_SINGLELINE
tokens = LITERAL_DEFAULT

*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyDefaultBlocksWithSwitchRuleAloneOrSingleline {

    public void test0() {
        int mode = 0;
        switch (mode) {
            case 1 -> {int x = 1;}
            case 2 -> {int x = 0;}
            default -> {System.out.println("default");}
        }
    }

    public static void test1() {
        int mode = 0;
        switch (mode) {
            case 1 -> {
                int
            x = 1;}
            default -> {
                int x = 0;
            }
            case 3 -> System.out.println("x is 3");
        }
    }

    public static void test2() {
        int mode = 0;
        switch (mode) {
            case 1 -> {
                int x = 1;

            }
            default -> {
                int x = 0;
            }
        }
    }

    public static void test3() {
        int k = 0;
        switch (k) {
            case 1 -> {


           int x = 1; }
            default -> {
                int x = 2;}     // violation '}' at column 27 should be alone on a line'

        }
    }

    public static void test4() {
         int k = 0;
         switch (k) {
            case 1 -> {int x = 1;

            }
            default -> {
                int x = 2;
            } }      // violation '}' at column 13 should be alone on a line'
    }

    public static void test5() {
        int mode = 0;
        switch (mode) {
            case 1 -> {
                 int x = 1;
            }
            default -> {
                    int x = 2;
            } }      // violation '}' at column 13 should be alone on a line'
    }

    public static void test6() {
        int mode = 0;
        switch (mode) { case 0 -> {int x = 1;} default -> {int x = 0;} }
        // violation above '}' at column 70 should be alone on a line'
    }

    public static void test7() {
        int mode = 0;
        switch (mode) {
            case 0 -> {
                int x = 1;
            }
            default -> {int x = 2;}
        }
    }

    public static void test8() {
        int mode = 0;
        int x = 0;
        switch (mode) {
            case 0 -> throw new RuntimeException();
            case 1 -> x = 1;
            default -> {x = 1; }
        }
    }

    public static void test9() {
        int mode = 0;
        switch (mode) {
            case 1 -> {
                int x = 1;

            } default -> {
                int x = 0;
            }
        }
    }
}
