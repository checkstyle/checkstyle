/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableReturnInLabeledLoop {
    boolean labeledForLoopInit(String pkg) {
        int state = 0;
        int index;
        final int len = pkg.length();
        next: for (index = 0; index <= len; index++) {
            if (state == 1) {
                return true;
            }
            if (state == 2) {
                return index == len;
            }
            state = 3;
        }
        return false;
    }

    int labeledWhileConditionAssignment() {
        int value;
        int result = 0;
        label: while ((value = result + 1) < 10) {
            result = value;
            if (result > 5) {
                return result;
            }
        }
        return result;
    }

    void nestedUnbracedForLoops() {
        int outer;
        int inner;
        for (outer = 0; outer < 3; outer++)
            for (inner = 0; inner < 3; inner++)
                System.out.println(inner);
    }

    void labeledLoopBodyReturn(boolean condition) {
        int value; // violation "Variable 'value' should be declared final"
        loop: while (condition) {
            value = 1;
            return;
        }
        value = 2;
    }

    void assignmentFollowedByPlainStatementInLoop(boolean condition) {
        int value;
        while (condition) {
            value = 1;
            value = 2;
        }
    }
}
