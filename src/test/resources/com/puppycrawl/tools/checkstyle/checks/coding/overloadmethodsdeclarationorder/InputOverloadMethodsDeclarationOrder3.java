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
    void bar() {} // ok
    static void foo(int a) { } // ok
    static void bar(int a) { } // ok
}

class InputOverloadMethodsDeclarationOrder3_2 {
    static private void foo(long b) { } // ok
    void foo() {  } // ok
    void foo(String baz) { } //ok
    void bar() {  } // ok
    private static void foo(int a) { } // violation
    private static void bar(int a) { } // ok
}

class InputOverloadMethodsDeclarationOrder3_3 {
    public void foo(long b) { } // ok
    public void bar(String baz) { } // ok
    private void foo(String baz) { } // violation
    void bar() {  } // violation
    static public void foo(int a) { } // ok
}

class InputOverloadMethodsDeclarationOrder3_4 {
    public void foo() { } // ok
    private void bar(String baz) { } // ok
    private void foo(String baz) { } // violation
    void bar() { } // violation
    static protected final void foo(int a) { } // ok
}

class InputOverloadMethodsDeclarationOrder3_5 {
    private void foo() { } // ok
    void bar() { } // ok
    public void foo(String baz) { } // violation
    public void foo(CharSequence baz) { }
    private static void foo(int i) { } // ok
    private final void foo(long i) { } // violation
}

abstract class InputOverloadMethodsDeclarationOrder3_6 {
    protected void foo(double d) { } // ok
    void bar() { } // ok
    protected abstract void foo(); // violation
    void foo(int i) { }
    public final void foo(long b) { }
}

abstract class InputOverloadMethodsDeclarationOrder3_7 {
    private void foo(char c) { } // ok
    void bar() { } // ok
    static void foo(double d) { } // ok
    static final int foo() {return  0;} // ok
    final void foo(String baz) { } // violation
    final public void foo(UUID baz) { }
    private void foo(int i) { }
    public final void foo(long i) { }
    abstract void foo(Error error);
}

abstract class InputOverloadMethodsDeclarationOrder3_8 {
    private void foo(char c) { } // ok
    void bar() { } // ok
    static void foo(long b) { } // ok
    static final void foo(float f) { } // ok
    final void foo(String baz) { } // violation
    public void foo(Path baz) { }
    private void foo(int i) { }
    public @Deprecated final void foo(List<Long> list) { }
    abstract void foo(Map<Integer, Long> map);
}
