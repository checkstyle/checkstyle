/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = VARIABLE_DEF, PARAMETER_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableReceiverParameter { // ok
    public void foo4(InputFinalLocalVariableReceiverParameter this)
    {
    }

    private class Inner
    {
        public Inner(InputFinalLocalVariableReceiverParameter
                InputFinalLocalVariableReceiverParameter.this)
        {
        }
    }
}
