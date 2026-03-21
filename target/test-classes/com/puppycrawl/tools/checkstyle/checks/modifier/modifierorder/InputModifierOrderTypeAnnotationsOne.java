/*
ModifierOrder


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class InputModifierOrderTypeAnnotationsOne extends MyClass {

    // Simple type definitions with type annotations
    private @TypeAnnotation String hello = "Hello, World!";
    private @TypeAnnotation final String jdk = "JDK8";
    @TypeAnnotation private String projectName = "Checkstyle";

    // We can use type Annotations with generic type arguments
    private Map.@TypeAnnotation Entry entry;
    // Type annotations can also be applied to nested types
    private List<@TypeAnnotation String> strings;

    // Constructors with type annotations
    {
        new @TypeAnnotation Object();
    }

    static {
        new @TypeAnnotation Object();
    }

    public void foo1() {
        new @TypeAnnotation Object();
    }

    // Type annotations work with nested (non static) class constructors too
    public void foo2() {
        Object myObject = new Object();
        //myObject.new @TypeAnnotation Nested();
    }

    // Inheritance
    class MySerializableClass<T> implements @TypeAnnotation Serializable {  }

    // Nested type annotations
    Map<@TypeAnnotation String, @TypeAnnotation List<@TypeAnnotation String>> documents;

    // Apply type annotations to intersection types
    public <E extends @TypeAnnotation Comparator<E> & @TypeAnnotation Comparable> void foo5() {  }

    // Including parameter bounds and wildcard bounds
    class Folder<F extends @TypeAnnotation File> { }
    Collection<? super @TypeAnnotation File> c;
    List<@TypeAnnotation ? extends Comparable<T>> unchangeable;

    // Throwing exceptions
    void foo6() throws @TypeAnnotation IOException { }

    // Type annotations in instanceof statements
    public void foo7() {
        boolean isNonNull = "string" instanceof @TypeAnnotation String;

    }

    class Nested { }

    class T { }

    // Type annotation on method return type
    @Override
    public @TypeAnnotation String toString() { return ""; }

    @Override
    @TypeAnnotation public int hashCode() { return 1; }

    public @TypeAnnotation int foo8() { return 1; }

    public @TypeAnnotation boolean equals(Object obj) { return super.equals(obj); }

//    @TypeAnnotation void foo9() { } <-- Compiletime error:
                                        // void type cannot be annotated with type annotation

    @Override
    void foo10() {
        super.foo10();
    }
}

class MyClass {

    // It is annotation on method, but not on type!
    @MethodAnnotation void foo10() {}
    private @MethodAnnotation void foo11() {}
     // violation above ''@MethodAnnotation' annotation modifier.*non-annotation modifiers.'
    public @TypeAnnotation MyClass() {}
    @ConstructorAnnotation public MyClass(String name) {}
}



@Target({
    ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER,
    ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
@interface TypeAnnotation {
}

@interface MethodAnnotation {}

@interface ConstructorAnnotation {}
