/*
AnnotatedMethodVisibilityModifier
annotations = (default)com.google.common.annotations.VisibleForTesting
visibility = (default)protected,package-private
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotatedmethodvisibilitymodifier;

public class InputAnnotatedMethodVisibilityModifierBasic {

    @com.google.common.annotations.VisibleForTesting
    protected void allowedProtectedMethod() {}

    @com.google.common.annotations.VisibleForTesting
    void allowedPackagePrivateMethod() {}

    @com.google.common.annotations.VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'public'.'
    public void violationPublicMethod() {}

    @com.google.common.annotations.VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'private'.'
    private void violationPrivateMethod() {}

    @com.google.common.annotations.VisibleForTesting
    protected int allowedProtectedField;

    @com.google.common.annotations.VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'public'.'
    public int violationPublicField;

    @com.google.common.annotations.VisibleForTesting
    protected InputAnnotatedMethodVisibilityModifierBasic() {}

    @com.google.common.annotations.VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'public'.'
    public InputAnnotatedMethodVisibilityModifierBasic(int x) {}
}
