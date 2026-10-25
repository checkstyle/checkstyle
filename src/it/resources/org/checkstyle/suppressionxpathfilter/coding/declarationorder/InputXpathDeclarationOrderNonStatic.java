/*
DeclarationOrder
ignoreConstructors = (default)false
ignoreModifiers = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.declarationorder;

public class InputXpathDeclarationOrderNonStatic {
    private int age;
    public String name; // violation 'Variable access definition in wrong order.'
}
