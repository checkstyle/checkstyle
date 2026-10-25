/*
SuppressWarnings
format = (default)^\\s*+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, \
         ANNOTATION_FIELD_DEF, ENUM_CONSTANT_DEF, PARAMETER_DEF, VARIABLE_DEF, \
         METHOD_DEF, CTOR_DEF, COMPACT_CTOR_DEF, RECORD_DEF, PATTERN_VARIABLE_DEF, \
         MODULE_DEF


*/
package org.checkstyle.suppressionxpathfilter.annotation.suppresswarnings;

public enum InputXpathSuppressWarningsEnumConstantDefinition {
        @SuppressWarnings("") // violation 'The warning '' cannot be suppressed at this location'
        FOO("FOO"),
    ;

    InputXpathSuppressWarningsEnumConstantDefinition(String foo) {

    }
}
