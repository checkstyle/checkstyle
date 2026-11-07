/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFallThroughNested {
    int nestedSwitch(int selector, int nested) {
        int result;
        switch (selector) {
            case 0:
                result = 1;
                switch (nested) {
                    default:
                        break;
                }
            default:
                result = 2;
        }
        return result;
    }

    int block(int selector) {
        int result;
        switch (selector) {
            case 0: {
                result = 1;
            }
            default:
                result = 2;
        }
        return result;
    }

    int conditionalAssignment(int selector, boolean assign) {
        int result;
        switch (selector) {
            case 0:
                if (assign) {
                    result = 1;
                }
            default:
                result = 2;
        }
        return result;
    }
}
