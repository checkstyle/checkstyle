package com.puppycrawl.tools.checkstyle.internal.raresyntaxnoexception;

public record InputRareSyntaxNoExceptionRecord(String name, int value) {
    public InputRareSyntaxNoExceptionRecord {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name must not be empty");
        }
        if (value < 0) {
            throw new IllegalArgumentException("value must not be negative");
        }
    }

    public InputRareSyntaxNoExceptionRecord(String name) {
        this(name, 0);
    }

    public String describe() {
        return switch (this) {
            case InputRareSyntaxNoExceptionRecord(String text, int number)
                    when number > 0 -> text + "=" + number;
            case InputRareSyntaxNoExceptionRecord(String text, int number) ->
                    text + " has no value";
        };
    }

    public record Nested(String text) {
        public String shout() {
            return text.toUpperCase();
        }
    }
}
