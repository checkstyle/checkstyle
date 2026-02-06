/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = true
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
allowInlineReturn = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

import java.util.function.Function;

public class InputJavadocMethodIgnoreThrowsOne {

    /**
     * Ignore try block, but keep catch and finally blocks.
     *
     * @param s String to parse
     * @return A positive integer
     */
    private static int parsePositiveInt(String s) {
        try {
            int value = Integer.parseInt(s);
            if (value <= 0) {
                throw new NumberFormatException(value + " is negative/zero");
            }
            return value;
        } catch (NumberFormatException ex) {
            // violation below '.* @throws .* 'IllegalArgumentException'.'
            throw new IllegalArgumentException("Invalid number", ex);
        } finally {
            // violation below '.* @throws .* 'IllegalStateException'.'
            throw new IllegalStateException("Should never reach here");
        }
    }

    /**
     * For exceptions that are thrown, caught, and rethrown, violation is in the catch block.
     * Prior to issue 7473, violation was logged in the try block instead.
     *
     * @param o An input
     */
    private static void catchAndRethrow(Object o) {
        try {
            if (o == null) {
                throw new IllegalArgumentException("null"); // ok, try
            }
        } catch (IllegalArgumentException ex) {
            // violation below '.* @throws .* 'IllegalArgumentException'.'
            throw new IllegalArgumentException(ex.toString());
        }
    }

    /**
     * However, rethrowing the same exception (without creating a new one)
     * will not be treated as a violation.
     *
     * @param o An input
     */
    private static void catchAndRethrowSame(Object o) {
        try {
            if (o == null) {
                throw new IllegalArgumentException("null"); // ok, try
            }
        } catch (IllegalArgumentException ex) {
            throw ex; // no violation
        }
    }

    /**
     * It is not possible to tell the exact type of the exception
     * unless 'throw new' is used.
     *
     * @param o An input
     * @param i Another input
     */
    private static void catchAndRethrowDifferent(Object o, int i) {
        try {
            float x = 1 / i; // ArithmeticException when i = 0
        } catch (RuntimeException ex) {
            if (o == null) {
                ex = new NullPointerException("");
            }
            throw ex;
        }
    }

    /**
     * Ignore everything inside lambda.
     *
     * @param maxLength Max length
     * @return A function to truncate string
     */
    private static Function<String, String> getTruncateFunction(int maxLength) {
        return s -> {
            if (s == null) {
                throw new IllegalArgumentException("Cannot truncate null"); // ok, inside lambda
            }
            return s.length() > maxLength ? s.substring(0, maxLength) : s;
        };
    }

}
