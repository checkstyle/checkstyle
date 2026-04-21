/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = PARAMETER_DEF,VARIABLE_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableMultiCatch {
    public void demo() throws Throwable {
        // violation below, "Variable 'a' should be declared final"
        int a; // assigned in adjacent catches
        byte b; // assigned in try and adjacent catches
        char c; // assigned in adjacent catches and finally
        double d; // assigned in adjacent catches multiple times
        // 2 violations 3 lines below:
        //   "Variable 'e1' should be declared final"
        //   "Variable 'e2' should be declared final"
        int e1, e2;  // assigned in nested catches
        int g; // assigned in two non-related catch blocks
        try {
            b = -1;
        } catch (final NumberFormatException ex) {
            a = 0;
            b = 0;
            d = 0.0;
            // violation below, "Variable 'f' should be declared final"
            float f; // defined in a catch block
            if (a != 0) {
                d = 1.0e9;
            }
            try {
                e1 = 0;
            } catch (final NumberFormatException ex2) {
                e2 = 5;
                f = 0.0f;
                g = 1;
            } catch (final IllegalArgumentException ex2) {
                e2 = 6;
                f = 1.3e-3f;
                g = 2;
            } finally {
                // do nothing
            }

            try {
                // do nothing
            } catch (final NumberFormatException ex2) {
                g = 3;
            } catch (final IllegalArgumentException ex2) {
                g = 4;
            }
        }
        // in-between comment
        catch (IllegalStateException | NullPointerException ex) {
            a = 1;
            b = 0;
            c = 0;
            d = 1.1;
            try {
                e2 = 9;
            } finally {
                e1 = 3;
            }
        } catch (final IllegalArgumentException ex) {
            a = 2;
            c = 1;
            e2 = a + (int) c;
        } finally {
            c = 2;
        }
    }
}
