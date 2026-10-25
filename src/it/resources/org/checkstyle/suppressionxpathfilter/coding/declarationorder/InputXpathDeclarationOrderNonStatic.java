/*
DeclarationOrder
ignoreConstructors = (default)false
ignoreModifiers = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.declarationorder;

public class InputXpathDeclarationOrderNonStatic {
    private int age;
    // violation below 'Variable access definition in wrong order.'
    public String name;  //warn
}
