/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableReturnInLoop {
    void throwInLoopBody(boolean condition) {
        int value; // violation "Variable 'value' should be declared final"
        while (condition) {
            value = 1;
            throw new IllegalStateException();
        }
    }

    void returnAfterTryInLoop(boolean condition) {
        int value; // violation "Variable 'value' should be declared final"
        while (condition) {
            try {
                value = 1;
            }
            catch (IllegalArgumentException ignored) {
                System.out.println(ignored.getMessage());
            }
            return;
        }
    }

    void assignInUnbracedIfLoopBody(boolean condition) {
        int value;
        while (condition)
            if (condition) {
                value = 1;
            }
        value = 2;
    }

    void assignInUnbracedLoopBody(boolean condition) {
        int value;
        while (condition)
            value = 1;
        return;
    }

    void throwAfterIfInLoop(boolean condition) {
        int value; // violation "Variable 'value' should be declared final"
        while (condition) {
            if (condition) {
                value = 1;
            }
            throw new IllegalStateException();
        }
    }

    void assignInLoopNoAbrupt(boolean condition) {
        int value;
        while (condition) {
            value = 1;
            System.out.println(value);
        }
    }
}
