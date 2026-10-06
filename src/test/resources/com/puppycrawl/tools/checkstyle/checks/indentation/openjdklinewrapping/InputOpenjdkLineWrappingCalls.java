/*
OpenjdkLineWrapping


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.openjdklinewrapping;

class InputOpenjdkLineWrappingCalls extends InputOpenjdkLineWrappingBase {
    java.util.Map<
        String, Integer> declaration;

    InputOpenjdkLineWrappingCalls() {
        this(1,
                2);
    }

    InputOpenjdkLineWrappingCalls(int first, int second) {
        super(first,
                second);
    }

    InputOpenjdkLineWrappingCalls(char ignored) {
        this(1,
            2); // violation 'incorrect indentation level 12'
    }

    InputOpenjdkLineWrappingCalls(boolean ignored) {
        super(1,
            2); // violation 'incorrect indentation level 12'
    }

    void calls(String text) {
        String valid = text.strip()
                           .stripIndent()
                           .trim();
        String invalid = text.strip()
            .stripIndent(); // violation 'incorrect indentation level 12'
        String afterArguments = new StringBuilder().append(
                "first")
                .append("second")
                .toString();
        String qualified = text.
                strip();
        String wrongName = text.
            strip(); // violation 'incorrect indentation level 12'
        Object created = new StringBuilder(
            "text"); // violation 'incorrect indentation level 12'
        int[] array = new int[2];
        consume(1,
                2,
                        3,
                        4);
        consume(1, 2,
                   3,
                           4,
                   5); // violation 'incorrect indentation level 19'
        consume(1,
                2 +
                        3,
                                4);
        String carried = new StringBuilder().append(
                "text")
                        .
                        toString(); // violation 'incorrect indentation level 24'
        accept(() -> 1,
            () -> 2); // violation 'incorrect indentation level 12'
        accept(() -> 1,
                () -> 2);
    }

    void consume(int... values) {
    }

    void accept(java.util.function.IntSupplier... values) {
    }
}

class InputOpenjdkLineWrappingBase {
    InputOpenjdkLineWrappingBase(int... values) {
    }
}
