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

    @Ann
    // violation below 'Annotated element has disallowed visibility 'public'.'
    public void violationPublicMethod() {}

    @Ann
    // violation below 'Annotated element has disallowed visibility 'private'.'
    private void violationPrivateMethod() {}

    @Ann
    protected int allowedField;

    @Ann
    // violation below 'Annotated element has disallowed visibility 'public'.'
    public int violationPublicField;

}
