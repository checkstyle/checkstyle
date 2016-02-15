//Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.function.Function;
import java.util.function.Supplier;

public class InputIllegalInstantiation2
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
