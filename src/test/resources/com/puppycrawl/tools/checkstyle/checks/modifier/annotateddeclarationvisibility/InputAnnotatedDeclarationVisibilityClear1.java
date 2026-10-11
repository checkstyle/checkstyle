/*
AnnotatedDeclarationVisibility
annotations = org.jspecify.annotations.Nullable, org.junit.jupiter.api.Disabled, \
              com.puppycrawl.tools.checkstyle.checks.modifier.\
              annotateddeclarationvisibility.Annotation
visibility = (default)protected,package
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotateddeclarationvisibility;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.*;

public class InputAnnotatedDeclarationVisibilityClear1 {

    // violation below 'Annotated element has disallowed visibility 'private'.'
    @Disabled
    private void violationMethod() {}

    // violation below 'Annotated element has disallowed visibility 'public'.'
    @Nullable
    public int violationField;

}
