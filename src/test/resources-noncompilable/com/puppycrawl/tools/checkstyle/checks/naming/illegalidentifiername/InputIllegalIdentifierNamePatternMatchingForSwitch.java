/*
IllegalIdentifierName
format = (default)(?i)^(?!(record|yield|var|permits|sealed|when)$).+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_CONSTANT_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, LAMBDA

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.naming.illegalidentifiername;

public class InputIllegalIdentifierNamePatternMatchingForSwitch {

    void m(Object o) {
        if (o instanceof Point(int when, int _)) { }
        // violation above, 'Name 'when' must match *.'
    }

    void m2(Object o) {
        switch (o) {
            case Point(int x, int when): {} break;
            // violation above, 'Name 'when' must match *.'
            case Integer when when when <= 0 : { }
            // violation above, 'Name 'when' must match *.'
            default: {}
        }
        String when = "";
        // violation above, 'Name 'when' must match *.'
    }

    record Point(int x, int y) { }
    record ColorPoint(Point p, String when) { }
    // violation above, 'Name 'when' must match *.'
}
