package com.puppycrawl.tools.checkstyle.internal.raresyntaxnoexception;

import java.util.ArrayList;
import java.util.List;

public class InputRareSyntaxNoExceptionStatements {
    private int count = 3;

    public <T extends Number & Comparable<T>> T bounded(T value) {
        return value;
    }

    public void consume(List<? super Number> values, String... extras) {
        List<String> copy = new ArrayList<>(extras.length);
        for (String extra : extras) {
            copy.add(extra);
        }
        values.add(count);
        System.out.println(copy + " " + textBlock());
    }

    public String textBlock() {
        return """
                hello
                world
                """;
    }

    private static class Child extends java.util.ArrayList<String> {
    }

    public String superText() throws Exception {
        return super.toString();
    }
}
