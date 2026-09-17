/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE, ANNOTATION_DEF, RECORD_DEF, \
         PATTERN_VARIABLE_DEF, LITERAL_CATCH, LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public enum InputRedundantModifierAnnotationOnEnumConstructor {
    ;

    @SuppressWarnings("checkstyle:name")
    InputRedundantModifierAnnotationOnEnumConstructor() {
    }
}
enum InputRedundantModifierAnnotationOnEnumConstructor2 {
    ;


    // violation 2 lines below 'Redundant 'private' modifier.'
    @SuppressWarnings("checkstyle:name")
    private InputRedundantModifierAnnotationOnEnumConstructor2() {
    }
}
