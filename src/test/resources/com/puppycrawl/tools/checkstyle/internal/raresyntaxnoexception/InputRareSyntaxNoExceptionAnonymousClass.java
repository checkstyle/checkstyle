package com.puppycrawl.tools.checkstyle.internal.raresyntaxnoexception;

import java.util.Comparator;

public class InputRareSyntaxNoExceptionAnonymousClass {
    private final Runnable task = new Runnable() {
        private int runs;

        @Override
        public void run() {
            runs++;
            try {
                if (runs > 10) {
                    throw new IllegalStateException("too many runs");
                }
            }
            catch (IllegalStateException ex) {
                throw new IllegalArgumentException("failed", ex);
            }
        }
    };

    public Comparator<String> comparator() {
        return new Comparator<String>() {
            @Override
            public int compare(String first, String second) {
                return Integer.compare(first.length(), second.length());
            }
        };
    }

    public Object maker() {
        return new Object() {
            @Override
            public String toString() {
                return "anonymous";
            }
        };
    }

    public void execute() {
        task.run();
    }
}
