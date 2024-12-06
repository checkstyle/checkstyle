package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

/**
 * A functional interface representing a function that accepts one parameter and
 * returns a result of the same type.
 *
 * @param <T> the type of the input to the function and the type of the result
 */
@FunctionalInterface
public interface FunctionWithOneParameter<T> {

  /**
     * Applies this function to the given argument.
     *
     * @param t the input argument
     * @return the result of applying the function
     */
  T apply(T t);
}

