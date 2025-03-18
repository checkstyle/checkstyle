package com.puppycrawl.tools.checkstyle.checks.upperell;

public class InputUpperEllEqualsVsHashCode6
{
    public <A> boolean equals(Comparable<A> a) // flag, weven with generics
    {
        return true;
    }
}
