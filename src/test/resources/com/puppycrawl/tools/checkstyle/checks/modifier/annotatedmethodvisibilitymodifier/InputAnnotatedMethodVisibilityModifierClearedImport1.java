/*
AnnotatedMethodVisibilityModifier
annotations = org.jspecify.annotations.Nullable, org.junit.jupiter.api.Disabled
visibility = (default)protected,package-private
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotatedmethodvisibilitymodifier;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.*;

public class InputAnnotatedMethodVisibilityModifierClearedImport1 {

    // violation 2 lines below 'Annotated element has disallowed visibility 'private'.'
    @Disabled
    private void violationMethod() {}

    // violation 2 lines below 'Annotated element has disallowed visibility 'public'.'
    @Nullable
    public int violationField;

}
