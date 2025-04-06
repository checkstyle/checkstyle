/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE


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

    @SuppressWarnings("checkstyle:name")
    private InputRedundantModifierAnnotationOnEnumConstructor2() { // violation
    }
}
