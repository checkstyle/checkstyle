/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = VARIABLE_DEF, PARAMETER_DEF

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableThrowScopes {
    void parameter(int value, final boolean condition) {
        if (condition) {
            throw new IllegalArgumentException();
        }
        value = 2;
    }

    void untouchedBad(final boolean condition) {
        int value; // violation "Variable 'value' should be declared final"
        if (condition) {
            throw new IllegalArgumentException();
        }
        value = 2;
    }

    void untouchedGood(final boolean condition) {
        final int value;
        if (condition) {
            throw new IllegalArgumentException();
        }
        value = 2;
    }

    void blockBad(final boolean condition) {
        int value; // violation "Variable 'value' should be declared final"
        if (condition) {
            {
                value = 1;
                throw new IllegalArgumentException();
            }
        }
        value = 2;
    }

    void blockGood(final boolean condition) {
        final int value;
        if (condition) {
            {
                value = 1;
                throw new IllegalArgumentException();
            }
        }
        value = 2;
    }

}
