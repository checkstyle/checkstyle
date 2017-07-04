package com.puppycrawl.tools.checkstyle.grammars.java8;
import java.util.function.Function;
import java.util.function.Supplier;


public class InputMethodReferences2
{

    public static void main(String[] args)
    {

        Supplier<InputMethodReferences2> supplier = InputMethodReferences2::new;
        Supplier<InputMethodReferences2> suppl = InputMethodReferences2::<Integer> new;
        Function<Integer, String[]> messageArrayFactory = String[]::new;

    }

    private class Bar<T>
    {

    }
}
