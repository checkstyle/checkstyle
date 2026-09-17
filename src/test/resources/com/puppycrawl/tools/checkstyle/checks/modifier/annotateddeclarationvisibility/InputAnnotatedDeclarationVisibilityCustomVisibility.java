/*
AnnotatedDeclarationVisibility
annotations = (default)com.google.common.annotations.VisibleForTesting
visibility = public
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotateddeclarationvisibility;

public class InputAnnotatedDeclarationVisibilityCustomVisibility {

    @com.google.common.annotations.VisibleForTesting
    public void allowedPublic() {}

    // violation below 'Annotated element has disallowed visibility 'protected'.'
    @com.google.common.annotations.VisibleForTesting
    protected void violationProtected() {}

    // violation below 'Annotated element has disallowed visibility 'private'.'
    @com.google.common.annotations.VisibleForTesting
    private void violationPrivate() {}

    // violation below 'Annotated element has disallowed visibility 'package-private'.'
    @com.google.common.annotations.VisibleForTesting
    void violationPackagePrivate() {}

    @com.google.common.annotations.VisibleForTesting
    public int allowedField;

    // violation below 'Annotated element has disallowed visibility 'protected'.'
    @com.google.common.annotations.VisibleForTesting
    protected int violationField;
}
