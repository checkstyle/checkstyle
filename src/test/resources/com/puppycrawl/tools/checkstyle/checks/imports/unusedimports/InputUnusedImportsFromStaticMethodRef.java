/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = (default)false
javadocTokens = (default)REFERENCE, PARAMETER_TYPE, THROWS_BLOCK_TAG

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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import static java.lang.String.format; // violation 'Unused import - java.lang.String.format.'
import static java.util.Arrays.sort; // violation 'Unused import - java.util.Arrays.sort.'
import static java.util.List.of; // violation 'Unused import - java.util.List.of.'
import static java.util.Collections.emptyMap; // violation 'Unused import - java.util.Collections.emptyMap.'






public class InputUnusedImportsFromStaticMethodRef {
    public static <T, R> Function<T, R> func(Function<T, R> f) { return f; }

    InputUnusedImportsFromStaticMethodRef() {
    }

    void testMethodRef() {
        Optional<String> test = Optional.empty();
        test.map(String::format);
    }

    void testMethodRefWithClass() {
        Optional<String> test = Optional.empty();
        test.map(Objects::nonNull);
    }

    void testMethodRefAssignment() {
        int[] array = { 10, 2, 19, 5, 17 };
        Consumer<int[]> consumer = Arrays::sort;
        consumer.accept(array);
    }

    void testConstructorMethodRefArgument() {
        func(func(@CJ WeakReference::new));
    }

    void testConstructorMethodRefAssignment() {
        ListGetter<String> listGetter = ArrayList::new;
        listGetter.get();
    }
    void testMethodRefOnGenericTypeAssignment() {
        Function<String, List<String>> fn = List::of;
        List<String> list = fn.apply("test");
    }

    void testMethodRefOnGenericTypeArgument() {
        Function<String, List<String>> fn = func(List::of);
        List<String> list = fn.apply("test");
    }

    void testMethodRefImportedInJavadoc() {
        Supplier<Map<String, String>> supplier = Collections::emptyMap;
        Map<String, String> list = supplier.get();
    }

    void testMethodRefNotImported() {
        Function<String, Integer> parseIntFunc = Integer::parseInt;
        int number = parseIntFunc.apply("42");
    }

}

@Retention(RUNTIME) @Target({TYPE_USE}) @interface CJ { }
interface ListGetter<T> {
    List<T> get();
}

