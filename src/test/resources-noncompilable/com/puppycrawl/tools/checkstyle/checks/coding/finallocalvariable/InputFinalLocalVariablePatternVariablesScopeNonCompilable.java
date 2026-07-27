/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
validatePatternVariables = true
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
         LITERAL_FOR,VARIABLE_DEF,PATTERN_VARIABLE_DEF,EXPR

*/
// non-compiled with javac: Contains duplicate variable for testing Checkstyle AST traversal

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariablePatternVariablesScopeNonCompilable {
    public void test12() {
        if (new Object() instanceof String s10) { // violation
            String s10 = "1"; // violation
        }
    }
}
