/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableReturn {
    void returnInIf(boolean condition) {
        int value; // violation "Variable 'value' should be declared final"
        if (condition) {
            value = 1;
            return;
        }
        value = 2;
    }

    void returnInIfInsideLoop(boolean condition) {
        int value; // violation "Variable 'value' should be declared final"
        while (condition) {
            if (condition) {
                value = 1;
                return;
            }
        }
        value = 2;
    }

    void returnInSwitchCase(int input) {
        int value; // violation "Variable 'value' should be declared final"
        switch (input) {
            case 0:
                value = 1;
                return;
            case 1:
                value = 2;
                return;
            default:
                break;
        }
        value = 3;
    }

    void returnInLoopBody(boolean condition) {
        int value; // violation "Variable 'value' should be declared final"
        while (condition) {
            value = 1;
            return;
        }
    }
}
