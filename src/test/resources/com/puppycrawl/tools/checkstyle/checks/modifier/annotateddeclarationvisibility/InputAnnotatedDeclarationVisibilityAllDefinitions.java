/*
AnnotatedDeclarationVisibility
annotations = (default)com.google.common.annotations.VisibleForTesting
visibility = (default)protected,package
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.annotateddeclarationvisibility;

import com.google.common.annotations.VisibleForTesting;

public class InputAnnotatedDeclarationVisibilityAllDefinitions {

    // METHOD_DEF
    @VisibleForTesting
    protected void allowedMethod() {}

    // violation below 'Annotated element has disallowed visibility 'public'.'
    @VisibleForTesting
    public void violationPublicMethod() {}

    // violation below 'Annotated element has disallowed visibility 'private'.'
    @VisibleForTesting
    private void violationPrivateMethod() {}

    // VARIABLE_DEF
    @VisibleForTesting
    protected int allowedField;

    // violation below 'Annotated element has disallowed visibility 'public'.'
    @VisibleForTesting
    public int violationPublicField;

    // CTOR_DEF
    @VisibleForTesting
    protected InputAnnotatedDeclarationVisibilityAllDefinitions() {}

    // violation below 'Annotated element has disallowed visibility 'public'.'
    @VisibleForTesting
    public InputAnnotatedDeclarationVisibilityAllDefinitions(int x) {}

    // CLASS_DEF
    @VisibleForTesting
    protected static class AllowedNestedClass {}

    // violation below 'Annotated element has disallowed visibility 'public'.'
    @VisibleForTesting
    public static class ViolationNestedClass {}

    // INTERFACE_DEF
    @VisibleForTesting
    interface AllowedNestedInterface {}

    // violation below 'Annotated element has disallowed visibility 'public'.'
    @VisibleForTesting
    public interface ViolationNestedInterface {}

    // ENUM_DEF
    @VisibleForTesting
    protected enum AllowedEnum {
        A, B
    }

    // violation below 'Annotated element has disallowed visibility 'public'.'
    @VisibleForTesting
    public enum ViolationEnum {
        A, B
    }

    // RECORD_DEF
    @VisibleForTesting
    protected record AllowedRecord(int x) {}

    // violation below 'Annotated element has disallowed visibility 'private'.'
    @VisibleForTesting
    private record ViolationRecord(int x) {}

    // ANNOTATION_DEF
    @VisibleForTesting
    protected @interface AllowedAnnotation {}

    // violation below 'Annotated element has disallowed visibility 'public'.'
    @VisibleForTesting
    public @interface ViolationAnnotation {}

}
