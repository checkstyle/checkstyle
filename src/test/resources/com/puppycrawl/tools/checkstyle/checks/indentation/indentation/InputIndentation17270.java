// Regression test for issue #17270: wrapped case labels indentation           //indent:0 exp:0
// https://github.com/checkstyle/checkstyle/issues/17270                       //indent:0 exp:0
//                                                                             //indent:0 exp:0
// This file tests wrapped case labels should NOT produce indentation errors   //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

public class InputIndentation17270 {
    private void test() {
        Object value = null;

        System.out.println(switch (value) {
            case null -> "null";
            case String,
                 Integer -> "String or Integer";
            default -> "Unknown type";
        });

        int result = switch (value) {
            case null -> 0;
            case String,
                 Integer,
                 Boolean -> 1;
            default -> 2;
        };

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
