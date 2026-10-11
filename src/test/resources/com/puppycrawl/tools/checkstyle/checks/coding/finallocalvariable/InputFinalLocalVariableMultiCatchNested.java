/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = PARAMETER_DEF,VARIABLE_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableMultiCatchNested {
    public void demo() throws Throwable {
        // ok, we do not fail this even though 'e1' and 'e2' can be final
        int e1, e2; // assigned in nested catches
        try {
        } catch (final NumberFormatException e) {
            try {
                e1 = 0;
            } catch (final NumberFormatException ex) {
                e2 = 5;
            } catch (final IllegalArgumentException ex) {
                e2 = 6;
            }
        } catch (IllegalStateException | NullPointerException e) {
            try {
                e2 = 9;
            } finally {
                e1 = 3;
            }
        } catch (final IllegalArgumentException e) {
            e2 = 1;
        }

        final int e1Correct, e2Correct;
        try {
        } catch (final NumberFormatException e) {
            try {
                e1Correct = 0;
            } catch (final NumberFormatException ex) {
                e2Correct = 5;
            } catch (final IllegalArgumentException ex) {
                e2Correct = 6;
            }
        } catch (IllegalStateException | NullPointerException e) {
            try {
                e2Correct = 9;
            } finally {
                e1Correct = 3;
            }
        } catch (final IllegalArgumentException e) {
            e2Correct = 1;
        }

        int g;
        try {
        } catch (final NumberFormatException e) {
            try {
            } catch (final IllegalArgumentException ex) {
                g = 1;
            } catch (final Exception ex) {
                g = 2;
            } finally {
                // do nothing
            }

            try {
            } catch (final IllegalArgumentException ex) {
                g = 3;
            } catch (final Exception ex) {
                g = 4;
            }
        }

        int h; // assigned in a block within try and in catch; edge case for pitest coverage
        try {
            {
                h = 1;
            }
        } catch (final NumberFormatException e) {
            h = 2;
        }

        try {
        } catch (final NumberFormatException e) {
            float f; // ok, we do not fail this even though 'f' can be final
            try {
            } catch (final IllegalArgumentException ex) {
                f = 0.0f;
            } catch (final Exception ex) {
                f = 1.3e-3f;
            }
        }

        try {
        } catch (final NumberFormatException e) {
            final float fCorrect;
            try {
            } catch (final IllegalArgumentException ex) {
                fCorrect = 0.0f;
            } catch (final Exception ex) {
                fCorrect = 1.3e-3f;
            }
        }
    }
}
