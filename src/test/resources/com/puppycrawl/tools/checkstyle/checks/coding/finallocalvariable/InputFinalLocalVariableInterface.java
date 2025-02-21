/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = VARIABLE_DEF, PARAMETER_DEF


*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableInterface {
    interface Test {
        static void test(String param) { // violation, "Variable 'param' should be declared final"
            String local = ""; // violation, "Variable 'local' should be declared final"
            System.out.println(param);
        }
    }
}
