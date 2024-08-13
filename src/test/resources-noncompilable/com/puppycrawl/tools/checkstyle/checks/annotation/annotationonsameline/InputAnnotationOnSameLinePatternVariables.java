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

       if (o instanceof ColoredPoint(@Deprecated
                         int _,
                         @Deprecated
                         int _,
                         @Deprecated
                         String _)) { }

       if (o instanceof @Deprecated
                        String s) { }

       if (o instanceof @Deprecated
                        String _) { }

       @Deprecated int _ = 0;

       // violation below, 'Annotation 'Deprecated' should be on the same line with its target.'
       @Deprecated
       int _ = 0;
    }

    void test2(Object o) {
        switch (o) {
            case ColoredPoint(@Deprecated
                              int x,
                              @Deprecated
                              int y,
                              @Deprecated
                              String color) when x >= 0 -> { }

            case ColoredPoint(@Deprecated
                              int x,
                              @Deprecated
                              int _,
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
