//Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.whitespace;
import java.util.function.Supplier;
public static class InputMethodReferences3
{
  public static class SomeClass {
    public static class Nested<V> {
      public Nested() {
      }
    }
  }

  public <V> void methodName(V value) {
    MyClass<T>::myMethod;
    Supplier<SomeClass.Nested<V>> passes = SomeClass.Nested::new;
    Supplier<SomeClass.Nested<V>> fails = SomeClass.Nested<V>::new;
  }
}
