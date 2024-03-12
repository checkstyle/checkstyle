/*
IllegalType
validateAbstractClassNames = (default)false
illegalClassNames = java.lang.StringBuffer,\t
legalAbstractClassNames = (default)
ignoredMethodNames = (default)getEnvironment, getInitialContext
illegalAbstractClassNameFormat = (default)^(.*[.])?Abstract.*$
memberModifiers = (default)
tokens = (default)ANNOTATION_FIELD_DEF, CLASS_DEF, INTERFACE_DEF, METHOD_CALL, METHOD_DEF, \
         METHOD_REF, PARAMETER_DEF, VARIABLE_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/
package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.List;

public class InputIllegalTypeWhitespaceInConfig {
    public void example(List<@MyPattern String> strings) {
    }

    @Target(ElementType.TYPE_USE)
    public @interface MyPattern {}
}

