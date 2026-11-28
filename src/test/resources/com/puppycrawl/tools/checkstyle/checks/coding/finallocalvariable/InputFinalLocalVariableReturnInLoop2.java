/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableReturnInLoop2 {
    void assignInNestedBlockInLoop(boolean condition) {
        int value;
        while (condition) {
            {
                value = 1;
            }
            return;
        }
    }

    void assignInMethodCallArgInLoop(boolean condition) {
        int value; // violation "Variable 'value' should be declared final"
        while (condition) {
            System.out.println(value = 1);
            return;
        }
    }

    void assignBeforeAndInLoopWithReturn(boolean condition) {
        int value;
        value = 1;
        while (condition) {
            value = 2;
            return;
        }
    }

    void returnInCatchInLoop(boolean condition) {
        int value;
        while (condition) {
            try {
                value = 1;
            }
            catch (IllegalArgumentException ignored) {
                return;
            }
        }
    }

    void assignInSynchronizedInLoop(boolean condition) {
        int value;
        while (condition) {
            synchronized (this) {
                value = 1;
            }
            return;
        }
    }

    void assignInElseIfInLoop(boolean condition) {
        int value;
        while (condition) {
            if (condition) {
                System.out.println(condition);
            }
            else if (condition) {
                value = 1;
            }
            return;
        }
    }

    void assignInSameLoopAsDeclaration(boolean condition) {
        while (condition) {
            int value; // violation "Variable 'value' should be declared final"
            value = 1;
            System.out.println(value);
        }
    }
}
