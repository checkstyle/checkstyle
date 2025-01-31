package org.checkstyle.suppressionxpathfilter.visibilitymodifier;

public class InputXpathVisibilityModifierDefault {
    private int myPrivateField; //private class member is allowed

    int field; // warn
}
