//Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.modifier;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class InputTypeAnnotations {

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
        InputTypeAnnotations myObject = new InputTypeAnnotations();
        myObject.new @TypeAnnotation Nested();
    }

    // Type casts
    public void foo3() {
        String myString = (@TypeAnnotation String) new Object();

    }

    // Type annotations with method arguments
    private void foo4(final @TypeAnnotation String parameterName) { }

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
}

@Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER,
         ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
@interface TypeAnnotation {
}