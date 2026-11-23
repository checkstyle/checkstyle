/*
HiddenField
ignoreFormat = (default)null
ignoreConstructorParameter = (default)false
ignoreSetter = (default)false
setterCanReturnItsClass = (default)false
ignoreAbstractMethods = (default)false
tokens = (default)VARIABLE_DEF,PARAMETER_DEF,CLASS_DEF,ENUM_DEF,ENUM_CONSTANT_DEF, \
          PATTERN_VARIABLE_DEF,LAMBDA,RECORD_DEF,RECORD_COMPONENT_DEF,COMPACT_COMPILATION_UNIT

*/
// non-compiled with javac: a record cannot declare a field with the same name
// as one of its components
package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

public class InputHiddenFieldRecordComponentOwnStaticField {

    record SelfHiding(int count) {
        static int count;
    }
}
