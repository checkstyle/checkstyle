/*
ModifierOrder
modifiersOrder = public, protected, private, abstract, default, static,\
                sealed, non-sealed, final, transient, volatile,\
                synchronized, native, strictfp

*/

package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

public class InputModifierOrderCustomSameAsDefault {
    private static final int INT_VALUE = 1;

    static private void testStaticPrivateMethod() {}
    // violation above ''private'.*out of order.*JLS suggestions.'
}
