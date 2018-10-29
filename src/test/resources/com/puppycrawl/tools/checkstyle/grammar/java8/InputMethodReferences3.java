package com.puppycrawl.tools.checkstyle.grammar.java8;
import java.util.function.Supplier;

public class InputMethodReferences3
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
    Supplier<?> t = Nested2<V>::new;
    Supplier<SomeClass.Nested<V>> passes = SomeClass.Nested::new;
    Supplier<SomeClass.Nested<V>> fails = SomeClass.Nested<V>::new;
  }
}
