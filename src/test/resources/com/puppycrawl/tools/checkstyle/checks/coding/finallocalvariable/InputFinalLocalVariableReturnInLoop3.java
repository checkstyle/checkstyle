/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableReturnInLoop3 {
    void returnAfterAssignmentInLoop(boolean condition) {
        int value; // violation "Variable 'value' should be declared final"
        while (condition) {
            value = 1;
            return;
        }
    }

    void throwAfterAssignmentInDoWhileLoop(boolean condition) {
        int value; // violation "Variable 'value' should be declared final"
        do {
            value = 1;
            throw new IllegalStateException();
        } while (condition);
    }

    void returnInTryAfterAssignmentInLoop(boolean condition) {
        int value;
        while (condition) {
            try {
                value = 1;
                return;
            }
            catch (IllegalStateException ignored) {
                System.out.println(ignored.getMessage());
            }
        }
    }
}
