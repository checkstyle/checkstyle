package com.puppycrawl.tools.checkstyle.internal.raresyntaxnoexception;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class InputRareSyntaxNoExceptionLambda {
    private final Function<String, Integer> length = String::length;
    private final Supplier<String> greeting = () -> "hello";

    public List<Integer> lengths(List<String> values) {
        return values.stream().map(length).collect(Collectors.toList());
    }

    public String combine(String first, String second) {
        BiFunction<String, String, String> join = (left, right) -> left + "-" + right;
        Function<String, Function<String, String>> curried = left -> right -> left + right;
        return join.apply(first, second) + curried.apply(first).apply(second) + greeting.get();
    }

    public void consume(List<String> values) {
        values.forEach(text -> {
            try {
                if (text.isEmpty()) {
                    throw new IllegalArgumentException("empty");
                }
                System.out.println(text);
            }
            catch (IllegalArgumentException ex) {
                throw new IllegalStateException("bad value", ex);
            }
        });
        Runnable nested = () -> {
            Runnable inner = () -> System.out.println("nested");
            inner.run();
        };
        nested.run();
    }
}
