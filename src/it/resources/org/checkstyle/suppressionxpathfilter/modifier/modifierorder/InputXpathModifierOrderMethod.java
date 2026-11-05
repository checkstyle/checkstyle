/*
ModifierOrder
modifiersOrder = (default)public, protected, private, abstract, default, static,\
                sealed, non-sealed, final, transient, volatile,\
                synchronized, native, strictfp

*/

package org.checkstyle.suppressionxpathfilter.modifier.modifierorder;

public class InputXpathModifierOrderMethod {
    private @MethodAnnotation void foo() {} // violation ''@MethodAnnotation' annotation modifier does not precede non-annotation modifiers.'
}

@interface MethodAnnotation {}
