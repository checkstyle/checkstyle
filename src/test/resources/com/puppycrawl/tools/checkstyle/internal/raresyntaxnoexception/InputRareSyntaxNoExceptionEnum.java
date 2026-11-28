package com.puppycrawl.tools.checkstyle.internal.raresyntaxnoexception;

public enum InputRareSyntaxNoExceptionEnum {
    FIRST("one") {
        @Override
        public String sound() {
            return "first";
        }
    },
    SECOND("two") {
        @Override
        public String sound() {
            return "second";
        }
    },
    THIRD("three");

    private final String word;

    InputRareSyntaxNoExceptionEnum(String word) {
        this.word = word;
    }

    public String sound() {
        return "third";
    }

    public String word() {
        return word;
    }

    public String describe() {
        return switch (this) {
            case FIRST -> "first " + word;
            case SECOND -> "second " + word;
            case THIRD -> "third " + word;
        };
    }
}
