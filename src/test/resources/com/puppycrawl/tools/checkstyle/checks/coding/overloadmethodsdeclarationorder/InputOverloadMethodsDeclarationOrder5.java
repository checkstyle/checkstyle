/*
OverloadMethodsDeclarationOrder
modifierGroups = public$, public final, static



*/
package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

class InputOverloadMethodsDeclarationOrder5 {
    private void foo() {  }
    void bar() {  }
    static void foo(int i) { }
    static final void foo(String baz) {}
    final void foo(String baz, int i) {  } // violation 'All overloaded methods should be placed'
    final public void foo(String baz, long l) {  }
    private void foo(int i , String baz) {  } // violation 'All overloaded methods should be placed'
    public final void foo(int i, long l) {  }
    void foo(long l) {} // violation 'All overloaded methods should be placed'
}

interface InputOverloadMethodsDeclarationOrder5_2 {
    void foo();

    abstract void foo(int i);

    void bar();

    //violation below 'All overloaded methods should be placed next to each other'
    public abstract void foo(long l);
}
