package com.puppycrawl.tools.checkstyle.internal.raresyntaxnoexception;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class InputRareSyntaxNoExceptionClass {
    private static final int LIMIT = 10;
    private int count;
    private String label = "class";

    public int compute(Object value) {
        int total = 0;
        outer:
        for (int index = 0; index < LIMIT; index++) {
            if (value instanceof String text) {
                total += text.length();
                continue outer;
            }
            total += index;
        }
        int countdown = LIMIT;
        while (countdown > 0) {
            countdown--;
        }
        do {
            total++;
        } while (total < 0);
        for (String item : List.of("a", "b", "c")) {
            total += item.length();
        }
        try (BufferedReader reader = new BufferedReader(new StringReader(label))) {
            String line = reader.readLine();
            if (line == null) {
                throw new IllegalStateException("empty");
            }
            total += line.length();
        }
        catch (Exception ex) {
            throw new IllegalArgumentException("failed", ex);
        }
        finally {
            total += 1;
        }
        return total;
    }

    public String describe(Object value) {
        return switch (value) {
            case String text when text.isEmpty() -> "empty";
            case String text -> "text " + text;
            case Integer number -> "number " + number;
            case null -> "null";
            default -> "other";
        };
    }

    public List<String> transform(List<String> values) {
        Function<String, String> upper = String::toUpperCase;
        List<String> result = new ArrayList<>();
        values.stream().map(upper).map(text -> text + "!").forEach(result::add);
        return result;
    }

    public <T extends Comparable<T>> T greatest(T first, T second) {
        if (first.compareTo(second) >= 0) {
            return first;
        }
        return second;
    }

    private static class Nested {
        void run() {
            int local = 0;
            local = local + 1;
            System.out.println(local);
        }
    }
}
