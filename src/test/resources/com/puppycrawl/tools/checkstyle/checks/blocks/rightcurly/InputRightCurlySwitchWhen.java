/*
RightCurly
option = ALONE_OR_SINGLELINE
tokens = LITERAL_CASE

*/

// Java21
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

record ColoredPoint(int p, int x, String c) { }
record Rectangle(ColoredPoint upperLeft, ColoredPoint lowerRight) { }

public class InputRightCurlySwitchWhen {

    public void testSwitchRuleWhenGuard() {
        Object obj = new Object();
        switch (obj) {
            case ColoredPoint(int x, _, _) when (x >= 2) -> { int y = 0; } // ok, single line
            case ColoredPoint(int x, _, _) when (x == 1) -> {
                int y = 1;
            }
            case ColoredPoint(int x, _, _) when (x == 0) -> {
                int
                y = 2;} // violation '}' at column 23 should be alone on a line'
            default -> { }
        }
    }

    public void testSwitchRuleWhenGuard2() {
        Object obj = new Object();
        switch (obj) {
            case ColoredPoint(int x, _, _) when (x >= 5) -> {
                int y = 3;

            } default -> {           // violation '}' at column 13 should be alone on a line'
                int z = 4;
            }
        }
    }

    public void testSwitchRuleWhenGuard3() {
        Object obj = new Object();
        switch (obj) {
            case ColoredPoint(int x, _, _) when (x == 7) -> {


                int a = 1;
            }
            case ColoredPoint(int x, _, _) when (x == 8) -> {
                int b = 2;} // violation '}' at column 27 should be alone on a line'
            default -> { }
        }
    }

    public void testSwitchRuleWhenGuard4() {
        Object obj = new Object();
        switch (obj) {
            case ColoredPoint(int x, _, _) when (x == 9) -> {
                int x1 = 10;
            }
            case ColoredPoint(int x, _, _) when (x == 10) -> {
                int x2 = 20;
            } default -> { } // violation '}' at column 13 should be alone on a line'
        }
    }

    public void testSwitchRuleWhenGuard5() {
        Object obj = new Object();
        switch (obj) {
            case ColoredPoint(int x, _, _) when (x == 11) -> {
                int v = 1;
            } case ColoredPoint(int x, _, _) when (x == 12) -> {int v = 2;}
            // violation above '}' at column 13 should be alone on a line'
            default -> { }
        }
    }
}
