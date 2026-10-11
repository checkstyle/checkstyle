/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.grammar.java8;
import java.util.function.Supplier;

public class InputMethodReferences3 // ok
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
