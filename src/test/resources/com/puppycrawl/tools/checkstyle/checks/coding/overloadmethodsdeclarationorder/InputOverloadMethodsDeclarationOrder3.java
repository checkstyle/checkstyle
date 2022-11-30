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
    void foo() {} // ok
    void foo(String baz) { } // ok
    void bar() { } // ok
    static void foo(int a) { } // ok
    static void bar(int a) { } // ok
}

class InputOverloadMethodsDeclarationOrder3_2 {
    static private void foo(long b) { } // ok
    void foo() {  } // ok
    void foo(String baz) { } // ok
    void bar() {  }
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    private static void foo(int a) { } // ok
    private static void bar(int a) { } // ok
}

class InputOverloadMethodsDeclarationOrder3_3 {
    public void foo(long b) { } // ok
    public void bar(String baz) { } // ok
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    private void foo(String baz) { }
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    void bar() { }
    static public void foo(int a) { } // ok
}

class InputOverloadMethodsDeclarationOrder3_4 {
    public void foo() { } // ok
    private void bar(String baz) { } // ok
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    private void foo(String baz) { }
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    void bar() { }
    static protected final void foo(int a) { } // ok
}

class InputOverloadMethodsDeclarationOrder3_5 {
    private void foo() { } // ok
    void bar() { } // ok
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    public void foo(String baz) { }
    public void foo(CharSequence baz) { } // ok
    private static void foo(int i) { } // ok
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    private final void foo(long i) { }
}

abstract class InputOverloadMethodsDeclarationOrder3_6 {
    protected void foo(double d) { } // ok
    void bar() { } // ok
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    protected abstract void foo(); // ok
    void foo(int i) { } // ok
    public final void foo(long b) { } // ok
}

abstract class InputOverloadMethodsDeclarationOrder3_7 {
    private void foo(char c) { } // ok
    void bar() { } // ok
    static void foo(double d) { } // ok
    static final int foo() {return  0;} // ok
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    final void foo(String baz) { }
    final public void foo(UUID baz) { } // ok
    private void foo(int i) { } // ok
    public final void foo(long i) { } // ok
    abstract void foo(Error error); // ok
}

abstract class InputOverloadMethodsDeclarationOrder3_8 {
    private void foo(char c) { } // ok
    void bar() { } // ok
    static void foo(long b) { } // ok
    static final void foo(float f) { } // ok
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    final void foo(String baz) { }
    public void foo(Path baz) { } // ok
    private void foo(int i) { } // ok
    public @Deprecated final void foo(List<Long> list) { } // ok
    abstract void foo(Map<Integer, Long> map); // ok
}

interface InputOverloadMethodsDeclarationOrder3_9 {
    public abstract void foo(); // ok
    void foo(int i); // ok
    public abstract void foo(String bar); // ok
    private static void foo(int i, String bar) { } // ok
    // violation below 'All overloaded methods.*Previous overloaded method located at.*'
    private void foo(long l) { }
}
