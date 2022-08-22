/*
OverloadMethodsDeclarationOrder
modifierGroups = public final



*/
package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

class InputOverloadMethodsDeclarationOrder5 {
    public final void foo() { } // ok
    protected void foo(String baz) { } // ok
    void foo(int a) { } // ok
    final public void foo(int i, int j) { } // ok
}

class InputOverloadMethodsDeclarationOrder5_2 {
    public final void foo() { } // ok
    protected void foo(String s) { } // ok
    final public void foo(int i, int j) { } // ok
    public final void foo(String s, String t) { } // violation
    void foo(byte b) { } // violation
}
