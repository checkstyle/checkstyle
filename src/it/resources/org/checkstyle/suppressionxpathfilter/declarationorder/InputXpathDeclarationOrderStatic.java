package org.checkstyle.suppressionxpathfilter.declarationorder;

public class InputXpathDeclarationOrderStatic {
    public static void foo1() {}
    public static final double MAX = 0.60; //warn
    public static void foo2() {}
}
