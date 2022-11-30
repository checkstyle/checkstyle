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
    //violation below 'All overloaded methods should be placed next to each other'
    static void foo(int a) { }
}

class InputOverloadMethodsDeclarationOrder6_3 {
    public void foo(long b) { } // ok
    public void bar(String baz) { } // ok
    private void foo(String baz) { } // ok
    public static void foo(byte b) { } // ok
    void bar() { } // ok
    //violation below 'All overloaded methods should be placed next to each other'
    static public void foo(int a) { }
}

class InputOverloadMethodsDeclarationOrder6_4 {
    void foo() { } // ok
    void foo(String baz) { } // ok
    void bar() { } // ok
    //violation below 'All overloaded methods should be placed next to each other'
    static void foo(int a) { }
}

class InputOverloadMethodsDeclarationOrder6_5 {
    private void foo() { } // ok
    void bar() { } // ok
    public void foo(String baz) { } // ok
    public void foo(CharSequence baz) { } // ok
    //violation below 'All overloaded methods should be placed next to each other'
    private static void foo(int i) { }
    private final void foo(long i) { } // ok
}

abstract class InputOverloadMethodsDeclarationOrder6_6 {
    protected void foo(double d) { } // ok
    void bar() { } // ok
    //violation below 'All overloaded methods should be placed next to each other'
    protected abstract void foo();
    void foo(int i) { } // ok
    public final void foo(long b) { } // ok
}

abstract class InputOverloadMethodsDeclarationOrder6_7 {
    private void foo(char c) { } // ok
    void bar() { } // ok
    static void foo(double d) { } // ok
    static final int foo() {return  0;} // ok
    final void foo(String baz) { } // ok
    final public void foo(UUID baz) { } // ok
    //violation below 'All overloaded methods should be placed next to each other'
    private void foo(int i) { }
    public final void foo(long i) { } // ok
    //violation below 'All overloaded methods should be placed next to each other'
    abstract void foo(Error error);
}

abstract class InputOverloadMethodsDeclarationOrder6_8 {
    private void foo(char c) { } // ok
    void bar() { } // ok
    static void foo(long b) { } // ok
    static final void foo(float f) { } // ok
    final void foo(String baz) { } // ok
    public void foo(Path baz) { } // ok
    public @Deprecated void foo(byte b) { } // ok
    //violation below 'All overloaded methods should be placed next to each other'
    private void foo(int i) { }
    public @Deprecated final void foo(List<Long> list) { } // ok
    //violation below 'All overloaded methods should be placed next to each other'
    abstract void foo(Map<Integer, Long> map);
}

interface InputOverloadMethodsDeclarationOrder6_9 {
    void foo(); // ok
    abstract void foo(int i); // ok
    void bar(); // ok
    //violation below 'All overloaded methods should be placed next to each other'
    public abstract void foo(long l);
    abstract public void foo(String baz); // ok
}
