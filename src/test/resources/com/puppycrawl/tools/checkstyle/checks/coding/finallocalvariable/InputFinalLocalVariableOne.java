/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = (default)VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableOne {

    private int m_ClassVariable = 0;
    //static block
    static
    {
        int i, j = 0; // 2 violations
        Runnable runnable = new Runnable() // violation
        {
            public void run()
            {
            }
        };
    }
    /** constructor */
    public InputFinalLocalVariableOne()
    {
        int i = 0; // violation
        // final variable
        final int j = 2;

        int z; // violation

        Object obj = new Object(); // violation

        int k = 0;

        String x = obj.toString(); // violation

        k++;

        k = 2;

        Runnable runnable = new Runnable() // violation
        {
            public void run()
            {
                int q = 0; // violation
            }
        };
    }

    public void method(int aArg, final int aFinal, int aArg2)
    {
        int z = 0;

        z++;

        aArg2++;
    }

    public void aMethod()
    {
        int i = 0; // violation

        final int j = 2;

        int z; // violation

        Object obj = new Object(); // violation

        int k = 0;

        String x = obj.toString(); // violation

        k++;

        final class Inner
        {
            public Inner()
            {
                int w = 0; // violation
                Runnable runnable = new Runnable() // violation
                {
                    public void run()
                    {
                    }
                };
            }
        }
    }
}
