/*
AnnotatedMethodVisibilityModifier
annotations = (default)com.google.common.annotations.VisibleForTesting
visibility = (default)protected,package-private
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotatedmethodvisibilitymodifier;

import com.google.common.annotations.VisibleForTesting;

public interface InputAnnotatedMethodVisibilityModifierInterfaceImplicit {

    @VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'public'.'
    void violationMethodOne();

    @VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'public'.'
    void violationMethodTwo();

    @VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'public'.'
    int violationFieldOne = 1;

    @VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'public'.'
    int violationFieldTwo = 2;

}
