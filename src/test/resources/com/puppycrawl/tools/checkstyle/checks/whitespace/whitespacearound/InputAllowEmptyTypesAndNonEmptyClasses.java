package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

import java.util.function.Function;
import java.util.function.Supplier;

public class InputAllowEmptyTypesAndNonEmptyClasses{

    private Object object;

    class SomeClass{
        int a = 5;
    }

    public class CheckstyleTest{
        private static final int SOMETHING = 1;
    }

    class MyClass{ int a; }

    class SomeTestClass{int a;}

    class TestClass { int a; }int b;

    class Table {}

    interface SupplierFunction<T> extends Function<Supplier<T>, T> {}

    class NotEmptyClass{ public void foo1() { foo2(); } }

    public void foo2() {
        do {} while (true);
    }
}
