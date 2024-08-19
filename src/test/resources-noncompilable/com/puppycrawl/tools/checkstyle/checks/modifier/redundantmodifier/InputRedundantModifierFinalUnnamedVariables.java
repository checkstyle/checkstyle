/*
RedundantModifier
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE
jdkVersion = (default)21


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public class InputRedundantModifierFinalUnnamedVariables {
    void m(Object o) {
        // violation below, 'Redundant 'final' modifier'
        if (o instanceof final String _) { }
        if (o instanceof final String __) { }
        if (o instanceof final String s)
        if (o instanceof final String _s) { }

        // violation below, 'Redundant 'final' modifier'
        final int _ = sideEffect();
        final int __ = sideEffect();
        final int x = sideEffect();
        final int _x = sideEffect();

    }

    void m2(Object o) {
        switch (o) {
            // violation below, 'Redundant 'final' modifier'
            case final String _ -> { }
            case final Integer __ -> { }
            case final Double s -> { }
            case final Float _s -> { }
            default -> { }
        }
    }

    int sideEffect() {
        return 0;
    }

    record Point(int x, int y) { }
}
