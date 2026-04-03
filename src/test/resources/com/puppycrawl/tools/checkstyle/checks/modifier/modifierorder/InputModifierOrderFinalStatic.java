/*
ModifierOrder
modifierOrder = (default)default

*/

package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

/**
 * Test case to verify that modifierOrder property actually changes behavior.
 * In default mode, "final static" should be a violation (static should come before final).
 */
public class InputModifierOrderFinalStatic {

    // violation below ''static' modifier out of order with the configured modifier order.'
    final static String WRONG_ORDER = "wrong";
}
