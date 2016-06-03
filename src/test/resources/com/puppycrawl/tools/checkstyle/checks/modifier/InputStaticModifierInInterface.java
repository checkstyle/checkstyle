//Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.modifier;

public interface InputStaticModifierInInterface
{
    static int f()
    {
        int someName = 5;
        return someName;
    }
}
