////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2015
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.modifier;

public enum InputRedundantConstructorModifier {
    VAL1, VAL2;

    private InputRedundantConstructorModifier() { } // violation

    InputRedundantConstructorModifier(int i) { }

    InputRedundantConstructorModifier(char c) { }
}

class ProperPrivateConstructor {
    private ProperPrivateConstructor() { }
}
