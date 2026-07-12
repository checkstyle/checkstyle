/*
AnnotatedDeclarationVisibility
annotations = java.lang.Deprecated
visibility = private
tokens = PACKAGE_DEF, IMPORT, RECORD_DEF, \
         CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotateddeclarationvisibility;

public class InputAnnotatedDeclarationVisibilityNonDefaultTokens {

    @Deprecated
    private int allowed;

    // violation 2 lines below 'Annotated element has disallowed visibility 'protected'.'
    @Deprecated
    protected int violationProtected;

    @Deprecated
    public void allowedToken() {}

}
