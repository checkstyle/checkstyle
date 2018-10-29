////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2015
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public enum InputRedundantModifierConstructorModifier {
    VAL1, VAL2;

    private InputRedundantModifierConstructorModifier() { } // violation

    InputRedundantModifierConstructorModifier(int i) { }

    InputRedundantModifierConstructorModifier(char c) { }
}

class ProperPrivateConstructor {
    private ProperPrivateConstructor() { }
}
