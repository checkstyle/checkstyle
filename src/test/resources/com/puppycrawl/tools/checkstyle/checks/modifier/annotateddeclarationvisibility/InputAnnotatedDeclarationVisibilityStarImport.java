/*
AnnotatedDeclarationVisibility
annotations = (default)com.google.common.annotations.VisibleForTesting
visibility = (default)protected,package
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotateddeclarationvisibility;

import javax.annotation.*;
import com.google.common.annotations.*;
import org.jspecify.annotations.*;


public class InputAnnotatedDeclarationVisibilityStarImport {

    @VisibleForTesting
    protected void allowed() {}

    // violation below 'Annotated element has disallowed visibility 'public'.'
    @VisibleForTesting
    public void violationPublic() {}

    // violation below 'Annotated element has disallowed visibility 'private'.'
    @VisibleForTesting
    private void violationPrivate() {}

    @VisibleForTesting
    protected int allowedField;

    // violation below 'Annotated element has disallowed visibility 'public'.'
    @VisibleForTesting
    public int violationField;
}
