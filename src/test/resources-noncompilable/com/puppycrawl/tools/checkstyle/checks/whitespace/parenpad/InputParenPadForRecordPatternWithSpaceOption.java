/*
ParenPad
option = space
tokens = (default)ANNOTATION, ANNOTATION_FIELD_DEF, CTOR_CALL, CTOR_DEF, DOT, ENUM_CONSTANT_DEF, \
         EXPR, LITERAL_CATCH, LITERAL_DO, LITERAL_FOR, LITERAL_IF, LITERAL_NEW, \
         LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_WHILE, METHOD_CALL, METHOD_DEF, QUESTION, \
         RESOURCE_SPECIFICATION, SUPER_CTOR_CALL, LAMBDA, RECORD_DEF

*/

// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

public class InputParenPadForRecordPatternWithSpaceOption {

    void test(Object obj) {
        // 2 violations above:
        //             ''(' is not followed by whitespace'
        //             '')' is not preceded with whitespace'
        if (obj instanceof ColoredPoint(Point p, String c)) {}
            // 2 violations above:
            //             ''(' is not followed by whitespace'
            //             '')' is not preceded with whitespace'
        if (obj instanceof ColoredPoint( Point p, String c)) {}
            // 2 violations above:
            //             ''(' is not followed by whitespace'
            //             '')' is not preceded with whitespace'
        if (obj instanceof ColoredPoint( Point p, String c )) {}
            // 2 violations above:
            //             ''(' is not followed by whitespace'
            //             '')' is not preceded with whitespace'


        if (obj instanceof ColoredPoint(Point(int x, int _), String c)) {}
            // 2 violations above:
            //             ''(' is not followed by whitespace'
            //             '')' is not preceded with whitespace'
        if (obj instanceof ColoredPoint( Point( int x, int _), String c)) {}
            // 2 violations above:
            //             ''(' is not followed by whitespace'
            //             '')' is not preceded with whitespace'
        if (obj instanceof ColoredPoint(Point( int x, int _ ), String c )) {}
            // 2 violations above:
            //             ''(' is not followed by whitespace'
            //             '')' is not preceded with whitespace'
        if (obj instanceof ColoredPoint( Point( int x, int _ ), String c )) {}
            // 2 violations above:
            //             ''(' is not followed by whitespace'
            //             '')' is not preceded with whitespace'

    }

    void test2(Object obj) {
        // 2 violations above:
        //             ''(' is not followed by whitespace'
        //             '')' is not preceded with whitespace'
        switch (obj) {
            // 2 violations above:
            //             ''(' is not followed by whitespace'
            //             '')' is not preceded with whitespace'
            case ColoredPoint(Point p, String c) -> {}
            case Point( int x, int y) when x == 0 -> {}
            case Point( int x, int y ) -> {}
            default -> {}
        }
    }


    record ColoredPoint(Point p, String color) {}
        // 2 violations above:
        //             ''(' is not followed by whitespace'
        //             '')' is not preceded with whitespace'
    record Point(int x, int y) {}
        // 2 violations above:
        //             ''(' is not followed by whitespace'
        //             '')' is not preceded with whitespace'
}
