/*
ModifierOrder
modifierOrder = openjdk


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

/**
 * Test case to verify that setting modifierOrder actually changes behavior.
 * In OpenJDK order: static comes BEFORE default
 * So "private default static" has static in wrong position.
 */
public interface InputModifierOrderDefaultStatic {

    // violation below ''static' modifier out of order with the configured modifier order.'
    default static void method() {}
}
