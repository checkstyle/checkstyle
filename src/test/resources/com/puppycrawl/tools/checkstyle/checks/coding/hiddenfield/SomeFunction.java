package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

/**
 * A functional interface representing a function that accepts two arguments of generic types
 * and produces a result of the second type.
 *
 * @param <O> the type of the first input to the function
 * @param <T> the type of the second input to the function and the result
 */
@FunctionalInterface
public interface SomeFunction<O, T> {

  /**
     * Applies the function to the given arguments and produces a result.
     *
     * @param one the first input to the function
     * @param two the second input to the function
     * @return the result of applying the function
     */
  T apply(O one, T two);
}

