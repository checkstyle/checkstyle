/*
ModifierOrder
order = public, protected, private, abstract, static, final, transient, volatile, default, synchronized, native, strictfp

*/

package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

public class InputModifierOrderCustomOrder {
    public static final int MAX_VALUE = 100;

    private final static String S = "x"; // violation ''static'.*out of order.*'

    private static final String OK = "y";
}
