/*
SuppressWarnings
format = (default)^\s*+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.suppresswarningsholder;

public class InputSuppressWarningsHolder {
    static final String UNUSED = "unused";

    @SuppressWarnings(UNUSED)
    int a;
    @SuppressWarnings(InputSuppressWarningsHolder.UNUSED)
    int b;
    @SuppressWarnings(InputSuppressWarningsHolder.UNUSED)
    int c;
    @SuppressWarnings(value = UNUSED)
    int d;
    @SuppressWarnings(value = InputSuppressWarningsHolder.UNUSED)
    int e;
    @SuppressWarnings(value = InputSuppressWarningsHolder.UNUSED)
    int f;
    @SuppressWarnings((1 != 1) ? "" : "unused")
    int g;
    @SuppressWarnings("un" + "used")
    int h;
    @SuppressWarnings((String) "unused")
    int i;
    @SuppressWarnings({})
    int j;
    @SuppressWarnings({UNUSED})
    int k;
}

class CustomSuppressWarnings {
    @SuppressWarnings
    private @interface SuppressWarnings {
    }
}
