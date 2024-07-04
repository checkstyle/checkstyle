/*
IllegalIdentifierName
format = (default)(?i)^(?!(record|yield|var|permits|sealed)$).+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_CONSTANT_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, LAMBDA

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.naming.illegalidentifiername;

public class InputIllegalIdentifierNameRecordPattern {

    void m(Object o) {
        if (o instanceof Point(int var, int _)) { } // violation, 'Name 'var' must match *.'
        if (o instanceof ColorPoint(Point(int record, int yield), String sealed)) { }
        // 3 violations above:
        //                    'Name 'record' must match *.'
        //                    'Name 'yield' must match *.'
        //                    'Name 'sealed' must match *.'
    }

    void m2(Object o) {
        switch (o) {
            case Point(int permits, int yield): {} break;
            // 2 violations above:
            //                    'Name 'permits' must match *.'
            //                    'Name 'yield' must match *.'
            case ColorPoint(Point(int permits, int _), String _): {}
            // violation above, 'Name 'permits' must match *.'
            default: {}
        }
    }

    record Point(int x, int y) { }
    record ColorPoint(Point p, String color) { }

}
