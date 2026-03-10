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

public class InputAnnotatedMethodVisibilityModifierAllDefinitions {

    // METHOD_DEF
    @VisibleForTesting
    protected void allowedMethod() {}

    @VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'public'.'
    public void violationPublicMethod() {}

    @VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'private'.'
    private void violationPrivateMethod() {}

    // VARIABLE_DEF
    @VisibleForTesting
    protected int allowedField;

    @VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'public'.'
    public int violationPublicField;

    // CTOR_DEF
    @VisibleForTesting
    protected InputAnnotatedMethodVisibilityModifierAllDefinitions() {}

    @VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'public'.'
    public InputAnnotatedMethodVisibilityModifierAllDefinitions(int x) {}

    // CLASS_DEF
    @VisibleForTesting
    protected static class AllowedNestedClass {}

    @VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'public'.'
    public static class ViolationNestedClass {}

    // INTERFACE_DEF
    @VisibleForTesting
    interface AllowedNestedInterface {}

    @VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'public'.'
    public interface ViolationNestedInterface {}

    // ENUM_DEF
    @VisibleForTesting
    protected enum AllowedEnum {
        A, B
    }

    @VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'public'.'
    public enum ViolationEnum {
        A, B
    }

    // RECORD_DEF
    @VisibleForTesting
    protected record AllowedRecord(int x) {}

    @VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'private'.'
    private record ViolationRecord(int x) {}

    // ANNOTATION_DEF
    @VisibleForTesting
    protected @interface AllowedAnnotation {}

    @VisibleForTesting
    // violation below 'Annotated element has disallowed visibility 'public'.'
    public @interface ViolationAnnotation {}

}
