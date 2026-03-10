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

    @Deprecated
    // violation below 'Annotated element has disallowed visibility 'public'.'
    public void violationPublicMethod1() {}

    @com.google.common.annotations.VisibleForTesting
    protected int allowedField1;

    @com.google.common.annotations.VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'private'.'
    private int violationPrivateField;

    @com.google.common.annotations.VisibleForTesting
    protected InputAnnotatedMethodVisibilityModifierMultipleConfigured() {}

    @VisibleForTesting
    public InputAnnotatedMethodVisibilityModifierMultipleConfigured(int x) {}

    @Deprecated
    @VisibleForTesting
    protected void allowedMethod() {}

    @Deprecated
    @VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'public'.'
    public void violationPublicMethod2() {}

    @com.google.common.annotations.VisibleForTesting
    @SuppressWarnings("unused")
    // violation below 'Annotated element has disallowed visibility 'private'.'
    private void violationPrivateMethod() {}

    @VisibleForTesting
    protected int allowedField2;

    @SuppressWarnings("unused")
    @com.google.common.annotations.VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'public'.'
    public int violationPublicField;

}
