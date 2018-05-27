package com.puppycrawl.tools.checkstyle.checks.whitespace.separatorwrap;

public class InputSeparatorWrapForTestComma<T extends FooForTestComma
        & BarForTestComma> {
    public void goodCase() throws FooException4TC, BarException4TC
    {
        int i = 0;
        String s = "ffffooooString";
        s
                .isEmpty(); //good wrapping
        s.isEmpty();
        try {
            foo(i, s);
        } catch (FooException4TC |
                BarException4TC e) {}
        foo(i,
                s); //good wrapping
    }
    public static void foo(int i, String s) throws FooException4TC, BarException4TC
    {

    }
}

class badCaseForTestComma<T extends FooForTestComma &  BarForTestComma> {


    public void goodCaseForTestComma(int... aFoo) throws FooException4TC, BarException4TC
    {
        String s = "ffffooooString";
        s.
                isEmpty(); //bad wrapping
        try {
            foo(1, s);
        } catch (FooException4TC
                | BarException4TC e) {}

        foo(1
                ,s);  //bad wrapping
        int[] i;
    }
    public static String foo(int i, String s) throws FooException4TC, BarException4TC
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

class FooException4TC extends Exception {

}

class BarException4TC extends Exception {

}
