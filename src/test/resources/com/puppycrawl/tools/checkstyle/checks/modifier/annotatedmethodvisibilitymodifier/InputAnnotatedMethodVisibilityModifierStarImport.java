/*
AnnotatedMethodVisibilityModifier
annotations = (default)com.google.common.annotations.VisibleForTesting
visibility = (default)protected,package-private
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotatedmethodvisibilitymodifier;

import javax.annotation.*;
import com.google.common.annotations.*;
import org.jspecify.annotations.*;


public class InputAnnotatedMethodVisibilityModifierStarImport {

    @VisibleForTesting
    protected void allowed() {}

    @VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'public'.'
    public void violationPublic() {}

    @VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'private'.'
    private void violationPrivate() {}

    @VisibleForTesting
    protected int allowedField;

    @VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'public'.'
    public int violationField;
}
