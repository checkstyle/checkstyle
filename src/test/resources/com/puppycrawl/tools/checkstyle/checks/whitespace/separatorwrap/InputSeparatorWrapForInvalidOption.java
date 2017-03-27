package com.puppycrawl.tools.checkstyle.checks.whitespace.separatorwrap;

public class InputSeparatorWrapForInvalidOption<T extends FooForInvalidOption
        & BarForInvalidOption> {
    public void goodCase() throws FooExceptionForInvalidOption, BarExceptionForInvalidOption
    {
        int i = 0;
        String s = "ffffooooString";
        s
                .isEmpty(); //good wrapping
        s.isEmpty();
        try {
            foo(i, s);
        } catch (FooExceptionForInvalidOption |
                BarExceptionForInvalidOption e) {}
        foo(i,
                s); //good wrapping
    }
    public static void foo(int i, String s) throws FooExceptionForInvalidOption, BarExceptionForInvalidOption
    {

    }
}

class badCaseForInvalidOption<T extends FooForInvalidOption &  BarForInvalidOption> {


    public void goodCaseForInvalidOption(int... aFoo) throws FooExceptionForInvalidOption, BarExceptionForInvalidOption
    {
        String s = "ffffooooString";
        s.
                isEmpty(); //bad wrapping
        try {
            foo(1, s);
        } catch (FooExceptionForInvalidOption
                | BarExceptionForInvalidOption e) {}

        foo(1
                ,s);  //bad wrapping
        int[] i;
    }
    public static String foo(int i, String s) throws FooExceptionForInvalidOption, BarExceptionForInvalidOption
    {
        return new StringBuilder("")
                .append("", 0, 1)
                .append("")
                .toString();
    }
}

interface FooForInvalidOption {

}

interface BarForInvalidOption {

}

class FooExceptionForInvalidOption extends Exception {

}

class BarExceptionForInvalidOption extends Exception {

}
