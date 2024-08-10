/*
AnnotationOnSameLine
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.annotation.annotationonsameline;

public class InputAnnotationOnSameLinePatternVariables {
    void test(Object o) {
        if (o instanceof ColoredPoint(@Deprecated
                         int x,
                         @Deprecated
                         int y,
                         @Deprecated
                         String color)) { }
    }

    void test2(Object o) {
        switch (o) {
            case ColoredPoint(@Deprecated
                              int x,
                              @Deprecated
                              int y,
                              @Deprecated
                              String color) -> { }
            default -> {}
        }
    }

    record ColoredPoint(@Deprecated
                        int x,
                        @Deprecated
                        int y,
                        @Deprecated
                        String color) { }
}
