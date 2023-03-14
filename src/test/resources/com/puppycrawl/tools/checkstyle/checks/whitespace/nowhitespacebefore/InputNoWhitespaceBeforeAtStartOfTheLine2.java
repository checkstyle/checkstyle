/*
NoWhitespaceBefore
allowLineBreaks = yes
tokens = METHOD_REF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace
.nowhitespacebefore;

import java.util.function.Supplier;

public class InputNoWhitespaceBeforeAtStartOfTheLine2 {
    public static class A {
        private A() {
        }
    }

    public <V> void methodName(V value) {
        Supplier<?> t =
A ::new; // violation
    }
}
