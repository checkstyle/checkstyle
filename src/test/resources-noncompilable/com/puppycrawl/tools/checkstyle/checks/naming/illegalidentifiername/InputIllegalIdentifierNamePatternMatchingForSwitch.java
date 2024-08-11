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

   // violation below, 'Name 'when' must match pattern*.'
    void when(Object o) {
        switch (o) {
            // violation below, 'Name 'when' must match pattern*.'
            case Integer when when when >= 0 -> {}
            // violation below, 'Name 'when' must match pattern*.'
            case Time(int when) when when >= 0 -> {}
            default -> { }
        }
        // violation below, 'Name 'when' must match pattern*.'
        if (o instanceof String when) { }
        // violation below, 'Name 'when' must match pattern*.'
        int when = 0;
    }

    // violation below, 'Name 'when' must match pattern*.'
    record Time(int when) { }
}
