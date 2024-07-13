/*
ParenPad
option = space
tokens = RECORD_PATTERN_DEF

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

public class InputParenPadForRecordPatternWithSpaceOption {

    void test(Object obj) {
        if (obj instanceof ColoredPoint(Point p, String c)) {}
        // 2 violations above:
        //             ''(' is not followed by whitespace'
        //             '')' is not preceded with whitespace'
        if (obj instanceof ColoredPoint( Point p, String c)) {}
        // violation above, '')' is not preceded with whitespace'
        if (obj instanceof ColoredPoint( Point p, String c )) {}


        if (obj instanceof ColoredPoint(Point(int x, int _), String c)) {}
        // 4 violations above:
        //             ''(' is not followed by whitespace'
        //             ''(' is not followed by whitespace'
        //             '')' is not preceded with whitespace'
        //             '')' is not preceded with whitespace'
        if (obj instanceof ColoredPoint( Point( int x, int _), String c)) {}
        // 2 violations above:
        //             '')' is not preceded with whitespace'
        //             '')' is not preceded with whitespace'
        if (obj instanceof ColoredPoint(Point( int x, int _ ), String c )) {}
        // violation above, ''(' is not followed by whitespace'
        if (obj instanceof ColoredPoint( Point( int x, int _ ), String c )) {}

    }

    void test2(Object obj) {
        switch (obj) {
            case ColoredPoint(Point p, String c) -> {}
            // 2 violations above:
            //             ''(' is not followed by whitespace'
            //             '')' is not preceded with whitespace'
            case Point( int x, int y) when x == 0 -> {}
            // violation above, '')' is not preceded with whitespace'
            case Point( int x, int y ) -> {}
            default -> {}
        }
    }


    record ColoredPoint(Point p, String color) {}
    record Point(int x, int y) {}
}
