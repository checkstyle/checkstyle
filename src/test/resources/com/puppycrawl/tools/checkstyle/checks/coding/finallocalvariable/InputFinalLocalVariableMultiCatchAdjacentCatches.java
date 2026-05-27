/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = PARAMETER_DEF,VARIABLE_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableMultiCatchAdjacentCatches {
    public void demo() throws Throwable {
        int a; // ok, we do not fail this even though 'a' can be final
        try {
        } catch (final NumberFormatException e) {
            a = 0;
            if (a != 0) {
            }
        } catch (IllegalStateException | NullPointerException e) {
            a = 1;
        } catch (final IllegalArgumentException e) {
            a = 2;
            final int x = a;
        }

        final int aCorrect;
        try {
        } catch (final NumberFormatException e) {
            aCorrect = 0;
            if (aCorrect != 0) {
            }
        } catch (IllegalStateException | NullPointerException e) {
            aCorrect = 1;
        } catch (final IllegalArgumentException e) {
            aCorrect = 2;
            final int x = aCorrect;
        }

        byte b;
        try {
            b = -1;
        } catch (final NumberFormatException e) {
            b = 0;
        } catch (IllegalStateException | NullPointerException e) {
            b = 0;
        }

        char c;
        try {
        } catch (IllegalStateException | NullPointerException e) {
            c = 0;
        } catch (final IllegalArgumentException e) {
            c = 1;
            final int x = c;
        } finally {
            c = 2;
        }

        double d;
        try {
        } catch (final NumberFormatException e) {
            d = 0.0;
            if (true) {
                d = 1.0e9;
            }
        } catch (IllegalStateException | NullPointerException e) {
            d = 1.1;
        }
    }
}
