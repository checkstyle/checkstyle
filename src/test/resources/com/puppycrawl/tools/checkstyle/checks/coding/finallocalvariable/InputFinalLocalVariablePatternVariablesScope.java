/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
validatePatternVariables = true
tokens = (default)VARIABLE_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariablePatternVariablesScope {
    public static boolean bigEnoughRect(String s) {
        if (!(s instanceof String r)) {
            return false;
        }
        r = "hello";
        return r.length() > 5;
    }
}
