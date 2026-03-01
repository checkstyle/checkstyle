/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
validatePatternVariables = (default)false
tokens = (default)VARIABLE_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariablePatternVariables2 {
    public void run(String... arguments) {
        final Object o = 45;
        if (o instanceof String p) {
            System.out.println(p);
        }
        if (o instanceof final String p) {
            System.out.println(p);
        }
        if (o instanceof String p) {
            p = "rewrite";
            System.out.println(p);
        }
        final boolean value = o instanceof String p;
    }
}
