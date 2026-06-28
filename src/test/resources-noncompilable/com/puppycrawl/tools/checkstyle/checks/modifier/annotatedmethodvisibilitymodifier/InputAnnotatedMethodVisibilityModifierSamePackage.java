/*
AnnotatedMethodVisibilityModifier
annotations = com.puppycrawl.tools.checkstyle.checks.modifier.annotatedmethodvisibilitymodifier.Ann
visibility = protected
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/
// non-compiled because it's not valid annotation, it is an attempt to resolve name through package
package com.puppycrawl.tools.checkstyle.checks.modifier.annotatedmethodvisibilitymodifier;

public class InputAnnotatedMethodVisibilityModifierSamePackage {

    @Ann
    protected void allowedMethod() {}

    // violation 2 lines below 'Annotated element has disallowed visibility 'public'.'
    @Ann
    public void violationPublicMethod() {}

    // violation 2 lines below 'Annotated element has disallowed visibility 'private'.'
    @Ann
    private void violationPrivateMethod() {}

    @Ann
    protected int allowedField;

    // violation 2 lines below 'Annotated element has disallowed visibility 'public'.'
    @Ann
    public int violationPublicField;

}
