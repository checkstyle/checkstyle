package com.google.checkstyle.test.chapter5naming.rule528typevariablenames;

import java.io.Serializable;

/** Some javadoc. */
public class InputInterfaceTypeParameterName<T> {
  /** Some javadoc. */
  public <TT> void foo() {}

  <T> void foo(int i) {}
}

// violation below 'Top-level class OtherClass has to reside in its own source file.'
class OtherClass<T extends Serializable & Cloneable> {

  T getOne() {
    return null; // comment
  }

  <X extends T> /*comment*/ X getTwo(X a) {
    return null;
  }

  <E extends Runnable> E getShadow() {
    return null;
  }

  static class Junk<E> {
    <T extends E> void getMoreFoo() {}
  }
}

// violation below 'Top-level class MoreOtherClass has to reside in its own source file.'
class MoreOtherClass<T extends Cloneable> {

  <E extends T> void getMore() {
    new Other() {
      <T> void getMoreFoo() {}
    };

    //        Other o = new Other() {
    //            <EE> void getMoreFoo() {
    //            }
    //        };
  }
}

// violation below 'Top-level class Boo has to reside in its own source file.'
interface Boo<Input> { // violation 'Interface type name 'Input' must match pattern'
  Input boo();
}

// violation below 'Top-level class FooInterface has to reside in its own source file.'
interface FooInterface<T> {
  T foo();
}

// violation below 'Top-level class FooInterface2 has to reside in its own source file.'
interface FooInterface2 {
  Input foo();
}

// violation below 'Top-level class FooInterface3 has to reside in its own source file.'
interface FooInterface3<T2> {
  Input foo();
}

// violation below 'Top-level class FooInterface4 has to reside in its own source file.'
interface FooInterface4<E> {
  Input foo();
}

// violation below 'Top-level class FooInterface5 has to reside in its own source file.'
interface FooInterface5<X> {
  Input foo();
}

// violation below 'Top-level class FooInterface6 has to reside in its own source file.'
interface FooInterface6<RequestT> {
  Input foo();
}

interface FooInterface7<Request> {
  // 2 violations above:
  //  'Top-level class FooInterface7 has to reside in its own source file.'
  //  'Interface type name 'Request' must match pattern'
  Input foo();
}

interface FooInterface8<TRequest> {
  // 2 violations above:
  //  'Top-level class FooInterface8 has to reside in its own source file.'
  //  'Interface type name 'TRequest' must match pattern'
  Input foo();
}

// violation below 'Top-level class Input has to reside in its own source file.'
class Input {}
