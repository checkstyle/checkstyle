package com.puppycrawl.tools.checkstyle.usage;

/** Test input for unread method check */
public class InputUnusedMethod
{

    private void methodUnused0()
    {
    }

    private void methodUsed0()
    {
    }
    
    private void methodUsed1(int aParam)
    {
    }
    
    private void methodUsed1(double aParam)
    {
    }
    
    private void methodUsed1(String aParam)
    {
    }
    
    public static void main(String[] args)
    {
        InputUnusedMethod method = new InputUnusedMethod();
        method.methodUsed0();
        method.methodUsed1(0 + 4);
        method.methodUsed1(Math.sqrt(2.0));
        method.methodUsed1("" + "a");
    }
}

interface InterfaceMethod
{
    public void method(int aParam);
}

abstract class AbstractClassMethod
{
    public abstract void method(int aParam);
}
