/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableLastCase {
    int assignmentAfterYield(int selector) {
        int result;
        switch (selector) {
            default:
                final int ignored = switch (selector) {
                    default:
                        result = 1;
                        yield 0;
                };
                result = 2 + ignored;
        }
        return result;
    }
}
