/*
SuppressWarnings
format = (default)^\s*+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF


*/

//non-compiled with eclipse: The value for annotation attribute must be a constant expression
package com.puppycrawl.tools.checkstyle.checks.suppresswarningsholder;
public class InputSuppressWarningsHolderNonConstant {
    static final String UNUSED = "unused";

    @SuppressWarnings(UNUSED)
    int a;
    @SuppressWarnings(InputSuppressWarningsHolderNonConstant.UNUSED)
    int b;
    @SuppressWarnings(InputSuppressWarningsHolderNonConstant.UNUSED)
    int c;
    @SuppressWarnings(value = UNUSED)
    int d;
    @SuppressWarnings(value = InputSuppressWarningsHolderNonConstant.UNUSED)
    int e;
    @SuppressWarnings(value = InputSuppressWarningsHolderNonConstant.UNUSED)
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
    @SuppressWarnings({"unused", true ? "unused" : ""})
    int l;
}

class CustomSuppressWarnings {
    @SuppressWarnings
    private @interface SuppressWarnings {
    }
}
