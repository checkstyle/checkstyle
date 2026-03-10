/*
AnnotatedMethodVisibilityModifier
annotations = org.jspecify.annotations.Nullable, org.junit.jupiter.api.Disabled
visibility = (default)protected,package-private
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/
// non-compiled because it's not a valid annotation reference, since the import is not defined
package com.puppycrawl.tools.checkstyle.checks.modifier.annotatedmethodvisibilitymodifier;

public class InputAnnotatedMethodVisibilityModifierClearedImport2 {

    @Nullable
    private void allowedMethod() {}

    @Disabled
    public int allowedField;

}
