/*
AnnotatedDeclarationVisibility
annotations = org.jspecify.annotations.Nullable
visibility = (default)protected,package
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotateddeclarationvisibility;

import javax.annotation.Nullable;

import com.google.common.annotations.VisibleForTesting;

public class InputAnnotatedDeclarationVisibilityImport {

    @Nullable
    protected void allowedProtected() {}

    // violation below 'Annotated element has disallowed visibility 'public'.'
    @org.jspecify.annotations.Nullable
    public int violationPublic;

    // violation below 'Annotated element has disallowed visibility 'private'.'
    @org.jspecify.annotations.Nullable
    private int violationPrivate;

    @org.jspecify.annotations.Nullable
    protected int allowedField;

    @Nullable
    public int allowedField1;

    @VisibleForTesting
    public int allowedField2;

}
