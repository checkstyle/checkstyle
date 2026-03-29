/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public enum InputRedundantModifierConstructorModifier {
    VAL1, VAL2;

    private InputRedundantModifierConstructorModifier() { } // violation

    InputRedundantModifierConstructorModifier(int i) { }

    InputRedundantModifierConstructorModifier(char c) { }
}

class ProperPrivateConstructor {
    private ProperPrivateConstructor() { }
}
