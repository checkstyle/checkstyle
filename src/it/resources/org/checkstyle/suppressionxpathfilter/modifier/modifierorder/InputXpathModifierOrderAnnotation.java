/*
ModifierOrder
modifiersOrder = (default)public, protected, private, abstract, default, static,\
                sealed, non-sealed, final, transient, volatile,\
                synchronized, native, strictfp

*/

package org.checkstyle.suppressionxpathfilter.modifier.modifierorder;

// violation below ''@InterfaceAnnotation' annotation.*not precede non-annotation modifiers.'
public @InterfaceAnnotation @interface InputXpathModifierOrderAnnotation {
    int foo();
}

@interface InterfaceAnnotation {}
