package com.puppycrawl.tools.checkstyle.checks.whitespace;
import java.util.function.Supplier;
public class InputNoWhitespaceAfterMethodRefBad
{
  public static class SomeClass {
    public static class Nested<V> {
      private Nested() {
      }
    }
  }

  public static class Nested2<V> {
  }

  public <V> void methodName(V value) {
    Supplier<?> t = Nested2<V>:: new; //16:33 violation
    Supplier<SomeClass.Nested<V>> passes = SomeClass.Nested:: new; //17:62 violation
    Supplier<SomeClass.Nested<V>> fails = SomeClass.Nested<V>::new; //no violation
  }
}
