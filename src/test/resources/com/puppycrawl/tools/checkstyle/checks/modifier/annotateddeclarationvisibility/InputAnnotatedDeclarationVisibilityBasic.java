/*
AnnotatedDeclarationVisibility
annotations = (default)com.google.common.annotations.VisibleForTesting
visibility = (default)protected,package
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotateddeclarationvisibility;

public class InputAnnotatedDeclarationVisibilityBasic {

    @com.google.common.annotations.VisibleForTesting
    protected void allowedProtectedMethod() {}

    @com.google.common.annotations.VisibleForTesting
    void allowedPackagePrivateMethod() {}

    // violation below 'Annotated element has disallowed visibility 'public'.'
    @com.google.common.annotations.VisibleForTesting
    public void violationPublicMethod() {}

    // violation below 'Annotated element has disallowed visibility 'private'.'
    @com.google.common.annotations.VisibleForTesting
    private void violationPrivateMethod() {}

    @com.google.common.annotations.VisibleForTesting
    protected int allowedProtectedField;

    // violation below 'Annotated element has disallowed visibility 'public'.'
    @com.google.common.annotations.VisibleForTesting
    public int violationPublicField;

    protected InputAnnotatedDeclarationVisibilityBasic() {}

    // violation below 'Annotated element has disallowed visibility 'public'.'
    @com.google.common.annotations.VisibleForTesting
    public InputAnnotatedDeclarationVisibilityBasic(int x) {}
}
