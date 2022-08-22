/*
OverloadMethodsDeclarationOrder
modifierGroups = ^public .*, (protected|package), private, ^static$



*/
package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.UUID;

class InputOverloadMethodsDeclarationOrder6 {
    public void foo() { } // ok
    private void bar(String baz) { } // ok
    private void foo(String baz) { } // ok
    void bar() { } // ok
    static protected final void foo(int a) { } // ok
}

class InputOverloadMethodsDeclarationOrder6_2 {
    static private void foo(long b) { } // ok
    void foo() { } // ok
    void foo(String baz) { } // ok
    void bar() { } // ok
    static void foo(int a) { } // violation
}

class InputOverloadMethodsDeclarationOrder6_3 {
    public void foo(long b) { } // ok
    public void bar(String baz) { } // ok
    private void foo(String baz) { } // ok
    public static void foo(byte b) { } // ok
    void bar() { } // ok
    static public void foo(int a) { } // violation
}

class InputOverloadMethodsDeclarationOrder6_4 {
    void foo() { } // ok
    void foo(String baz) { } // ok
    void bar() { } // ok
    static void foo(int a) { } // violation
}

class InputOverloadMethodsDeclarationOrder6_5 {
    private void foo() { } // ok, 2nd group
    void bar() { } // ok
    public void foo(String baz) { } //ok
    public void foo(CharSequence baz) { } // ok
    private static void foo(int i) { } // violation
    private final void foo(long i) { } // violation
}

abstract class InputOverloadMethodsDeclarationOrder6_6 {
    protected void foo(double d) { } // ok
    void bar() { }
    protected abstract void foo(); // violation
    void foo(int i) { } // violation
    public final void foo(long b) { } // ok
}

abstract class InputOverloadMethodsDeclarationOrder6_7 {
    private void foo(char c) { } // ok
    void bar() { } // ok
    static void foo(double d) { } // ok
    static final int foo() {return  0;} // ok
    final void foo(String baz) { } // ok
    final public void foo(UUID baz) { } // ok
    private void foo(int i) { } // violation
    public final void foo(long i) { } // ok
    abstract void foo(Error error); // violation
}

abstract class InputOverloadMethodsDeclarationOrder6_8 {
    private void foo(char c) { } // ok
    void bar() { } // ok
    static void foo(long b) { } // ok
    static final void foo(float f) { } // ok
    final void foo(String baz) { } // ok
    public void foo(Path baz) { } // ok
    private void foo(int i) { } // violation
    public @Deprecated final void foo(List<Long> list) { } // ok
    abstract void foo(Map<Integer, Long> map); // violation
}
