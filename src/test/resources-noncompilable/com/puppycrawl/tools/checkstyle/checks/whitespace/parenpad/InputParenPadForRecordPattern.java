/*
ParenPad
option = (default)nospace

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

public class InputParenPadForRecordPattern {

    void test(Object obj) {
        if (obj instanceof ColoredPoint(Point p, String c)) {}
        if (obj instanceof ColoredPoint( Point p, String c)) {}
        // violation above, ''(' is followed by whitespace'
        if (obj instanceof ColoredPoint( Point p, String c )) {}
        // 2 violations above:
        //             ''(' is followed by whitespace'
        //             '')' is preceded with whitespace'

        if (obj instanceof ColoredPoint(Point(int x, int _), String c)) {}
        if (obj instanceof ColoredPoint( Point( int x, int _), String c)) {}
        // 2 violations above:
        //             ''(' is followed by whitespace'
        //             ''(' is followed by whitespace'
        if (obj instanceof ColoredPoint(Point( int x, int _ ), String c )) {}
        // 3 violations above:
        //             ''(' is followed by whitespace'
        //             '')' is preceded with whitespace'
        //             '')' is preceded with whitespace'
        if (obj instanceof ColoredPoint( Point( int x, int _ ), String c )) {}
        // 4 violations above:
        //             ''(' is followed by whitespace'
        //             ''(' is followed by whitespace'
        //             '')' is preceded with whitespace'
        //             '')' is preceded with whitespace'

    }
    void test2(Object obj) {
        switch (obj) {
            case ColoredPoint(Point p, String c) -> {}
            case Point( int x, int y) when x == 0 -> {}
            // violation above, ''(' is followed by whitespace'
            case Point( int x, int y ) -> {}
            // 2 violations above:
            //             ''(' is followed by whitespace'
            //             '')' is preceded with whitespace'
            default -> {}
        }
        switch (obj) {
            case ColoredPoint(Point(int x, int _), String c) when x == 0-> {}
            case ColoredPoint( Point( int x, int _), String c) when c.isEmpty() -> {}
            // 2 violations above:
            //             ''(' is followed by whitespace'
            //             ''(' is followed by whitespace'
            case ColoredPoint(Point( int x, int _ ), String c ) when x == 0 -> {}
            // 3 violations above:
            //             ''(' is followed by whitespace'
            //             '')' is preceded with whitespace'
            //             '')' is preceded with whitespace'
            case ColoredPoint( Point( int x, int _ ), String c ) when c.isEmpty() -> {}
            // 4 violations above:
            //             ''(' is followed by whitespace'
            //             ''(' is followed by whitespace'
            //             '')' is preceded with whitespace'
            //             '')' is preceded with whitespace'
            default -> {}
        }
    }

    record ColoredPoint(Point p, String color) {}
    record Point(int x, int y) {}
}
