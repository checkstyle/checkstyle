/*
ModifierOrder
modifierOrder = (default)default

*/

package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

/**
 * Test case for ModifierOrder check with default style.
 * This file is used to verify that the default behavior remains unchanged.
 */
final class InputModifierOrderDefault {

    /** Correct order according to default JLS style: private final static */
    private static final String CORRECT_ORDER = "correct";

    /** Correct order: public static final */
    public static final int MAX_VALUE = 100;

    /**
     * Correct order for methods: private static final
     */
    private static final void correctMethod() {
    }

    /**
     * Violation: static before final is OK in default mode
     * But final before private is wrong
     */
    final private String WRONG_ORDER = "wrong"; // violation ''private'.*out of order.*'
}
