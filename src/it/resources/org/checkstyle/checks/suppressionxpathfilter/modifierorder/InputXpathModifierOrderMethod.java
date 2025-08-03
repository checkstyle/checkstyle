package org.checkstyle.checks.suppressionxpathfilter.modifierorder;

public class InputXpathModifierOrderMethod {
    private @MethodAnnotation void foo() {} // warn
}

@interface MethodAnnotation {}
