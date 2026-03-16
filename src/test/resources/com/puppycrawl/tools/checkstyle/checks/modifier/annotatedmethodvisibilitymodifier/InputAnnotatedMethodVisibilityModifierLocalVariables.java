/*
AnnotatedMethodVisibilityModifier
annotations = (default)com.google.common.annotations.VisibleForTesting
visibility = protected
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotatedmethodvisibilitymodifier;

import com.google.common.annotations.VisibleForTesting;

public class InputAnnotatedMethodVisibilityModifierLocalVariables {

    @VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'public'.'
    public int field;

    public void method() {
        @VisibleForTesting
        int localVariable = 0; // should not trigger
    }
}
