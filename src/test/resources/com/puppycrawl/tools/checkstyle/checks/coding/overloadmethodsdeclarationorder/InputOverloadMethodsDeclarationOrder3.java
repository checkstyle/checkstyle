/*
OverloadMethodsDeclarationOrder
modifierGroups = static



*/
package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.UUID;

class InputOverloadMethodsDeclarationOrder3 {
    void foo() {}
    void foo(String baz) { }
    void bar() {}
    static void foo(int a) { }
    static void bar(int a) { }
}

class InputOverloadMethodsDeclarationOrder3_2 {
    static private void foo(long b) { }
    void foo() {  }
    void foo(String baz) { }
    void bar() {  }
    //violation below 'All overloaded methods should be placed next to each other'
    private static void foo(int a) { }
    private static void bar(int a) { }
}

class InputOverloadMethodsDeclarationOrder3_3 {
    public void foo(long b) { }
    public void bar(String baz) { }
    //violation below 'All overloaded methods should be placed next to each other'
    private void foo(String baz) { }
    //violation below 'All overloaded methods should be placed next to each other'
    void bar() {  }
    static public void foo(int a) { }
}

class InputOverloadMethodsDeclarationOrder3_4 {
    public void foo() { }
    private void bar(String baz) { }
    //violation below 'All overloaded methods should be placed next to each other'
    private void foo(String baz) { }
    //violation below 'All overloaded methods should be placed next to each other'
    void bar() { }
    static protected final void foo(int a) { }
}

class InputOverloadMethodsDeclarationOrder3_5 {
    private void foo() { }
    void bar() { }
    //violation below 'All overloaded methods should be placed next to each other'
    public void foo(String baz) { }
    public void foo(CharSequence baz) { }
    private static void foo(int i) { }
    //violation below 'All overloaded methods should be placed next to each other'
    private final void foo(long i) { }
}

abstract class InputOverloadMethodsDeclarationOrder3_6 {
    protected void foo(double d) { }
    void bar() { }
    //violation below 'All overloaded methods should be placed next to each other'
    protected abstract void foo();
    void foo(int i) { }
    public final void foo(long b) { }
}

abstract class InputOverloadMethodsDeclarationOrder3_7 {
    private void foo(char c) { }
    void bar() { }
    static void foo(double d) { }
    static final int foo() {return  0;}
    //violation below 'All overloaded methods should be placed next to each other'
    final void foo(String baz) { }
    final public void foo(UUID baz) { }
    private void foo(int i) { }
    public final void foo(long i) { }
    abstract void foo(Error error);
}

abstract class InputOverloadMethodsDeclarationOrder3_8 {
    private void foo(char c) { }
    void bar() { }
    static void foo(long b) { }
    static final void foo(float f) { }
    //violation below 'All overloaded methods should be placed next to each other'
    final void foo(String baz) { }
    public void foo(Path baz) { }
    private void foo(int i) { }
    public @Deprecated final void foo(List<Long> list) { }
    abstract void foo(Map<Integer, Long> map);
}

interface InputOverloadMethodsDeclarationOrder3_9 {
    public abstract void foo();

    void foo(int i);

    public abstract void foo(String bar);
}
