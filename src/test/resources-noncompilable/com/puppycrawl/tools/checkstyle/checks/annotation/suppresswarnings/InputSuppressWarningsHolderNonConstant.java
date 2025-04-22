/*
SuppressWarnings
format = (default)^\\s*+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF


*/

//non-compiled with eclipse: The value for annotation attribute must be a constant expression
package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;
public class InputSuppressWarningsHolderNonConstant {
    static final String UN_U = "UN_U";

    @SuppressWarnings(UN_U)
    int a;
    @SuppressWarnings(InputSuppressWarningsHolderNonConstant.UN_U)
    int b;
    @SuppressWarnings(
     com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings.CustomSuppressWarnings.UN_U)
    int c;
    @SuppressWarnings(value = UN_U)
    int d;
    @SuppressWarnings(value = InputSuppressWarningsHolderNonConstant.UN_U)
    int e;
    @SuppressWarnings(value =
     com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings.CustomSuppressWarnings.UN_U)
    int f;
    // violation below, 'The warning '' cannot be suppressed at this location'
    @SuppressWarnings((1 != 1) ? "" : "UN_U")
    int g;
    @SuppressWarnings("un" + "used")
    int h;
    @SuppressWarnings((String) "UN_U")
    int i;
    // violation below, 'The warning '' cannot be suppressed at this location'
    @SuppressWarnings({})
    int j;
    @SuppressWarnings({UN_U})
    int k;
    // violation below, 'The warning '' cannot be suppressed at this location'
    @SuppressWarnings({"UN_U", true ? "UN_U" : ""})
    int l;
}

class CustomSuppressWarnings {
    static final String UN_U = "UN_U";
    @SuppressWarnings
    // violation above, 'The warning '' cannot be suppressed at this location'
    private @interface SuppressWarnings {
    }
}
