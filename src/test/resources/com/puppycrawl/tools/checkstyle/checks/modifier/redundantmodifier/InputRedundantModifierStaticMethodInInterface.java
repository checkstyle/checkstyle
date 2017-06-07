package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;


public interface InputRedundantModifierStaticMethodInInterface
{
    static int f()
    {
        int someName = 5;
        return someName;
    }
}
