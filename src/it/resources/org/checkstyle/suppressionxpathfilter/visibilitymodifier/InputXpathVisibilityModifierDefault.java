package org.checkstyle.suppressionxpathfilter.visibilitymodifier;

public class InputXpathVisibilityModifierDefault {
    private int myPrivateField; // ok, private class member is allowed

    int field; // warn
}
