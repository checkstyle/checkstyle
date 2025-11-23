/*
UnusedImports
processJavadoc = false
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Objects;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.Arrays; // violation 'Unused import - java.util.Arrays.'
import static java.lang.Integer.parseInt; // violation 'Unused import - java.lang.Integer.'
import static java.lang.String.format; // violation 'Unused import - java.lang.String.format.'
import static java.util.List.of; // violation 'Unused import - java.util.List.of.'
import static java.util.Collections.emptyMap; // violation 'Unused import - java.util.Collections.emptyMap.'

/**
 * This {@link Arrays::sort} is NOT a valid link,
 * same as the one below.
 * Use {@link Integer::parseInt}.
 */
public class InputUnusedImportsFromStaticMethodRefJavadocDisabled {

    public static <T, R> Function<T, R> func(Function<T, R> f) { return f; }

    InputUnusedImportsFromStaticMethodRefJavadocDisabled() {
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
    void testConstructorMethodRefArgument()
    {
        func(func(@CJ2 WeakReference::new));
    }

    void testConstructorMethodRefAssignment()
    {
        ListGetter2<String> listGetter = ArrayList::new;
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

@Retention(RUNTIME) @Target({TYPE_USE}) @interface CJ2 { }

interface ListGetter2<T> {
    List<T> get();
}
