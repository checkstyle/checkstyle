/*
NoWhitespace
allowLineBreaks = yes
tokens = METHOD_REF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace
.nowhitespace;

import java.util.function.Supplier;

public class InputNoWhitespaceAtStartOfTheLine2 {
    public static class A {
        private A() {
        }
    }

    public <V> void methodName(V value) {
        Supplier<?> t =
A ::new; // violation
    }
}
