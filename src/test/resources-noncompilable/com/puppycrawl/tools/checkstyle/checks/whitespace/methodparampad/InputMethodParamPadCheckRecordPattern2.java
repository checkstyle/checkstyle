/*
MethodParamPad
allowLineBreaks = (default)false
option = space
tokens = (default)CTOR_DEF, LITERAL_NEW, METHOD_CALL, METHOD_DEF, SUPER_CTOR_CALL, \
         ENUM_CONSTANT_DEF, RECORD_DEF, RECORD_PATTERN_DEF


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.whitespace.methodparampad;

public class InputMethodParamPadCheckRecordPattern2 {

    void test (Object obj) {


        if (obj instanceof ColoredPoint (Point p, String s)) {
        }

        if (obj instanceof ColoredPoint  (Point  ( int x, int y), String s)) {}
        if (obj instanceof ColoredPoint(Point(int x, int y), _)) {}
         // 2 violations above:
         //              ''(' is not preceded with whitespace'
         //              ''(' is not preceded with whitespace'

        switch (obj) {
            case ColoredPoint (Point  ( int x, int y), String s) -> {}
            case ColoredPoint (Point p, String s) -> {}
            case Point(int x, int y) -> {} // violation, ''(' is not preceded with whitespace'
            default -> {}
        }

        boolean b = obj instanceof ColoredPoint
                (Point p, String s); // violation, ''(' should be on the previous line'

        boolean c = obj instanceof ColoredPoint
                (Point // violation, ''(' should be on the previous line'
                         (int x, int y), // violation, ''(' should be on the previous line'
                 String s);

        boolean cc = obj instanceof ColoredPoint
                ( // violation, ''(' should be on the previous line'
                    Point
                     ( // violation, ''(' should be on the previous line'
                         int x, int y),
                 String s);

        if (obj instanceof ColoredPoint( Point( int x, int y ), _)) {}
         // 2 violations above:
         //              ''(' is not preceded with whitespace'
         //              ''(' is not preceded with whitespace'
        if (obj instanceof ColoredPoint ( Point ( int x, int y), _)) {}
    }

    record ColoredPoint (Point p, String s) {}
    record Point (int x, int y) {}
}
