/*
FinalParameters
ignorePrimitiveTypes = (default)false
ignoreUnnamedParameters = (default)true
tokens = PATTERN_VARIABLE_DEF


*/

// non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.finalparameters;

public class InputFinalParametersPatternVariables {
    record ARecord(String name, int age) {
    }
    static void method(final Object o) {
        final boolean isString = o instanceof String s; // violation, 's' should be final
        final boolean isString = o instanceof final String correct;
        if (o instanceof Integer i) { // violation, 'i' should be final
        }
        if (o instanceof final Integer i) {
        }

        if (o instanceof ARecord(String name, final int age)) { // violation, 'name' should be final
        }
        if (o instanceof ARecord(final String name, final int age)) {
        }

        switch (o) {
            case String s -> { // violation, 's' should be final
            }
            case ARecord(String name, final int age) -> { // violation, 'name' should be final
            }
            default -> {}
        }
        switch (o) {
            case final String s -> {
            }
            case ARecord(final String name, final int age) -> {
            }
            default -> {}
        }

    }
}
