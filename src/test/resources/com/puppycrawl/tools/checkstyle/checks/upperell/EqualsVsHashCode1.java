package com.puppycrawl.tools.checkstyle.checks.upperell;

public class EqualsVsHashCode1
{
    public boolean equals(int a) // wrong arg type, don't flag
    {
        return a == 1;
    }
}
