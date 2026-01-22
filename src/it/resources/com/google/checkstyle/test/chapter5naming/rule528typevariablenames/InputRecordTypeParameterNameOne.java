// Java17

package com.google.checkstyle.test.chapter5naming.rule528typevariablenames;

import java.io.Serializable;
import java.util.LinkedHashMap;

/** Some javadoc. Config: pattern = "(^[A-Z][0-9]?)$|([A-Z][a-zA-Z0-9]*[T]$)" */
public record InputRecordTypeParameterNameOne<t>(Integer x, String str) {
  // violation above 'Record type name 't' must match pattern'
  /** Some javadoc. */
  public <TT> void foo() {}

  <T> void foo(int i) {}

  // violation below 'Record type name 'foo' must match pattern'
  record Other<foo extends Serializable & Cloneable>(LinkedHashMap<String, String> linkedHashMap) {

    foo getOne() {
      return null; // comment
    }

    <T extends foo> /*comment*/ T getTwo(T a) {
      return null;
    }

    <E extends Runnable> E getShadow() {
      return null;
    }

    static record Junk<foo>() { // violation 'Record type name 'foo' must match pattern'
      <E extends foo> void getMoreFoo() {}
    }
  }

  record MoreOther<T extends Cloneable>(char c, String string) {

    interface Boo<I> {
      I boo();
    }

    interface FooInterface<T> {
      T foo();
    }

    interface FooInterface2 {
      I foo();
    }

    record I(int x, int y) {}
  }
}
