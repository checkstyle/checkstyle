/*
RightCurly
option = ALONE_OR_SINGLELINE
tokens = LITERAL_DEFAULT, LITERAL_IF

*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyDefaultBlocksWithSwitchExpressionAloneOrSingleline {

    int foo(int yield) {
        return switch (yield) {
            case 1 -> 2;
            case 2 -> 3;
            case 3 -> 4;
            default -> 5;
        };
    }

    void test() {
        int x = 1;
        String s = switch (x) {
            case 1 -> {

                yield "and that's it";
            }  case 2 -> "x is 2";
            default -> "x is neither 1 nor 2";
        };
    }

    void test2() {
        int x = 1;
        String s = switch (x) {
            case 1 -> {

                yield "and that's it";
            }  case 2 -> { yield "x is 2";}
            default -> "x is neither 1 nor 2";
        };
    }

    void test3() {
        int x = 1;
         String s = switch (x) {
            case 1 -> {

                String s1 = "and that's it";
                yield s1;
            } case 2 -> { yield "x is 2";}
            default -> "x is neither 1 nor 2";
        };
    }

    void test4() throws Exception {
           int a = 0;
           int b = switch (a) {
                case 0 -> {
                    int x = 5;
                    int y = 6;
                    if (a == 2) {
                        y = 7;}   // violation '}' at column 31 should be alone on a line'
                    throw new Exception();
                }
                default -> 2;
            };
    }

    void test5() throws Exception {
        int a = 0;
        int b = switch (a) {
            case 0 -> {throw new Exception();}
            default -> 2;
        };
    }

    void test6() {
         int y = 0;
         int x = 0;
         final boolean a = switch (y) {
             case 1 -> {x = 1; yield true;
             }case 2 -> {
                 x = 1;
                 yield true;} case 3 -> {
                 x = 1;
                 if (x == 1) {yield true;}
                 yield false;
             }
             default -> throw new RuntimeException();
         };
    }

    void test7() throws Exception {
         int y = 0;
         int x = 0;
         final boolean a = switch (y) {
             case 1 -> {x = 1; yield true;}
             default -> {
                 x = 1;
                 yield true;
             }
         };
    }

    void test8() throws Exception {
         int y = 0;
         int x = 0;
         final String a = switch (y) {
             case 1 -> "one";
             default -> {
                 x = 1;
                 if (x == 1) {
                     yield "yes";}  // violation '}' at column 34 should be alone on a line'
                 yield "no";
             }
         };
    }
}
