/*
IllegalIdentifierName
format = (default)^(?!var$|\\S*\\$)\\S+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_CONSTANT_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, LAMBDA

*/

// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.naming.illegalidentifiername;

public class InputIllegalIdentifierNameRecordPattern {

    void m(Object o) {
        // violation below 'Name 'var' must match pattern'
        if (o instanceof Point(int var, int _)) { }
        if (o instanceof ColorPoint(Point(int record, int yield), String sealed)) { }
    }

    void m2(Object o) {
        switch (o) {
            case Point(int permits, int yield): {} break;
            // violation below 'Name 'permit\$' must match pattern'
            case ColorPoint(Point(int permit$, int _), String _): {}
            default: {}
        }
    }

    record Point(int x, int y) { }
    record ColorPoint(Point p, String color) { }

}
