package com.puppycrawl.tools.checkstyle.internal.raresyntaxnoexception;

import java.util.List;

public interface InputRareSyntaxNoExceptionInterface {
    String NAME = "interface";
    int VERSION = 1;

    String describe(Object value);

    default String greet(String name) {
        return "hello " + name + " " + NAME;
    }

    static List<String> names() {
        return List.of(NAME, "other");
    }

    private String shout(String text) {
        return text.toUpperCase();
    }

    private static int doubled(int value) {
        return value * 2;
    }

    interface Nested {
        void nested();
    }

    enum Kind {
        FIRST,
        SECOND
    }
}
