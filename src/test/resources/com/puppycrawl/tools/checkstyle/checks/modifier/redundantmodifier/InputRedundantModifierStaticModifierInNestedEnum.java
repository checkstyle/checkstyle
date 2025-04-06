/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public class InputRedundantModifierStaticModifierInNestedEnum {
    static enum NestedEnumWithRedundantStatic {} // violation

    enum CorrectNestedEnum {
        VAL;
        static enum NestedEnumWithRedundantStatic {} // violation
    }

    interface NestedInterface {
        static enum NestedEnumWithRedundantStatic {} // violation
    }
}
