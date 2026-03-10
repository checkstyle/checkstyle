/*
AnnotatedDeclarationVisibility
annotations = org.jspecify.annotations.Nullable, org.junit.jupiter.api.Disabled
visibility = (default)protected,package
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/
package com.puppycrawl.tools.checkstyle.checks.modifier.annotateddeclarationvisibility;

@interface Disabled {}

@interface Nullable {}

public class InputAnnotatedDeclarationVisibilityClearedImport2 {

    @Disabled
    private void allowedMethod() {}

    @Nullable
    public int allowedField;
}
