/*
SuppressWarnings
format = ^unchecked$*|^unused$*|.*
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

public class InputSuppressWarningsPatternVariables {

    void test(Object o) {
        if (o instanceof ColoredPoint(@SuppressWarnings("")int x, int y, int z)) { } // violation

        if (o instanceof ColoredPoint(_, _,@SuppressWarnings("unused") int _)) { } // violation

        if (o instanceof @SuppressWarnings("unchecked") ColoredPoint _) { } // violation

        @SuppressWarnings("") // violation
        int _ = sideEffect();
    }

    int sideEffect() {
        return 0;
    }
    record ColoredPoint(int x, int y, int z) { }
}
