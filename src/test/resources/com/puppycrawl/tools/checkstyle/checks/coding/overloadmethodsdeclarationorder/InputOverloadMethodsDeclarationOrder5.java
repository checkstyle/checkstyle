/*
OverloadMethodsDeclarationOrder
modifierGroups = public$, public final, static



*/
package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

class InputOverloadMethodsDeclarationOrder5 {
    private void foo() { } // ok
    void bar() { } // ok
    static void foo(int i) { } // ok
    static final void foo(String baz) { } // ok
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    final void foo(String baz, int i) { }
    final public void foo(String baz, long l) { } // ok
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    private void foo(int i , String baz) { }
    public final void foo(int i, long l) { } // ok
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    void foo(long l) { }
}

interface InputOverloadMethodsDeclarationOrder5_2 {
    void foo(); // ok
    abstract void foo(int i); // ok
    void bar(); // ok
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    public abstract void foo(long l);
    static public void foo(String baz) { } // ok
    static void foo(long l, byte b) { } // ok
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    public default void foo(byte b) { }
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    private static void foo( int i, String bar) { }
}
