package com.puppycrawl.tools.checkstyle.internal.raresyntaxnoexception;

public class InputRareSyntaxNoExceptionReceiver {
    private int value;

    public InputRareSyntaxNoExceptionReceiver() {
        this.value = 0;
    }

    void setValue(InputRareSyntaxNoExceptionReceiver InputRareSyntaxNoExceptionReceiver.this,
            int value) {
        this.value = value;
    }

    void guarded(InputRareSyntaxNoExceptionReceiver InputRareSyntaxNoExceptionReceiver.this) {
        try {
            System.out.println("work");
        }
        catch (IllegalStateException ex) {
            throw new IllegalArgumentException("failed", ex);
        }
    }

    public int value() {
        return value;
    }

    public class Inner {
        private String name;

        Inner(InputRareSyntaxNoExceptionReceiver InputRareSyntaxNoExceptionReceiver.this,
                String name) {
            this.name = name;
        }

        public String name() {
            return name;
        }
    }
}
