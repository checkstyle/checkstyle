/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableTernary {

    void assignedInBothBranches(boolean c) {
        int x; // violation "Variable 'x' should be declared final"
        int r = c ? (x = 1) : (x = 2); // violation "Variable 'r' should be declared final"
        System.out.println(x + r);
    }

    void assignedInThenBranchOnly(boolean c) {
        int x; // violation "Variable 'x' should be declared final"
        int r = c ? (x = 1) : 0; // violation "Variable 'r' should be declared final"
        System.out.println(r);
    }

    void assignedInElseBranchOnly(boolean c) {
        int x; // violation "Variable 'x' should be declared final"
        int r = c ? 0 : (x = 2); // violation "Variable 'r' should be declared final"
        System.out.println(r);
    }

    void nestedTernaryInThenBranch(boolean c1, boolean c2) {
        int x; // violation "Variable 'x' should be declared final"
        int r = c1 ? (c2 ? (x = 1) : (x = 2)) : (x = 3); // violation "Variable 'r' should be declared final"
        System.out.println(x + r);
    }

    void nestedTernaryInElseBranch(boolean c1, boolean c2) {
        int x; // violation "Variable 'x' should be declared final"
        int r = c1 ? (x = 1) : (c2 ? (x = 2) : (x = 3)); // violation "Variable 'r' should be declared final"
        System.out.println(x + r);
    }

    void ternaryInsideLoop(boolean c) {
        int x;
        while (c) {
            int r = c ? (x = 1) : (x = 2); // violation "Variable 'r' should be declared final"
            System.out.println(x + r);
        }
    }

    void priorAssignmentPlusTernary(boolean c) {
        int x;
        x = 0;
        int r = c ? (x = 1) : (x = 2); // violation "Variable 'r' should be declared final"
        System.out.println(x + r);
    }

    void assignedTwiceInSameBranch(boolean c) {
        int x;
        int r = c ? (x = 1) + (x = 2) : 0; // violation "Variable 'r' should be declared final"
        System.out.println(r);
    }

    void assignmentInConditionAndBranch() {
        int x;
        int r = (x = 1) == 1 ? (x = 2) : 3; // violation "Variable 'r' should be declared final"
        System.out.println(x + r);
    }

    void ternaryInElseBlock(boolean c, boolean d) {
        int x; // violation "Variable 'x' should be declared final"
        if (c) {
            x = 1;
        } else {
            int r = d ? (x = 2) : (x = 3); // violation "Variable 'r' should be declared final"
            System.out.println(r);
        }
        System.out.println(x);
    }

    void ifElseControl(boolean c) {
        int x; // violation "Variable 'x' should be declared final"
        if (c) {
            x = 1;
        } else {
            x = 2;
        }
        System.out.println(x);
    }
}
