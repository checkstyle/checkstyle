/*
ModifierOrder
modifiersOrder = (default)public, protected, private, abstract, default, static,\
                sealed, non-sealed, final, transient, volatile,\
                synchronized, native, strictfp

*/

package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;
// violation below ''@InterfaceAnnotation' annotation.*not precede non-annotation modifiers.'
public @InterfaceAnnotation @interface InputModifierOrderAnnotationDeclaration {
    int getValue();
}

@interface InterfaceAnnotation {}
