/*
ModifierOrder


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;
// violation below ''@InterfaceAnnotation' annotation.*not precede non-annotation modifiers.'
public @InterfaceAnnotation @interface InputModifierOrderAnnotationDeclaration {
    int getValue();
}

@interface InterfaceAnnotation {}
