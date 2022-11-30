/*
OverloadMethodsDeclarationOrder
modifierGroups = public$, package, public abstract$, private$

*/

package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

class InputOverloadMethodsDeclarationOrder8
{
    public void overloadMethod(int i) { } // ok

    void overloadMethod(boolean b) { } // ok

    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    public void overloadMethod() { }

    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    void overloadMethod(String s, Boolean b, int i) { }
}

interface InputOverloadMethodsDeclarationOrder8_2
{
    public abstract void foo(int i); // ok

    void foo(String s); // ok

    default void foo(byte b) { } // ok

    private void foo(long l) { } // ok

    private void foo(long l, long m) { } // ok

    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    void foo(int i, String s);

    abstract void foo(long l, byte b); // ok

    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    public default void foo(long l, String s) { }

    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    public void foo(int i, long l);

    void foo(int i, int x); // ok

    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    static void foo(String s, byte b) { }
}
