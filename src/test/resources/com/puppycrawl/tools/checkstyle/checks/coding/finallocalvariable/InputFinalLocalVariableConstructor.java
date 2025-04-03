/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = PARAMETER_DEF
validateUnnamedVariables = (default)false


*/


package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableConstructor {

    InputFinalLocalVariableConstructor(int a) {
        // violation above 'Variable 'a' should be declared final'
    }

    InputFinalLocalVariableConstructor(int a, // violation 'Variable 'a' should be declared final'
                                      int b) { // violation 'Variable 'b' should be declared final'
    }

    InputFinalLocalVariableConstructor(String str) {
        // violation above 'Variable 'str' should be declared final'
    }
}

class Mutation {

    Mutation(final int a) {
    }

    Mutation(final String check) {
    }

    Mutation(String str, final int b) {
        // violation above 'Variable 'str' should be declared final'
    }
}
