/*
ModifierOrder
modifierOrder =  public, static , final


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

/**
 * Test case to verify that whitespace is trimmed from modifier order property.
 * The configuration has spaces around modifiers: " public, static , final"
 * This should work the same as "public,static,final".
 */
public class InputModifierOrderWhitespaceTrim {

    // This should be valid - public static in correct order
    public static final String CORRECT = "correct";
}
