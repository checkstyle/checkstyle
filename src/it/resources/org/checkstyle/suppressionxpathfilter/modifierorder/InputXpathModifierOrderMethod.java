package org.checkstyle.suppressionxpathfilter.modifierorder;

public class InputXpathModifierOrderMethod {
    private @MethodAnnotation void foo() {} // warn
}

@interface MethodAnnotation {}
