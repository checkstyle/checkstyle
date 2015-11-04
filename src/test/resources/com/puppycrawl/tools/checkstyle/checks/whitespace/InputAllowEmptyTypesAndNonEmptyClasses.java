package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.google.common.base.Function;
import com.google.common.base.Supplier;

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
