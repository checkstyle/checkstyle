/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = (default)VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableAnonymousClass {
    public void test() {
        Object testSupport = new Object() { // violation
            @Override
            public String toString() {
                final String dc = new String();
                return dc;
            }
        };
        testSupport.toString();
    }
}
