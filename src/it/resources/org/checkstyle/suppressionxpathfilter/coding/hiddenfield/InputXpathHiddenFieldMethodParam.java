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

public class InputXpathHiddenFieldMethodParam {
    static int someField;
    static Object other = null;
    Object field = null;

    // violation below ''other' hides a field.'
    static void method(Object field, Object other) { //warn
    }
}
