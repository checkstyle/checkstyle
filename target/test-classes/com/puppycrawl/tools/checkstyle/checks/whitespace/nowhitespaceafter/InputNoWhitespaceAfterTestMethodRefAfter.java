/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = METHOD_REF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

import java.util.function.Supplier;

public class InputNoWhitespaceAfterTestMethodRefAfter {
    public static class SomeClass {
        public static class Nested<V> {
            private Nested() {
            }
        }
    }

    public static class Nested2<V> {
    }

    public <V> void methodName(V value) {
        Supplier<?> t = Nested2<V>:: new;
        // violation above, ''::' is followed by whitespace.'
        Supplier<SomeClass.Nested<V>> passes = SomeClass.Nested:: new;
        // violation above, ''::' is followed by whitespace.'
        Supplier<SomeClass.Nested<V>> fails = SomeClass.Nested<V>::new; //no violation
    }
}
