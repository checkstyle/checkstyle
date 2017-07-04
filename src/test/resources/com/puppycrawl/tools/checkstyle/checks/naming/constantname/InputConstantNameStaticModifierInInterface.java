package com.puppycrawl.tools.checkstyle.checks.naming.constantname;


public interface InputConstantNameStaticModifierInInterface
{
    static int f()
    {
        int someName = 5;
        return someName;
    }
}
