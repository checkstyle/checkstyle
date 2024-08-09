package com.google.checkstyle.test.chapter5naming.rule528typevariablenames;

import java.io.Serializable;

class InputClassTypeParameterName<t> { // violation 'Class type name 't' must match pattern'
  public <TT> void foo() {}

  <T> void foo(int i) {}
}

// violation below 'Top-level class Other has to reside in its own source file.'
class Other<foo extends Serializable & Cloneable> {
  // violation above 'Class type name 'foo' must match pattern'

  foo getOne() {
    return null;
  }

  <T extends foo> T getTwo(T a) {
    return null;
  }

  <T extends Runnable> foo getShadow() {
    return null;
  }

  static class Junk<$foo> { // violation 'Class type name '\$foo' must match pattern'
    <T extends $foo> void getMoreFoo() {}
  }
}

// violation below 'Top-level class MoreOther has to reside in its own source file.'
class MoreOther<T extends Cloneable> {

  <E extends T> void getMore() {
    new Other() {
      <T> void getMoreFoo() {}
    };

    Other o =
        new Other() {
          <T> void getMoreFoo() {}
        };
  }
}
