package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

/**
 * A functional interface representing a function with no parameters that produces a String result.
 */
@FunctionalInterface
public interface Foo {
    /**
     * Applies the function and returns a String result.
     *
     * @return the result as a String
     */
    String apply();
}
