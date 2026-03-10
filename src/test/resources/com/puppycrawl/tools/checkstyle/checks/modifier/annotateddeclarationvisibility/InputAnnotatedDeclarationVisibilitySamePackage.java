/*
AnnotatedDeclarationVisibility
annotations = com.puppycrawl.tools.checkstyle.checks.modifier.annotateddeclarationvisibility.Ann
visibility = protected
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/
package com.puppycrawl.tools.checkstyle.checks.modifier.annotateddeclarationvisibility;

@interface Ann {}

public class InputAnnotatedDeclarationVisibilitySamePackage {

    @Ann
    protected void allowedMethod() {}

    // violation below 'Annotated element has disallowed visibility 'public'.'
    @Ann
    public void violationPublicMethod() {}

    // violation below 'Annotated element has disallowed visibility 'private'.'
    @Ann
    private void violationPrivateMethod() {}

    @Ann
    protected int allowedField;

    // violation below 'Annotated element has disallowed visibility 'public'.'
    @Ann
    public int violationPublicField;

}
