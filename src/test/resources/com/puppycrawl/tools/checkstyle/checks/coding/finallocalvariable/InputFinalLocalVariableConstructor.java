/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = PARAMETER_DEF


*/


package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableConstructor {

    InputFinalLocalVariableConstructor(int a) {
        // violation above 'Variable 'a' should be declared final'
    }

    InputFinalLocalVariableConstructor(int a, int b) { // 2 violations
    }

    InputFinalLocalVariableConstructor(String str) {
        // violation above 'Variable 'str' should be declared final'
    }
}

class Mutation {

    Mutation(final int a) { // ok
    }

    Mutation(final String check) { // ok
    }

    Mutation(String str, final int b) {
        // violation above 'Variable 'str' should be declared final'
    }
}
