/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = PARAMETER_DEF


*/


package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableConstructor {

    InputFinalLocalVariableConstructor(int a) { // violation
    }

    InputFinalLocalVariableConstructor(int a, int b) { // 2 violations
    }

    InputFinalLocalVariableConstructor(String str) { // violation
    }
}
