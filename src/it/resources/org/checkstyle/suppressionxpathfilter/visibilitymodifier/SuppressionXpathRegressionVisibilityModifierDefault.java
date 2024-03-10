package org.checkstyle.suppressionxpathfilter.visibilitymodifier;

public class SuppressionXpathRegressionVisibilityModifierDefault {
    private int myPrivateField; // ok, private class member is allowed

    int field; // warn
}
