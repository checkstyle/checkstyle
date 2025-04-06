/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = (default)VARIABLE_DEF
validateUnnamedVariables = (default)false


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public record InputFinalLocalVariableCheckRecords(boolean t, boolean f) {
    public InputFinalLocalVariableCheckRecords {
        int a = 0;
        a = 1;
    }

    record bad(int i) {
        public bad {
            int b = 0; // violation, "Variable 'b' should be declared final"
        }
    }
}

