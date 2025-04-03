/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = PARAMETER_DEF,VARIABLE_DEF
validateUnnamedVariables = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableReceiverParameter {
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
