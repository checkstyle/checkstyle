/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableReturnBranches {
    void returnAfterIfElseInLoop(boolean condition) {
        int value; // violation "Variable 'value' should be declared final"
        while (condition) {
            if (condition) {
                System.out.println(condition);
            }
            else {
                value = 1;
            }
            return;
        }
    }

    void conditionalReturn(boolean first, boolean second) {
        int value;
        if (first) {
            value = 1;
            if (second) {
                return;
            }
        }
        value = 2;
    }

    void returnInTryCatch(boolean condition) {
        int value;
        try {
            if (condition) {
                value = 1;
                return;
            }
        }
        catch (IllegalArgumentException ignored) {
            value = 2;
        }
        value = 3;
    }

    void returnInTryInsideLoop(boolean condition) {
        int value;
        while (condition) {
            try {
                if (condition) {
                    value = 1;
                    return;
                }
            }
            catch (IllegalArgumentException ignored) {
                System.out.println(ignored.getMessage());
            }
        }
    }

    void loopAssignAfterReturnBranch(boolean condition) {
        int value;
        while (condition) {
            if (condition) {
                value = 1;
                return;
            }
            value = 2;
        }
    }

    void returnInIfInsideLoopGood(boolean condition) {
        final int value;
        while (condition) {
            if (condition) {
                value = 1;
                return;
            }
        }
        value = 2;
    }
}
