/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableThrow {
    void terminatingBranchBad(boolean condition) {
        int value; // violation "Variable 'value' should be declared final"
        if (condition) {
            value = 1;
            throw new IllegalArgumentException();
        }
        value = 2;
    }

    void terminatingBranchGood(boolean condition) {
        final int value;
        if (condition) {
            value = 1;
            throw new IllegalArgumentException();
        }
        value = 2;
    }

    void nestedBranchBad(boolean first, boolean second) {
        int value; // violation "Variable 'value' should be declared final"
        if (first) {
            if (second) {
                value = 1;
                throw new IllegalArgumentException();
            }
            value = 2;
            throw new IllegalStateException();
        }
        value = 3;
    }

    void nestedBranchGood(boolean first, boolean second) {
        final int value;
        if (first) {
            if (second) {
                value = 1;
                throw new IllegalArgumentException();
            }
            value = 2;
            throw new IllegalStateException();
        }
        value = 3;
    }

    void repeatedAssignment(boolean condition) {
        int value;
        if (condition) {
            value = 1;
            value = 2;
            throw new IllegalArgumentException();
        }
        value = 3;
    }

    void assignedBeforeBranch(boolean condition) {
        int value = 0;
        if (condition) {
            value = 1;
            throw new IllegalArgumentException();
        }
        value = 2;
    }

    void assignedAfterBranch(boolean condition) {
        int value;
        if (condition) {
            value = 1;
            throw new IllegalArgumentException();
        }
        value = 2;
        value = 3;
    }

}
