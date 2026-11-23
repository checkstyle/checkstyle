/*
HiddenField
ignoreFormat = ^ignored.*$
ignoreConstructorParameter = (default)false
ignoreSetter = (default)false
setterCanReturnItsClass = (default)false
ignoreAbstractMethods = (default)false
tokens = (default)VARIABLE_DEF,PARAMETER_DEF,CLASS_DEF,ENUM_DEF,ENUM_CONSTANT_DEF, \
          PATTERN_VARIABLE_DEF,LAMBDA,RECORD_DEF,RECORD_COMPONENT_DEF,COMPACT_COMPILATION_UNIT

*/

package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

public class InputHiddenFieldRecordComponentIgnoreFormat {
    private static int ignoredCount;

    record Inner(int ignoredCount) { // ok, matches ignoreFormat
    }
}
