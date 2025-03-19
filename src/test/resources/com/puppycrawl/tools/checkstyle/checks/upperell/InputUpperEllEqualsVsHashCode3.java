package com.puppycrawl.tools.checkstyle.checks.upperell;

public class InputUpperEllEqualsVsHashCode3
{
    public boolean equals(Object a) // don't flag
    {
        return true;
    }

    public int hashCode()
    {
        return 0;
    }
}
