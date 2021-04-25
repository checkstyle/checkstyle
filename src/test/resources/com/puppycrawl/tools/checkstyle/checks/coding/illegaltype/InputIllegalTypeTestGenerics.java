package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

/*
 * Config:
 * illegalClassNames = { Boolean, Foo, Serializable }
 * memberModifiers = { LITERAL_PUBLIC, FINAL }
 */
public abstract class InputIllegalTypeTestGenerics {

    private Set<Boolean> privateSet; // OK
    private java.util.List<Map<Boolean, Foo>> privateList; // OK
    public Set<Boolean> set; // violation
    public java.util.List<Map<Boolean, Foo>> list; // violation

    private void methodCall() {
        Bounded.<Boolean>foo(); // violation
        final Consumer<Foo> consumer = Foo<Boolean>::foo; // violation
    }

    public <T extends Boolean, U extends Serializable> void typeParameter(T a) {} // violation

    public void fullName(java.util.ArrayList<? super Boolean> a) {} // violation

    public abstract Set<Boolean> shortName(Set<? super Set<Boolean>> a); // violation

    public Set<? extends Foo<Boolean>> typeArgument() { // violation
        return new TreeSet<Foo<Boolean>>(); // OK
    }

    public class MyClass<Foo extends Boolean> {} // violation

}

class Bounded {

    public boolean match = new TreeSet<Integer>().stream()
            .allMatch(new TreeSet<>()::add); // OK

    public static <Boolean> void foo() {} // violation

}

class Foo<T extends Boolean & Serializable> { // OK

    void foo() {}

}

@interface Annotation {

    Class<? extends Boolean>[] nonPublic(); // OK
    public Class<? extends Boolean>[] value(); // violation

}
