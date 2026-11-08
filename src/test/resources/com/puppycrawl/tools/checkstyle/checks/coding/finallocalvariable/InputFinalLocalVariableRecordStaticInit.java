/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT, \
          LITERAL_BREAK,LITERAL_FOR,VARIABLE_DEF,EXPR

*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public record InputFinalLocalVariableRecordStaticInit(int x) {

    static {
        int a; // violation "Variable 'a' should be declared final"
        a = 1;
    }
}
