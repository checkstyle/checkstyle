/*
MethodParamPad
allowLineBreaks = true
option = (default)nospace
tokens = (default)CTOR_DEF, LITERAL_NEW, METHOD_CALL, METHOD_DEF, SUPER_CTOR_CALL, \
         ENUM_CONSTANT_DEF, RECORD_DEF, RECORD_PATTERN_DEF


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.whitespace.methodparampad;

public class InputMethodParamPadCheckRecordPattern {

    void test(Object obj) {

        // violation below, ''(' is preceded with whitespace'
        if (obj instanceof ColoredPoint (Point p, String s)) {
        }

        if (obj instanceof ColoredPoint  (Point  ( int x, int y), String s)) {}
        // 2 violations above:
        //              ''(' is preceded with whitespace'
        //              ''(' is preceded with whitespace'

         if (obj instanceof ColoredPoint(Point(int x, int y), _)) {
        }

        switch (obj) {
            case ColoredPoint (Point  ( int x, int y), String s) -> {}
            // 2 violations above:
            //              ''(' is preceded with whitespace'
            //              ''(' is preceded with whitespace'
            // violation below, ''(' is preceded with whitespace'
            case ColoredPoint (Point p, String s) -> {}
            case Point(int x, int y) -> {}
            default -> {}
        }

        boolean b = obj instanceof ColoredPoint
                (Point p, String s); // ok, because allow line breaks

        boolean c = obj instanceof ColoredPoint
                (Point   // ok, because allow line breaks
                         (int x, int y), // ok, because allow line breaks
                 String s);

        boolean cc = obj instanceof ColoredPoint
                (  // ok, because allow line breaks
                    Point
                     ( // ok, because allow line breaks
                         int x, int y),
                 String s);

        if (obj instanceof ColoredPoint( Point( int x, int y ), _)) {}
        if (obj instanceof ColoredPoint ( Point ( int x, int y), _)) {}
         // 2 violations above:
         //              ''(' is preceded with whitespace'
         //              ''(' is preceded with whitespace'
    }

    record ColoredPoint(Point p, String s) {}
    record Point(int x, int y) {}
}
