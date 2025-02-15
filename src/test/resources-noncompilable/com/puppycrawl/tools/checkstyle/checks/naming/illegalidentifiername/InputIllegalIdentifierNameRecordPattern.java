/*
IllegalIdentifierName
format = (default)^(?!var$|.*\\$).+
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_CONSTANT_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, LAMBDA

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.naming.illegalidentifiername;

public class InputIllegalIdentifierNameRecordPattern {

    void m(Object o) {
        if (o instanceof Point(int var, int _)) { } // violation, 'Name 'var' must match pattern'
        if (o instanceof ColorPoint(Point(int record, int yield), String sealed)) { }
    }

    void m2(Object o) {
        switch (o) {
            case Point(int permits, int yield): {} break;
            case ColorPoint(Point(int permit$, int _), String _): {}
            // violation above, 'must match pattern'
            default: {}
        }
    }

    record Point(int x, int y) { }
    record ColorPoint(Point p, String color) { }

}
