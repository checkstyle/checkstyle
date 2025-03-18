package com.puppycrawl.tools.checkstyle.checks.upperell;

public class EqualsVsHashCode6
{
    public <A> boolean equals(Comparable<A> a) // flag, weven with generics
    {
        return true;
    }
}
