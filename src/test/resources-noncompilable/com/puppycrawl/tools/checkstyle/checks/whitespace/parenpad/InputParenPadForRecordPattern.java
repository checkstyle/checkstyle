/*
ParenPad
option = (default)nospace
tokens = (default)ANNOTATION, ANNOTATION_FIELD_DEF, CTOR_CALL, CTOR_DEF, DOT, \
         ENUM_CONSTANT_DEF, EXPR, LITERAL_CATCH, LITERAL_DO, LITERAL_FOR, LITERAL_IF, \
         LITERAL_NEW, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_WHILE, METHOD_CALL, \
         METHOD_DEF, QUESTION, RESOURCE_SPECIFICATION, SUPER_CTOR_CALL, LAMBDA, RECORD_DEF

*/

// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

public class InputParenPadForRecordPattern {

    void test(Object obj) {
        if (obj instanceof ColoredPoint(Point p, String c)) {}
        if (obj instanceof ColoredPoint( Point p, String c)) {}
        if (obj instanceof ColoredPoint( Point p, String c )) {}

        if (obj instanceof ColoredPoint(Point(int x, int _), String c)) {}
        if (obj instanceof ColoredPoint( Point( int x, int _), String c)) {}
        if (obj instanceof ColoredPoint(Point( int x, int _ ), String c )) {}
        if (obj instanceof ColoredPoint( Point( int x, int _ ), String c )) {}

    }
    void test2(Object obj) {
        switch (obj) {
            case ColoredPoint(Point p, String c) -> {}
            case Point( int x, int y) when x == 0 -> {}
            case Point( int x, int y ) -> {}
            default -> {}
        }
        switch (obj) {
            case ColoredPoint(Point(int x, int _), String c) when x == 0-> {}
            case ColoredPoint( Point( int x, int _), String c) when c.isEmpty() -> {}
            case ColoredPoint(Point( int x, int _ ), String c ) when x == 0 -> {}
            case ColoredPoint( Point( int x, int _ ), String c ) when c.isEmpty() -> {}
            default -> {}
        }
    }
    void test3(Object obj) {
        if ( obj instanceof ColoredPoint(Point p, String c)) {}
        // violation above ''(' is followed by whitespace'
        switch ( obj ) {
            // 2 violations above:
            //             ''(' is followed by whitespace'
            //             '')' is preceded with whitespace'
            case Point( int x, int y) when ( x == 0 ) -> {}
            // 2 violations above:
            //             ''(' is followed by whitespace'
            //             '')' is preceded with whitespace'
            case ColoredPoint(Point p, String c) -> {
                System.out.println( p );
                // 2 violations above:
                //             ''(' is followed by whitespace'
                //             '')' is preceded with whitespace'
            }
            default -> {}
        }
    }

    record ColoredPoint(Point p, String color) {}
    record Point(int x, int y) {}
}
