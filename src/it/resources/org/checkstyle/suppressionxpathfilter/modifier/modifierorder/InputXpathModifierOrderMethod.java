package org.checkstyle.suppressionxpathfilter.modifier.modifierorder;

public class InputXpathModifierOrderMethod {
    private @MethodAnnotation void foo() {} // warn
}

@interface MethodAnnotation {}
