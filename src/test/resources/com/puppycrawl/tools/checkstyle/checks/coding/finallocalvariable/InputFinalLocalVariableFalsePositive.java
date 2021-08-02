/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = VARIABLE_DEF, PARAMETER_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFalsePositive // ok
{
    public void method()
    {
        final java.util.List<Object> list = new java.util.ArrayList<>();

        for ( int i = 0, s0 = list.size(); i < s0; i++ )
        {
        }
    }

    public void method1()
    {
        final java.util.List<Object> list = new java.util.ArrayList<>();

        for ( int i = 0, s0 = list.size(); i < s0; s0++ )
        {
        }
    }
}
