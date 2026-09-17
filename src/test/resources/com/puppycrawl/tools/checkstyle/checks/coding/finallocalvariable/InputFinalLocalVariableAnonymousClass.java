/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableAnonymousClass {
    public void test() {
        // violation below "Variable 'testSupport' should be declared final"
        Object testSupport = new Object() {
            @Override
            public String toString() {
                final String dc = new String();
                return dc;
            }
        };
        testSupport.toString();
    }
}
