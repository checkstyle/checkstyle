/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.grammar.java8;
import java.util.function.Function;
import java.util.function.Supplier;


public class InputMethodReferences2 // ok
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
