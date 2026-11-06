/*
ParenPad
option = (default)nospace
tokens = (default)ANNOTATION, ANNOTATION_FIELD_DEF, CTOR_CALL, CTOR_DEF, DOT, ENUM_CONSTANT_DEF, \
         EXPR, LITERAL_CATCH, LITERAL_DO, LITERAL_FOR, LITERAL_IF, LITERAL_NEW, LITERAL_SWITCH, \
         LITERAL_SYNCHRONIZED, LITERAL_WHILE, METHOD_CALL, METHOD_DEF, QUESTION, \
         RESOURCE_SPECIFICATION, SUPER_CTOR_CALL, LAMBDA, RECORD_DEF, RECORD_PATTERN_DEF, ASSIGN, \
         PLUS_ASSIGN, MINUS_ASSIGN, STAR_ASSIGN, DIV_ASSIGN, MOD_ASSIGN, SR_ASSIGN, BSR_ASSIGN, \
         SL_ASSIGN, BAND_ASSIGN, BXOR_ASSIGN, BOR_ASSIGN, LAND, LOR, BAND, BOR, BXOR, EQUAL, \
         NOT_EQUAL, LT, LE, GT, GE, SL, SR, BSR, PLUS, MINUS, STAR, DIV, MOD, UNARY_PLUS, \
         UNARY_MINUS, LNOT, BNOT, INC, DEC, POST_INC, POST_DEC, INDEX_OP, METHOD_REF, \
         LITERAL_INSTANCEOF, TYPECAST

*/

// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

public class InputParenPadForRecordPattern {

    void test(Object obj) {
        if (obj instanceof ColoredPoint(Point p, String c)) {}
        if (obj instanceof ColoredPoint( Point p, String c)) {}
        // violation above ''(' is followed by whitespace'
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
            // violation above ''(' is followed by whitespace'
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
