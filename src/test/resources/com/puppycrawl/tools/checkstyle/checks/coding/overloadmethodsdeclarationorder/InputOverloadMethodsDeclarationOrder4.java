/*
OverloadMethodsDeclarationOrder
modifierGroups = static, public|protected|package



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
    protected void foo(byte b, int i) { } // violation
    private static void foo(int a) { } // violation
    protected static void foo(int a, Path p) { }
    static void foo(long l, int i) { }
    private void foo(byte b) { } // violation
}
