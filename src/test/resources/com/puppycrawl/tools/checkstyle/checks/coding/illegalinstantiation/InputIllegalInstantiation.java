package com.puppycrawl.tools.checkstyle.checks.coding.illegalinstantiation;

import java.util.function.Function;
import java.util.function.Supplier;


public class InputIllegalInstantiation
{

    public static void main(String[] args)
    {

        Supplier<InputMethodReferencesTest2> supplier = InputMethodReferencesTest2::new;
        Function<Integer, String[]> messageArrayFactory = String[]::new;

    }

    private static class InputMethodReferencesTest2<T>
    {

    }
}
