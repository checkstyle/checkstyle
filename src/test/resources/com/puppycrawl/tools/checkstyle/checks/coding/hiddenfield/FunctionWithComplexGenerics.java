package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

/**
 * A functional interface representing a function that accepts two arguments of generic types
 * and produces a result of the second type.
 *
 * @param <O> the type of the first input to the function
 * @param <T> the type of the second input to the function and the result
 */
@FunctionalInterface
public interface FunctionWithComplexGenerics<O, T> {
  /**
     * Applies the function and returns the result of type T.
     *
     * @param one the first input
     * @param two the second input
     * @return the result of type T
     */
  T foo(O one, T two);
}
