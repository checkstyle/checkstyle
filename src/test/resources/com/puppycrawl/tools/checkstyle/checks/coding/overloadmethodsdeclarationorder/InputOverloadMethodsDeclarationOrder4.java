/*
OverloadMethodsDeclarationOrder
modifierGroups = static, abstract, public|protected|package



*/
package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

import java.nio.file.Path;

class InputOverloadMethodsDeclarationOrder4 {
    public void foo() { } // ok
    protected void foo(String baz) { } // ok
    void foo(int a) { } // ok
    void bar() {} // ok
    static void foo(byte b) { } // ok
    static void bar(int a) { } // ok
    private void foo(String s, int i) { } // ok
}

class InputOverloadMethodsDeclarationOrder4_2 {
    static private void foo(long b) { } // ok
    void foo() { } // ok
    public void foo(String baz) { } // ok
    private void foo(String s, int i) { } // ok
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    protected void foo(byte b, int i) { }
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    private static void foo(int a) { } // ok
    protected static void foo(int a, Path p) { } // ok
    static void foo(long l, int i) { }
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    private void foo(byte b) { } // ok
}

interface InputOverloadMethodsDeclarationOrder4_3 {
    public void foo(); // ok
    void foo(int i); // ok
    abstract void foo(String bar); // ok
    private static void foo(byte b) { } // ok
    void bar(long l); // ok
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    void foo(long l); // ok
    private void foo(int i, String bar) { } // ok
}
