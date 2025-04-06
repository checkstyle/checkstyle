/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = VARIABLE_DEF, PARAMETER_DEF
validateUnnamedVariables = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableInterface {
    interface Test {
        static void test (String param) { // violation, "Variable 'param' should be declared final"
            String local = ""; // violation, "Variable 'local' should be declared final"
            System.out.println (param);
        }

        void method (int aParam);

        default void init (int aParam) {} // violation, "Variable 'aParam' should be declared final"

        // violation below, "Variable 'num' should be declared final"
        static int parseInteger(String num) {
            int result = 0;
            try {
                result = Integer.parseInt(num);
            }
            catch (NumberFormatException e) {} // violation, "Variable 'e' should be declared final"
            return result;
        }
    }
}
