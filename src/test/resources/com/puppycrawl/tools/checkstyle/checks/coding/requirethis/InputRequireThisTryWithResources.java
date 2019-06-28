package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisTryWithResources {
    private final AutoCloseable value = () -> {};

    public void method() throws Exception {
        try (AutoCloseable value = () -> {}) {
            System.out.println(value); // No violation.
        }
        try (AutoCloseable value1 = () -> {};
                AutoCloseable value2 = value1) {
            System.out.println(value); // Violation.
        }
        try (AutoCloseable value = () -> {};
                AutoCloseable other = value) { // No violation.
            System.out.println(value); // No violation.
            System.out.println(other);
        }
        try (AutoCloseable other = value; // Violation.
                AutoCloseable value = other) {
            System.out.println(other);
            System.out.println(value); // No violation.
        }
    }
}
