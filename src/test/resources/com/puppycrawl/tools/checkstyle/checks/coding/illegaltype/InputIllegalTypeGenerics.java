package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

/**
 * This test-input is intended to be checked using following configuration:
 *
 * illegalClassNames = {Boolean, Foo, Serializable}
 * memberModifiers = {LITERAL_PUBLIC, FINAL}
 *
 */
public abstract class InputIllegalTypeGenerics {

    private Set<Boolean> privateSet; // OK
    private java.util.List<Map<Boolean, Foo>> privateList; // OK
    public Set<Boolean> set; // warn
    public java.util.List<Map<Boolean, Foo>> list; // warn

    private void methodCall() {
        Bounded.<Boolean>foo(); // warn
        final Consumer<Foo> consumer = Foo<Boolean>::foo; // warn
    }

    public <T extends Boolean, U extends Serializable> void typeParameter(T a) {} // warn

    public void fullName(java.util.ArrayList<? super Boolean> a) {} // warn

    public abstract Set<Boolean> shortName(Set<? super Set<Boolean>> a); // warn

    public Set<? extends Foo<Boolean>> typeArgument() { // warn
        return new TreeSet<Foo<Boolean>>(); // OK
    }

    public class MyClass<Foo extends Boolean> {} // warn

}

class Bounded {

    public boolean match = new TreeSet<Integer>().stream()
            .allMatch(new TreeSet<>()::add); // OK

    public static <Boolean> void foo() {} // warn

}

class Foo<T extends Boolean & Serializable> { // OK

    void foo() {}

}

@interface Annotation {

    Class<? extends Boolean>[] nonPublic(); // OK
    public Class<? extends Boolean>[] value(); // warn

}
