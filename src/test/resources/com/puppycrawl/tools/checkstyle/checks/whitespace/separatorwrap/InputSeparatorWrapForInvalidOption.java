/*
SeparatorWrap
option = invalid_option
tokens = (default)DOT, COMMA


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.separatorwrap;

public class InputSeparatorWrapForInvalidOption<T extends FooForInvalidOption
        & BarForInvalidOption> {
    public void goodCase() throws FooException4IO, BarException4IO
    {
        int i = 0;
        String s = "ffffooooString";
        s
                .isEmpty(); //good wrapping
        s.isEmpty();
        try {
            foo(i, s);
        } catch (FooException4IO |
                BarException4IO e) {}
        foo(i,
                s); //good wrapping
    }
    public static void foo(int i, String s) throws FooException4IO, BarException4IO
    {

    }
}

class badCaseForInvalidOption<T extends FooForInvalidOption &  BarForInvalidOption> {


    public void goodCaseForInvalidOption(int... aFoo) throws FooException4IO, BarException4IO
    {
        String s = "ffffooooString";
        s.
                isEmpty(); //bad wrapping
        try {
            foo(1, s);
        } catch (FooException4IO
                | BarException4IO e) {}

        foo(1
                ,s);  //bad wrapping
        int[] i;
    }
    public static String foo(int i, String s) throws FooException4IO, BarException4IO
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

class FooException4IO extends Exception {

}

class BarException4IO extends Exception {

}
