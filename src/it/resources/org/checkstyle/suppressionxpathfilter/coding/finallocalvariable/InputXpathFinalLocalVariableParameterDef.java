package org.checkstyle.suppressionxpathfilter.coding.finallocalvariable;

public class InputXpathFinalLocalVariableParameterDef {
    public void method(int aArg, final int aFinal, int aArg2) // warn
    {
        int z = 0;

        z++;

        aArg2++;
    }
}
