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
    final void foo(String baz, int i) {  } // violation 'All overloaded methods should be placed'
    final public void foo(String baz, long l) { } // ok
    private void foo(int i , String baz) { } // violation 'All overloaded methods should be placed'
    public final void foo(int i, long l) { } // ok
    void foo(long l) { } // violation 'All overloaded methods should be placed'
}

interface InputOverloadMethodsDeclarationOrder5_2 {
    void foo(); // ok

    abstract void foo(int i); // ok

    void bar(); // ok

    //violation below 'All overloaded methods should be placed next to each other'
    public abstract void foo(long l);

    static public void foo(String baz) { } // ok

    //violation below 'All overloaded methods should be placed next to each other'
    public default void foo(byte b) { }
}
