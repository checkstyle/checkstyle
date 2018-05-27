package com.puppycrawl.tools.checkstyle.checks.whitespace.separatorwrap;

public class InputSeparatorWrapForTestDot<T extends FooForTestDot
        & BarForTestDot> {
    public void goodCase() throws FooExceptionForTestDot, BarExceptionForTestDot
    {
        int i = 0;
        String s = "ffffooooString";
        s
                .isEmpty(); //good wrapping
        s.isEmpty();
        try {
            foo(i, s);
        } catch (FooExceptionForTestDot |
                BarExceptionForTestDot e) {}
        foo(i,
                s); //good wrapping
    }
    public static void foo(int i, String s) throws FooExceptionForTestDot, BarExceptionForTestDot
    {

    }
}

class badCaseForTestDot<T extends FooForTestDot &  BarForTestDot> {


    public void goodCaseForTestDot(int... aFoo)throws FooExceptionForTestDot, BarExceptionForTestDot
    {
        String s = "ffffooooString";
        s.
                isEmpty(); //bad wrapping
        try {
            foo(1, s);
        } catch (FooExceptionForTestDot
                | BarExceptionForTestDot e) {}

        foo(1
                ,s);  //bad wrapping
        int[] i;
    }
    public static String foo(int i, String s) throws FooExceptionForTestDot, BarExceptionForTestDot
    {
        return new StringBuilder("")
                .append("", 0, 1)
                .append("")
                .toString();
    }
}

interface FooForTestDot {

}

interface BarForTestDot {

}

class FooExceptionForTestDot extends Exception {

}

class BarExceptionForTestDot extends Exception {

}
