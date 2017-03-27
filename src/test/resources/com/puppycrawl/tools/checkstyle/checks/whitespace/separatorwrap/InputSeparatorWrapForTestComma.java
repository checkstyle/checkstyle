package com.puppycrawl.tools.checkstyle.checks.whitespace.separatorwrap;

public class InputSeparatorWrapForTestComma<T extends FooForTestComma
        & BarForTestComma> {
    public void goodCase() throws FooExceptionForTestComma, BarExceptionForTestComma
    {
        int i = 0;
        String s = "ffffooooString";
        s
                .isEmpty(); //good wrapping
        s.isEmpty();
        try {
            foo(i, s);
        } catch (FooExceptionForTestComma |
                BarExceptionForTestComma e) {}
        foo(i,
                s); //good wrapping
    }
    public static void foo(int i, String s) throws FooExceptionForTestComma, BarExceptionForTestComma
    {

    }
}

class badCaseForTestComma<T extends FooForTestComma &  BarForTestComma> {


    public void goodCaseForTestComma(int... aFoo) throws FooExceptionForTestComma, BarExceptionForTestComma
    {
        String s = "ffffooooString";
        s.
                isEmpty(); //bad wrapping
        try {
            foo(1, s);
        } catch (FooExceptionForTestComma
                | BarExceptionForTestComma e) {}

        foo(1
                ,s);  //bad wrapping
        int[] i;
    }
    public static String foo(int i, String s) throws FooExceptionForTestComma, BarExceptionForTestComma
    {
        return new StringBuilder("")
                .append("", 0, 1)
                .append("")
                .toString();
    }
}

interface FooForTestComma {

}

interface BarForTestComma {

}

class FooExceptionForTestComma extends Exception {

}

class BarExceptionForTestComma extends Exception {

}
