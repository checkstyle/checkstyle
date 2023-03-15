package org.checkstyle.suppressionxpathfilter.modifierorder;

public class SuppressionXpathRegressionModifierOrderMethod {
    private @MethodAnnotation void foo() {} // warn
}

@interface MethodAnnotation {}
