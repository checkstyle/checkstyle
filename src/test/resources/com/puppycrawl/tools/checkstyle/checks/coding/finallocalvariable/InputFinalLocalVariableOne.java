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
        // violation below "Variable 'runnable' should be declared final"
        Runnable runnable = new Runnable()
        {
            public void run()
            {
            }
        };
    }
    /** constructor */
    public InputFinalLocalVariableOne()
    {
        int i = 0; // violation, "Variable 'i' should be declared final"
        // final variable
        final int j = 2;

        int z; // violation, "Variable 'z' should be declared final"

        Object obj = new Object(); // violation, "Variable 'obj' should be declared final"

        int k = 0;

        String x = obj.toString(); // violation, "Variable 'x' should be declared final"

        k++;

        k = 2;
        // violation below "Variable 'runnable' should be declared final"
        Runnable runnable = new Runnable()
        {
            public void run()
            {
                int q = 0; // violation, "Variable 'q' should be declared final"
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
        int i = 0; // violation, "Variable 'i' should be declared final"

        final int j = 2;

        int z; // violation, "Variable 'z' should be declared final"

        Object obj = new Object(); // violation, "Variable 'obj' should be declared final"

        int k = 0;

        String x = obj.toString(); // violation, "Variable 'x' should be declared final"

        k++;

        final class Inner
        {
            public Inner()
            {
                int w = 0; // violation, "Variable 'w' should be declared final"
                // violation below  "Variable 'runnable' should be declared final"
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
