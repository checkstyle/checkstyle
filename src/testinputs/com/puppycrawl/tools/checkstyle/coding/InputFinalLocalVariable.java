package com.puppycrawl.tools.checkstyle.coding;

public class InputFinalLocalVariable
{
    private int m_ClassVariable = 0;

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
    
    public InputFinalLocalVariable()
    {
        int i = 0;

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

    public void anotherMethod()
    {
        boolean aBool = true;
        for (int i = 0, j = 1, k = 1; j < 10 ; j++)
        {
            k++;
            aBool = false;
        }
        
        int l = 0;
        {
            int weird = 0;
            int j = 0;
            int k = 0;
            {
               l++;
            }
        }

        int weird = 0;
        weird++;

        final InnerClass ic = new InnerClass();

        ic.mInner2 = 1;
    }

    class InnerClass
    {
        private int mInner = 0;

        public int mInner2 = 0;
    }
}

interface Inter
{
    void method(int aParam);
}

abstract class AbstractClass
{
    public abstract void abstractMethod(int aParam);
}

class Blah
{
    static
    {
        for(int a : some.getInts())
        {

        }
    }
}
