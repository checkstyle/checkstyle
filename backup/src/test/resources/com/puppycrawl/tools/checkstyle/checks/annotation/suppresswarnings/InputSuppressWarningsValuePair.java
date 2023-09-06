/*
SuppressWarnings
format = (default)^\s*+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, CTOR_DEF, \
         COMPACT_CTOR_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.suppresswarnings;

import java.util.ArrayList;
import java.util.List;

public class InputSuppressWarningsValuePair
{
    public static final String UNCHECKED = "unchecked";

    public static void test() {
        @SuppressWarnings(value = UNCHECKED) // ok
        final List<String> dummyOne = (List<String>) new ArrayList();
    }
}
