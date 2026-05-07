// Regression test for issue #17270: Switch with multiple wrapped cases has incorrect indentation
// https://github.com/checkstyle/checkstyle/issues/17270
//
// This file demonstrates the false-positive indentation error when case labels are wrapped across lines.
// The checker incorrectly reports: 'case' child has incorrect indentation level 17, expected level should be 20
// For this valid code:

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

public class InputIndentation17270 {
    private void test() {
        Object value = null;

        // Issue #17270: Multiple case labels wrapped on separate lines should NOT produce indentation error
        // Expected: No indentation warnings
        System.out.println(switch (value) {
            case null -> "null";
            case String,
                 Integer -> "String or Integer";
            default -> "Unknown type";
        });

        // Additional test case with more wrapped cases
        int result = switch (value) {
            case null -> 0;
            case String,
                 Integer,
                 Boolean -> 1;
            default -> 2;
        };

        // Test case with multiple groups
        String output = switch (value) {
            case null, String -> "null or string";
            case Integer,
                 Boolean,
                 Double -> "number";
            case Long,
                 Short,
                 Byte -> "other number";
            default -> "other";
        };
    }
}
