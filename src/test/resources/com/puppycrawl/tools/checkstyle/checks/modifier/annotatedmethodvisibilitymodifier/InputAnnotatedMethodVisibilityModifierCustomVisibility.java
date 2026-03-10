/*
AnnotatedMethodVisibilityModifier
annotations = (default)com.google.common.annotations.VisibleForTesting
visibility = public
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotatedmethodvisibilitymodifier;

public class InputAnnotatedMethodVisibilityModifierCustomVisibility {

    @com.google.common.annotations.VisibleForTesting
    public void allowedPublic() {}

    @com.google.common.annotations.VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'protected'.'
    protected void violationProtected() {}

    @com.google.common.annotations.VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'private'.'
    private void violationPrivate() {}

    @com.google.common.annotations.VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'package-private'.'
    void violationPackagePrivate() {}

    @com.google.common.annotations.VisibleForTesting
    public int allowedField;

    @com.google.common.annotations.VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'protected'.'
    protected int violationField;
}
