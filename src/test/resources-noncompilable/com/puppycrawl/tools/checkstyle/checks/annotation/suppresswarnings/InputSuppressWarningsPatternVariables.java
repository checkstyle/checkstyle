/*
SuppressWarnings
format = ^unchecked$|^unused$|^$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF, PATTERN_VARIABLE_DEF


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

public class InputSuppressWarningsPatternVariables {

    void test(Object o) {
        // violation below, 'The warning '' cannot be suppressed at this location.'
        if (o instanceof ColoredPoint(@SuppressWarnings("")int x, int y, int z)) { }

        // violation below, 'The warning 'unused' cannot be suppressed at this location.'
        if (o instanceof ColoredPoint(_, _,@SuppressWarnings("unused") int _)) { }

        // violation below, 'The warning 'unchecked' cannot be suppressed at this location.'
        if (o instanceof @SuppressWarnings("unchecked") ColoredPoint _) { }

        // violation below, 'The warning '' cannot be suppressed at this location.'
        @SuppressWarnings("")
        int _ = sideEffect();

        if (o instanceof @SuppressWarnings("a unused") ColoredPoint _) { }

        @SuppressWarnings("foo")
        int _ = sideEffect();
    }

    int sideEffect() {
        return 0;
    }
    record ColoredPoint(int x, int y, int z) { }
}
