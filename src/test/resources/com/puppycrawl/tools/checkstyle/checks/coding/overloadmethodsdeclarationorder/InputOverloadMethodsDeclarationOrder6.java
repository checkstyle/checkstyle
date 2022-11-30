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
    public void foo() { } 
    private void bar(String baz) { } 
    private void foo(String baz) { } 
    void bar() { } 
    static protected final void foo(int a) { } 
}

class InputOverloadMethodsDeclarationOrder6_2 {
    static private void foo(long b) { } 
    void foo() { } 
    void foo(String baz) { } 
    void bar() { }
    //violation below 'All overloaded methods should be placed next to each other'
    static void foo(int a) { }
}

class InputOverloadMethodsDeclarationOrder6_3 {
    public void foo(long b) { }
    public void bar(String baz) { }
    private void foo(String baz) { }
    public static void foo(byte b) { }
    void bar() { }
    //violation below 'All overloaded methods should be placed next to each other'
    static public void foo(int a) { }
}

class InputOverloadMethodsDeclarationOrder6_4 {
    void foo() { }
    void foo(String baz) { }
    void bar() { }
    //violation below 'All overloaded methods should be placed next to each other'
    static void foo(int a) { }
}

class InputOverloadMethodsDeclarationOrder6_5 {
    private void foo() { }
    void bar() { }
    public void foo(String baz) { }
    public void foo(CharSequence baz) { }
    //violation below 'All overloaded methods should be placed next to each other'
    private static void foo(int i) { }
    private final void foo(long i) { }
}

abstract class InputOverloadMethodsDeclarationOrder6_6 {
    protected void foo(double d) { }
    void bar() { }
    //violation below 'All overloaded methods should be placed next to each other'
    protected abstract void foo();
    void foo(int i) { }
    public final void foo(long b) { }
}

abstract class InputOverloadMethodsDeclarationOrder6_7 {
    private void foo(char c) { } 
    void bar() { } 
    static void foo(double d) { } 
    static final int foo() {return  0;} 
    final void foo(String baz) { } 
    final public void foo(UUID baz) { }
    //violation below 'All overloaded methods should be placed next to each other'
    private void foo(int i) { }
    public final void foo(long i) { }
    //violation below 'All overloaded methods should be placed next to each other'
    abstract void foo(Error error);
}

abstract class InputOverloadMethodsDeclarationOrder6_8 {
    private void foo(char c) { }
    void bar() { }
    static void foo(long b) { }
    static final void foo(float f) { }
    final void foo(String baz) { }
    public void foo(Path baz) { }
    public @Deprecated void foo(byte b) { }
    //violation below 'All overloaded methods should be placed next to each other'
    private void foo(int i) { }
    public @Deprecated final void foo(List<Long> list) { }
    //violation below 'All overloaded methods should be placed next to each other'
    abstract void foo(Map<Integer, Long> map);
}

interface InputOverloadMethodsDeclarationOrder6_9 {
    void foo();
    abstract void foo(int i);
    void bar();
    //violation below 'All overloaded methods should be placed next to each other'
    public abstract void foo(long l);
    abstract public void foo(String baz);
}
