/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFallThrough {
    int direct(int selector) {
        int result;
        switch (selector) {
            case 0:
                result = 1;
            default:
                result = 2;
                break;
        }
        return result;
    }

    int conditionalBreak(int selector, boolean stop) {
        int result;
        switch (selector) {
            case 0:
                result = 1;
                if (stop) {
                    break;
                }
            default:
                result = 2;
        }
        return result;
    }

    int nestedLoopBreak(int selector) {
        int result;
        switch (selector) {
            case 0:
                result = 1;
                while (selector > 0) {
                    break;
                }
            default:
                result = 2;
        }
        return result;
    }

    int terminated(int selector) {
        int result; // violation "Variable 'result' should be declared final"
        switch (selector) {
            case 0:
                result = 1;
                break;
            default:
                result = 2;
        }
        return result;
    }

    int mutuallyExclusive(int selector, boolean stop) {
        int result; // violation "Variable 'result' should be declared final"
        switch (selector) {
            case 0:
                if (stop) {
                    result = 1;
                    break;
                }
                else {
                    result = 2;
                    break;
                }
            default:
                result = 3;
        }
        return result;
    }

    int severalCases(int selector) {
        int result;
        switch (selector) {
            case 0:
                result = 1;
            case 1:
                result = 2;
            default:
                result = 3;
        }
        return result;
    }

    int defaultFirst(int selector) {
        int result;
        switch (selector) {
            default:
                result = 1;
            case 0:
                result = 2;
        }
        return result;
    }

}
