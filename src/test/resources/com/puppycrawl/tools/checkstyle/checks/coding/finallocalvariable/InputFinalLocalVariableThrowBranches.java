/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableThrowBranches {
    void caughtException(boolean condition) {
        int value;
        try {
            if (condition) {
                value = 1;
                throw new IllegalArgumentException();
            }
        }
        catch (IllegalArgumentException ignored) {
            value = 2;
        }
        value = 3;
    }

    void finallyAssignment(boolean condition) {
        int value;
        try {
            if (condition) {
                value = 1;
                throw new IllegalArgumentException();
            }
        }
        finally {
            value = 2;
        }
    }

    void elseBranchBad(boolean condition) {
        int value; // violation "Variable 'value' should be declared final"
        if (condition) {
            System.out.println(condition);
        }
        else {
            value = 1;
            throw new IllegalArgumentException();
        }
        value = 2;
    }

    void elseBranchGood(boolean condition) {
        final int value;
        if (condition) {
            System.out.println(condition);
        }
        else {
            value = 1;
            throw new IllegalArgumentException();
        }
        value = 2;
    }

    void conditionalThrow(boolean first, boolean second) {
        int value;
        if (first) {
            value = 1;
            if (second) {
                throw new IllegalArgumentException();
            }
        }
        value = 2;
    }

    void nestedBlockBad(boolean condition) {
        int value; // violation "Variable 'value' should be declared final"
        if (condition) {
            value = 1;
            {
                System.out.println(value);
            }
            throw new IllegalArgumentException();
        }
        value = 2;
    }

    void nestedBlockGood(boolean condition) {
        final int value;
        if (condition) {
            value = 1;
            {
                System.out.println(value);
            }
            throw new IllegalArgumentException();
        }
        value = 2;
    }

    void reassignedElse(boolean condition, boolean reverse) {
        int value;
        if (condition) {
            throw new IllegalArgumentException();
        }
        else {
            value = 1;
            if (reverse) {
                value = -value;
            }
        }
    }

}
