/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = VARIABLE_DEF, PARAMETER_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableMultiCatch { // ok
    public void demo() throws Throwable {
        try {
            // do nothing
        } catch (final NumberFormatException ex) {
            // do nothing
        } catch (IllegalStateException | NullPointerException ex) {
            // do nothing
        }
    }
}
