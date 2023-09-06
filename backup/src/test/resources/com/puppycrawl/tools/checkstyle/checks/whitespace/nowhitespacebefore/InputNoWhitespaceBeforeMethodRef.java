/*
NoWhitespaceBefore
allowLineBreaks = (default)false
tokens = METHOD_REF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebefore;

import java.util.function.Supplier;

public class InputNoWhitespaceBeforeMethodRef {
    public static class SomeClass {
    public static class Nested<V> {
      private Nested() {
      }
    }
  }

  public static class Nested2<V> {
  }

  public <V> void methodName(V value) {
    Supplier<?> t = Nested2<V> ::new; // violation
    Supplier<SomeClass.Nested<V>> passes = SomeClass.Nested ::new; // violation
    Supplier<SomeClass.Nested<V>> fails = SomeClass.Nested<V>::new;
  }
}
