/*
AnnotatedMethodVisibilityModifier
annotations = java.lang.Deprecated
visibility = private
tokens = PACKAGE_DEF, IMPORT, RECORD_DEF, \
         CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotatedmethodvisibilitymodifier;

public class InputAnnotatedMethodVisibilityModifierNonDefaultTokens {

    @Deprecated
    private int allowed;

    @Deprecated
    // violation below 'Annotated element has disallowed visibility 'protected'.'
    protected int violationProtected;

    @Deprecated
    public void allowedToken() {}

}
