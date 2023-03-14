/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = true
accessModifiers = (default)public, protected, package, private
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.function.Function;

public class InputJavadocMethodIgnoreThrows {

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
                throw new NumberFormatException(value + " is negative/zero"); // ok
            }
            return value;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid number", ex); // violation
        } finally {
            throw new IllegalStateException("Should never reach here"); // violation
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
            throw new IllegalArgumentException(ex.toString()); // violation
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
            throw ex; // ok
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

    /**
     * Try-with-resources should also be ignored if there is a catch block.
     *
     * @param input file to read
     */
    private static void ignoreTryWithResources(String input) {
        try (BufferedReader in = new BufferedReader(new FileReader(input))) {
            String s = in.readLine();
            System.out.println(s);
            if (s.length() == 0) {
                // false negative, unable to tell what was caught
                throw new IllegalArgumentException("empty input"); // no violation
            }
            else {
                throw new IOException(); // ok, exception was caught
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    /**
     * However, do not ignore try block without catch.
     */
    private static void keepTryWithoutCatch() {
        try (Scanner sc = new Scanner(System.in)) {
            if (sc.nextInt() <= 0) {
                throw new IllegalArgumentException(""); // violation
            }
        }
    }

    /**
     * Local inner classes should be ignored.
     *
     * @param str input
     */
    void dfs(String str) {
        class DFS {
            void neverCalled() {
                throw new IllegalStateException(""); // ok, inside local class
            }

            void dfs(String str) {
                if (str != null) {
                    dfs(str.toLowerCase());
                    System.out.println(str);
                    dfs(str.toUpperCase());
                }
            }
        }
        new DFS().dfs(str);
    }

    /**
     * Anonymous classes should be ignored as well.
     *
     * @return some Runnable
     */
    Runnable ignoreAnonClasses() {
        return new Runnable() {
            @Override
            public void run() {
                throw new UnsupportedOperationException(""); // ok, inside anon class
            }
        };
    }

    /**
     * Catch block is not ignored, but lambda is still ignored.
     *
     * @param s a string
     * @return a function
     */
    Function<String, Integer> lambdaInCatchBlock(String s) {
        try {
            int value = Integer.parseInt(s);
            if (value <= 0) {
                throw new NumberFormatException(value + " is negative/zero"); // ok, try
            }
            return x -> value;
        } catch (NumberFormatException ex) {
            if (s.length() == 1) {
                throw new IllegalArgumentException("Invalid number", ex); // violation
            }
            return x -> {
                throw new UnsupportedOperationException(""); // ok, inside lambda
            };
        }
    }

}
