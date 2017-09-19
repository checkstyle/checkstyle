package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

public class InputIllegalTypeGenerics {
    private MyClass1<Boolean> b; //WARNING
    private java.util.List<MyClass1<Boolean>> lb; //WARNING
    private class MyClass1<T> {
        T a;
        T[] b;
        T[][] c;
    }

    private void foo(){
        Bounded.<Boolean>foo(); //WARNING
    }

    public <T extends Boolean> void foo(T a) {} //WARNING

    public void foo2(java.util.ArrayList<? super Boolean> a) {} //WARNING

    private class MyClass2<T extends Boolean> {}

    public Foo<? extends Foo> foo3() { //2 WARNINGS
        return new Foo<>();
    }
}

class Bounded {
    public static <T> void foo() {}
}

class Foo<T extends Foo> {}
