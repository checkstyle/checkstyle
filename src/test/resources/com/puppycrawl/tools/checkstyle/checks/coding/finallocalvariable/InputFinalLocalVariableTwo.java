/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = (default)VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableTwo {

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
            int weird = 0; // violation, "Variable 'weird' should be declared final"
            int j = 0; // violation, "Variable 'j' should be declared final"
            int k = 0; // violation, "Variable 'k' should be declared final"
            {
                l++;
            }
        }

        int weird = 0;
        weird++;

        final InputFinalLocalVariableTwo.InnerClass ic
                = new InputFinalLocalVariableTwo.InnerClass();

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
        for(int a : getInts())
        {

        }
    }

    static int[] getInts() {
        return null;
    }
}

class test_1241722
{
    private Object o_;

    public void doSomething(Object _o)
    {
        System.identityHashCode(_o);
    }

    public void doSomething2(Object _o1)
    {
        o_ = _o1;
    }
}

class class1
{
    public class1(final int x){

    }
}

class AA {
    {
        int y = 0;
        y = 9;
    }
}

enum Enum1 {
    ;

    {
        int var = 0;
        var = 1;
    }
}
