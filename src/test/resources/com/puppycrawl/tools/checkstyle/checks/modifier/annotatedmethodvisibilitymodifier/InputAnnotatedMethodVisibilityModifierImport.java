/*
AnnotatedMethodVisibilityModifier
annotations = org.jspecify.annotations.Nullable
visibility = (default)protected,package-private
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotatedmethodvisibilitymodifier;

import javax.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;

public class InputAnnotatedMethodVisibilityModifierImport {

    @Nullable
    protected void allowedProtected() {}

    @org.jspecify.annotations.Nullable
    // violation below 'Annotated element has disallowed visibility 'public'.'
    public int violationPublic;

    @org.jspecify.annotations.Nullable
    // violation below 'Annotated element has disallowed visibility 'private'.'
    private int violationPrivate;

    @org.jspecify.annotations.Nullable
    protected int allowedField;

    @Nullable
    public int allowedField1;

    @VisibleForTesting
    public int allowedField2;

}
