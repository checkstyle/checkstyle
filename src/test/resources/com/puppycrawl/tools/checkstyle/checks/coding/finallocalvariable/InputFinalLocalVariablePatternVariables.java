/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
validatePatternVariables = true
tokens = (default)VARIABLE_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariablePatternVariables {
    public void run(String... arguments) {
        final Object o = 45;
        if (o instanceof String p) { // violation "Variable 'p' should be declared final"
            System.out.println(p);
        }
        if (o instanceof final String p) {
            System.out.println(p);
        }
        if (o instanceof String p) {
            p = "rewrite";
            System.out.println(p);
        }
        if (obj instanceof String s) {
            s = new String("s"); // reassignment of pattern variable
        }
        final boolean value = o instanceof String p;
    }
}
