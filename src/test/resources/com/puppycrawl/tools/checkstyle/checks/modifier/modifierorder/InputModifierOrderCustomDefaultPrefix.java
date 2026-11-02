/*
ModifierOrder
modifiersOrder = public

*/

package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

public class InputModifierOrderCustomDefaultPrefix {
    private static final int INT_VALUE = 1;

    static private void testStaticPrivateMethod() {}
    // violation above 'private' modifier out of order with the defined modifier order.
}
