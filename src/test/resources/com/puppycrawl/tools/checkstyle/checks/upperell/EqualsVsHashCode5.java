package com.puppycrawl.tools.checkstyle.checks.upperell;

public class EqualsVsHashCode5
{
    public <A> boolean equals(int a) // wrong arg type, don't flag even with generics
    {
        return a == 1;
    }
}
