package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

/**
 * A functional interface representing a function that accepts two arguments and produces a result.
 *
 * @param <A> the type of the first input to the function
 * @param <B> the type of the second input to the function and the result
 */
@FunctionalInterface
public interface Function<A, B> {

  /**
     * Applies the function to the given arguments and produces a result.
     *
     * @param a the first input to the function
     * @param b the second input to the function
     * @return the result of applying the function
     */
  B apply(A a, B b);
}

