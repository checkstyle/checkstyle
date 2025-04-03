/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = PARAMETER_DEF
validateUnnamedVariables = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariable2Two {

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

        final InputFinalLocalVariable2Two.InnerClass ic =
                new InputFinalLocalVariable2Two.InnerClass();

        ic.mInner2 = 1;
    }

    class InnerClass
    {
        private int mInner = 0;

        public int mInner2 = 0;
    }
}

interface Inter2
{
    void method(int aParam);
}

abstract class AbstractClass2
{
    public abstract void abstractMethod(int aParam);
}

class Blah2
{
    static
    {
        for(int a : getInts())
        {

        }
    }

    static int[] getInts() {
        return null;
    }
}

class test_12417222
{
    private Object o_;

    public void doSomething(Object _o) // violation, "Variable '_o' should be declared final"
    {
        System.identityHashCode(_o);
    }

    public void doSomething2(Object _o1) // violation, "Variable '_o1' should be declared final"
    {
        o_ = _o1;
    }
}

class class12
{
    public class12(final int x){

    }
}

class AA2 {
    {
        int y = 0;
        y = 9;
    }
}

enum Enum12 {
    ;

    {
        int var = 0;
        var = 1;
    }
}
