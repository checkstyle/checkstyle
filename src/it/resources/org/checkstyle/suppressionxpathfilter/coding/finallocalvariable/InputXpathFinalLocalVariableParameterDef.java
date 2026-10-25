/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = PARAMETER_DEF


*/

package org.checkstyle.suppressionxpathfilter.coding.finallocalvariable;

public class InputXpathFinalLocalVariableParameterDef {
    // violation below 'Variable 'aArg' should be declared final.'
    public void method(int aArg, final int aFinal, int aArg2)
    {
        int z = 0;

        z++;

        aArg2++;
    }
}
