/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = PARAMETER_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariable2One {
    private int m_ClassVariable = 0;
    //static block
    static
    {
        int i, j = 0;
        Runnable runnable = new Runnable()
        {
            public void run()
            {
            }
        };
    }
    /** constructor */
    public InputFinalLocalVariable2One()
    {
        int i = 0;
        // final variable
        final int j = 2;

        int z;

        Object obj = new Object();

        int k = 0;

        String x = obj.toString();

        k++;

        k = 2;

        Runnable runnable = new Runnable()
        {
            public void run()
            {
                int q = 0;
            }
        };
    }

    // violation below "Variable 'aArg' should be declared final"
    public void method(int aArg, final int aFinal, int aArg2)
    {
        int z = 0;

        z++;

        aArg2++;
    }

    public void aMethod()
    {
        int i = 0;

        final int j = 2;

        int z;

        Object obj = new Object();

        int k = 0;

        String x = obj.toString();

        k++;

        final class Inner
        {
            public Inner()
            {
                int w = 0;
                Runnable runnable = new Runnable()
                {
                    public void run()
                    {
                    }
                };
            }
        }
    }
}
