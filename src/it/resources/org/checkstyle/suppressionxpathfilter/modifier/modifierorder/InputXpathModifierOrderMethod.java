/*
ModifierOrder
modifiersOrder = (default)public, protected, private, abstract, default, static,\
                sealed, non-sealed, final, transient, volatile,\
                synchronized, native, strictfp

*/

package org.checkstyle.suppressionxpathfilter.modifier.modifierorder;

public class InputXpathModifierOrderMethod {
    // violation below ''@MethodAnnotation' annotation.*not precede non-annotation modifiers.'
    private @MethodAnnotation void foo() {}
}

@interface MethodAnnotation {}
