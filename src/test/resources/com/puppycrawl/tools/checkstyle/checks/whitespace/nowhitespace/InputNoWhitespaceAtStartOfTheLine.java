/*
NoWhitespace
allowLineBreaks = (default)false
tokens = DOT


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace
.nowhitespace; // violation

import java.util.function.Supplier;

public class InputNoWhitespaceAtStartOfTheLine {
    public static class A {
        private A() {
        }
    }

    public <V> void methodName(V value) {
        Supplier<?> t =
A ::new;
    }
}
