/*
RedundantModifier
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE
jdkVersion = 8


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public class InputRedundantModifierFinalUnnamedVariablesWithOldVersion {
    void m(Object o) {
        if (o instanceof final String _) { }
        if (o instanceof final String __) { }
        if (o instanceof final String s)
        if (o instanceof final String _s) { }

        final int _ = sideEffect();
        final int __ = sideEffect();
        final int x = sideEffect();
        final int _x = sideEffect();

        switch (o) {
            case Point(final int a, final int _) when a == 0 -> { }
            case Point(final int _, final int _) -> { }
            default -> { }
        }
    }

    int sideEffect() {
        return 0;
    }

    record Point(int x, int y) { }
}
