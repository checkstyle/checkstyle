package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// modifierGroups = "static"
class Test_Static1 {
    void foo() {}
    void foo(String baz) {}
    void bar() {} // OK, bar group
    static void foo(int a) {} // OK, as "static foo" is separate group
}

// modifierGroups = "static"
class Test_Static2 {
    static private void foo(long b) {  }
    void foo() {  } // OK
    void foo(String baz) {  } //OK
    void bar() {  }
    static void foo(int a) {  } // WARN - "static foo" methods are not grouped
}

// modifierGroups = "static"
class Test_Static3 {
    public void foo(long b) {  } //OK
    public void bar(String baz) {  } //OK
    private void foo(String baz) {  } //VIOLATION private foo(String) should be next to foo()
    void bar() {  } // VIOLATION bar should be next to bar(String)
    static public void foo(int a) {  } // OK, "static foo" is separate group.
}

// modifierGroups = "static"
class Test_Static4 {
    public void foo() {  } //OK
    private void bar(String baz) {  } //OK
    private void foo(String baz) {  } //VIOLATION private foo(String) should be next to foo()
    void bar() {  } // VIOLATION bar should be next to bar(String)
    static protected final void foo(int a) {  } // OK, static foo is it's own group.
}

//modifierGroups = "protected, private, package"
class Test_Protected_Private_package1 {
    private void foo() {  } //OK, 2nd group
    void bar() {  }
    public void foo(String baz) {  } //OK, "public foo" does not match special groups so it is default group
    public void foo(CharSequence baz) {  } //OK
    private static void foo(int i) {  } // VIOLATION, matches 2nd group, should be close to first method
    private final void foo(long i) {  } // VIOLATION, matches 2nd group, should be close to first method
}

//modifierGroups = "protected, private, package"
abstract class Test_Protected_Private_package2 {
    protected void foo(double d) {  } //OK, matches 1st group
    void bar() {  }
    protected abstract void foo(); //VIOLATION, matches 1st group
    void foo(int i) {  } //OK, matches 3rd group
    public final void foo(long b) {  } // OK, "public final " does not match special groups so it is default group
}

//modifierGroups = "public, public final, static"
abstract class Test_Public_PublicFinal_Static {
    private void foo(char c) {  } // default group
    void bar() {  } // default group
    static void foo(double d) {} // OK, 3rd group
    static final int foo() {return  0;} // OK, matches to 3rd group
    final void foo(String baz) {  } //VIOLATION, matching to default group
    final public void foo(UUID baz) {  } //OK, 1st group, Attention: does not match second group, as strict order of modifiers is in regexp
    private void foo(int i) {  } // VIOLATION, default group, should be close to first method
    public final void foo(long i) {  } // OK, 2nd group
    abstract void foo(Error error); //VIOLATION, matches to default, should be close to first method
}

//modifierGroups = RegExp[] = "^public .*, (protected|package), private, ^static$"
abstract class Test_Public_ProtectedOrPackage_Private_onlyStatic {
    private void foo(char c) {  } // 3rd group
    void bar() {  } // second group
    static void foo(long b) {} // OK, 4th group
    static final void foo(float f) {} // OK, matches to default group
    final void foo(String baz) {  } //OK, matching to default group
    public void foo(Path baz) {  } //OK, first group
    private void foo(int i) {  } // VIOLATION, 3rd group, should be close to first method
    public @Deprecated final void foo(List<Long> list) {  } // VIOLATION, matches to first group
    abstract void foo(Map<Integer, Long> map); //VIOLATION, matches to default
}
