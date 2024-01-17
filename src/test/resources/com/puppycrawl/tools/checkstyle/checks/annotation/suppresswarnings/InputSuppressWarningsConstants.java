/*
SuppressWarnings
format = (default)^\s*+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

import java.util.List;
import java.util.ArrayList;

public class InputSuppressWarningsConstants
{
    public static final String UNCHECKED = "unchecked";

    public static void test() {
        @SuppressWarnings(UNCHECKED)
        final List<String> dummyOne = (List<String>) new ArrayList();
        @SuppressWarnings(InputSuppressWarningsConstants.UNCHECKED)
        final List<String> dummyTwo = (List<String>) new ArrayList();
    }
}
