/*
OpenjdkLineWrapping


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.openjdklinewrapping;

class InputOpenjdkLineWrappingNested {
    void conditions(boolean first, boolean second) {
        if (first ||
                second) {
        }
        if (first ||
            second) { // violation 'incorrect indentation level 12'
        }
        int[] values = {1,
            sum(2,
                    3)};
        int[] invalid = {1,
            sum(2,
                  3)}; // violation 'incorrect indentation level 18'
        int value = sum(1,
                sum(2,
                        3));
        int wrong = sum(1,
                sum(2,
                     3)); // violation 'incorrect indentation level 21'
        int parenthesized = (1
                + 2);
        int indexed = values[0
                + 1
        ];
        String text = """
                contents
            """;
        boolean aligned = first
                          || second;
        boolean alignedRight = first ||
                               second;
        int arithmetic = 1
                         + 2;
        int operand = sum(1,
                2 + 3);
        String call = text()
                .strip();
        int switched = switch (value) {
            default -> 1;
        };
        int conditional = first
                          ? 1 : 2;
        int qualified = sum(1,
                Integer.valueOf(2));
        int afterBody = new Object() {
            int field;
        }.hashCode()
                + 1;
        String built = new StringBuilder()
                .append("x")
                .toString();
        String bad = new StringBuilder().append("a").append(
                                        "x").toString(); // violation 'level 40'
        int other = 1 + sum(2,
                    3); // violation 'incorrect indentation level 20'
        int subexpression = (1
                                     + 2);
        int sameLine = sum(1,
             2 + 3); // violation 'incorrect indentation level 13'
        Object generic = java.util.Collections.<
             String>emptyList(); // violation 'incorrect indentation level 13'
        int sibling = sum(1,
                sum(2,
                    3));
    }

    int sum(int first, int second) { return first + second; }

    String text() { return ""; }
}
