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

package org.checkstyle.suppressionxpathfilter.coding.hiddenfield;

public class InputXpathHiddenFieldLocalVariable {
    int count = 10;

    void process() {
        // violation below ''count' hides a field.'
        int count = 20; // warn
    }
}
