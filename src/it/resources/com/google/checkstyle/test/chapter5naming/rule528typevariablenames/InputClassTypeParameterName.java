package com.google.checkstyle.test.chapter5naming.rule528typevariablenames;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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

class Cache<FOOT, BART> {
  // 3 violations above:
  // 'Top-level class Cache has to reside in its own source file.'
  // 'Class type name 'FOOT' must match pattern'
  // 'Class type name 'BART' must match pattern'
  Map<FOOT, BART> map = new HashMap<>();

  BART get(FOOT k) {
    return map.get(k);
  }

  void put(FOOT k, BART v) {
    map.put(k, v);
  }
}
