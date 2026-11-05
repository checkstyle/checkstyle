/*
ModifierOrder
modifiersOrder = (default)public, protected, private, abstract, default, static,\
                sealed, non-sealed, final, transient, volatile,\
                synchronized, native, strictfp

*/

package org.checkstyle.suppressionxpathfilter.modifier.modifierorder;

public @InterfaceAnnotation @interface InputXpathModifierOrderAnnotation { // violation ''@InterfaceAnnotation' annotation modifier does not precede non-annotation modifiers.'
    int foo();
}

@interface InterfaceAnnotation {}
