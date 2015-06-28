package com.puppycrawl.tools.checkstyle;

@TestClassAnnotation
class InputRightCurlyAnnotations
{
    private static final int X = 10;
    @Deprecated
    @Override
    public boolean equals(Object other) { return false; }

    @Override
    public String toString() {
        return "InputRightCurlyAnnotations{}";
    }

    public String foo() { return "foo"; }

    @Override
    @SuppressWarnings("unused")
    public int hashCode()
    {
        int a = 10;
        return 1;
    }
}
