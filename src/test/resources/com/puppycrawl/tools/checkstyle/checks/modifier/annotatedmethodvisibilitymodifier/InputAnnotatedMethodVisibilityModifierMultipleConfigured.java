/*
AnnotatedMethodVisibilityModifier
annotations = com.google.common.annotations.VisibleForTesting,java.lang.Deprecated
visibility = (default)protected,package-private
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotatedmethodvisibilitymodifier;

import org.fest.util.VisibleForTesting;

public class InputAnnotatedMethodVisibilityModifierMultipleConfigured {

    @com.google.common.annotations.VisibleForTesting
    protected void allowedProtected() {}

    @Deprecated
    void allowedPackagePrivate() {}

    // violation 2 lines below 'Annotated element has disallowed visibility 'public'.'
    @Deprecated
    public void violationPublicMethod1() {}

    @com.google.common.annotations.VisibleForTesting
    protected int allowedField1;

    // violation 2 lines below 'Annotated element has disallowed visibility 'private'.'
    @com.google.common.annotations.VisibleForTesting
    private int violationPrivateField;

    @com.google.common.annotations.VisibleForTesting
    protected InputAnnotatedMethodVisibilityModifierMultipleConfigured() {}

    @VisibleForTesting
    public InputAnnotatedMethodVisibilityModifierMultipleConfigured(int x) {}

    @Deprecated
    @VisibleForTesting
    protected void allowedMethod() {}

    // violation 3 lines below 'Annotated element has disallowed visibility 'public'.'
    @Deprecated
    @VisibleForTesting
    public void violationPublicMethod2() {}

    // violation 3 lines below 'Annotated element has disallowed visibility 'private'.'
    @com.google.common.annotations.VisibleForTesting
    @SuppressWarnings("unused")
    private void violationPrivateMethod() {}

    @VisibleForTesting
    protected int allowedField2;

    // violation 3 lines below 'Annotated element has disallowed visibility 'public'.'
    @SuppressWarnings("unused")
    @com.google.common.annotations.VisibleForTesting
    public int violationPublicField;

}
