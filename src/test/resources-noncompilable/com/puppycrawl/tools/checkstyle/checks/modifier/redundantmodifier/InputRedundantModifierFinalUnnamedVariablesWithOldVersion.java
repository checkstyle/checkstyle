/*
RedundantModifier
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE, PATTERN_VARIABLE_DEF, LITERAL_CATCH, LAMBDA
jdkVersion = 8


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

import java.util.function.BiFunction;

public class InputRedundantModifierFinalUnnamedVariablesWithOldVersion {
    void m(Object o) {
        if (o instanceof final String _) { }
        if (o instanceof final String __) { }
        if (o instanceof final String s)
        if (o instanceof final String _s) { }

        final int _ = sideEffect();
        final int __ = sideEffect();
        final int x = sideEffect();
        final int _x = sideEffect();
    }

    void m2(Object o) {
        switch (o) {
            case final String _ -> { }
            case Float _ -> { }
            case final Integer __ -> { }
            case final Double s -> { }
            default -> { }
        }
    }

    void m3() {
        // violation below, 'Redundant 'final' modifier'
        try (final var a = lock();) {

        } catch (final Exception e) {

        }

        // violation below, 'Redundant 'final' modifier'
        try (final var _ = lock();) {

        } catch (final Exception _) {

        }
    }

    void m4() {
        BiFunction<Integer, Integer, Integer> f = (final Integer a, final Integer b) -> {
            return 5;
        };

        BiFunction<Integer, Integer, Integer> f2 = (final Integer _, final Integer b) -> {
            return 5;
        };

        BiFunction<Integer, Integer, Integer> f3 = (final Integer _, final Integer _) -> {
            return 5;
        };
    }

    int sideEffect() {
        return 0;
    }

    AutoCloseable lock() {
        return null;
    }

    record Point(int x, int y) { }
}
