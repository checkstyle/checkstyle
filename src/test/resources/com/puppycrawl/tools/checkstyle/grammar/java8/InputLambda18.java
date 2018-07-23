package com.puppycrawl.tools.checkstyle.grammar.java8;

import java.util.function.Predicate;

public class InputLambda18 {

    static <T> Predicate<T> isEqual(Object targetRef) {
        return (null == targetRef)
                ? null
                : object -> targetRef.equals(object);
    }
}
