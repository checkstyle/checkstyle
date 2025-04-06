/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = PARAMETER_DEF,VARIABLE_DEF
validateUnnamedVariables = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableMultiCatch {
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
