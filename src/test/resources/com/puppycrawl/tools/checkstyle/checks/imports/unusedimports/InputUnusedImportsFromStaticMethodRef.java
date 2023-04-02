/*
UnusedImports
processJavadoc = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import static java.lang.annotation.ElementType.TYPE_USE; // ok
import static java.lang.annotation.RetentionPolicy.RUNTIME; // ok

import java.lang.annotation.Retention; // ok
import java.lang.annotation.Target; // ok
import java.lang.ref.WeakReference; // ok
import java.util.ArrayList; // ok
import java.util.Optional; // ok
import java.util.Objects; // ok
import java.util.Arrays; // ok
import java.util.List; // ok
import java.util.Map; // ok
import java.util.Collections; // ok
import java.util.function.Consumer; // ok
import java.util.function.Function; // ok
import java.util.function.Supplier; // ok
import static java.lang.String.format; // violation
import static java.util.Arrays.sort; // violation
import static java.util.List.of; // violation
import static java.util.Collections.emptyMap; // violation

/**
 * Use {@link Collections::emptyMap} in this javadoc.
 */
public class InputUnusedImportsFromStaticMethodRef {

    public static <T, R> Function<T, R> func(Function<T, R> f) { return f; }

    InputUnusedImportsFromStaticMethodRef() {
    }

    void testMethodRef()
    {
        Optional<String> test = Optional.empty();
        test.map(String::format);
    }

    void testMethodRefWithClass()
    {
        Optional<String> test = Optional.empty();
        test.map(Objects::nonNull);
    }

    void testMethodRefAssignment()
    {
        int[] array = { 10, 2, 19, 5, 17 };
        Consumer<int[]> consumer = Arrays::sort;
        consumer.accept(array);
    }

    void testConstructorMethodRefArgument()
    {
        func(func(@CJ WeakReference::new));
    }

    void testConstructorMethodRefAssignment()
    {
        ListGetter<String> listGetter = ArrayList::new;
        listGetter.get();
    }
    void testMethodRefOnGenericTypeAssignment()
    {
        Function<String, List<String>> fn = List::of;
        List<String> list = fn.apply("test");
    }

    void testMethodRefOnGenericTypeArgument()
    {
        Function<String, List<String>> fn = func(List::of);
        List<String> list = fn.apply("test");
    }

    void testMethodRefImportedInJavadoc()
    {
        Supplier<Map<String, String>> supplier = Collections::emptyMap;
        Map<String, String> list = supplier.get();
    }

}

@Retention(RUNTIME) @Target({TYPE_USE}) @interface CJ { }

interface ListGetter<T> {
    List<T> get();
}
