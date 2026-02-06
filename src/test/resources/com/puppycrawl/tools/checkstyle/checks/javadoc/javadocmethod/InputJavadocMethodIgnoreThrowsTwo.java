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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.function.Function;

public class InputJavadocMethodIgnoreThrowsTwo {

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
                // violation below '.* @throws .* 'IllegalArgumentException'.'
                throw new IllegalArgumentException("");
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
                // violation below '.* @throws .* 'IllegalArgumentException'.'
                throw new IllegalArgumentException("Invalid number", ex);
            }
            return x -> {
                throw new UnsupportedOperationException(""); // ok, inside lambda
            };
        }
    }
}
