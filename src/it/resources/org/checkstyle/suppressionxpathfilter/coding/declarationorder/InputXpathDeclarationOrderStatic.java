/*
DeclarationOrder
ignoreConstructors = (default)false
ignoreModifiers = (default)false


*/

package org.checkstyle.suppressionxpathfilter.coding.declarationorder;

public class InputXpathDeclarationOrderStatic {
    public static void foo1() {}
    // violation below 'Static variable definition in wrong order.'
    public static final double MAX = 0.60; //warn
    public static void foo2() {}
}
