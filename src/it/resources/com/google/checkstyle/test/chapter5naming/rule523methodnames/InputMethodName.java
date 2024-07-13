package com.google.checkstyle.test.chapter5naming.rule523methodnames;

/**
 * Test input for MethodNameCheck specifically whether the method name equals the class name.
 *
 * @author Travis Schneeberger
 */
public class InputMethodName {
  void foo() {}

  void Foo() {} // violation 'Method name 'Foo' must match pattern'

  void fOo() {} // violation 'Method name 'fOo' must match pattern'

  void f0o() {}

  void f$o() {} // violation 'Method name 'f\$o' must match pattern'

  void f_oo() {} // violation 'Method name 'f_oo' must match pattern'

  void f() {} // violation 'Method name 'f' must match pattern'

  void fO() {} // violation 'Method name 'fO' must match pattern'

  class InnerFoo {
    void foo() {}

    void Foo() {} // violation 'Method name 'Foo' must match pattern'

    void fOo() {} // violation 'Method name 'fOo' must match pattern'

    void f0o() {}

    void f$o() {} // violation 'Method name 'f\$o' must match pattern'

    void f_oo() {} // violation 'Method name 'f_oo' must match pattern'

    void f() {} // violation 'Method name 'f' must match pattern'

    void fO() {} // violation 'Method name 'fO' must match pattern'
  }

  InnerFoo anon =
          new InnerFoo() {
            void foo() {}

            void Foo() {} // violation 'Method name 'Foo' must match pattern'

            void fOo() {} // violation 'Method name 'fOo' must match pattern'

            void f0o() {}

            void f$o() {} // violation 'Method name 'f\$o' must match pattern'

            void f_oo() {} // violation 'Method name 'f_oo' must match pattern'

            void f() {} // violation 'Method name 'f' must match pattern'

            void fO() {} // violation 'Method name 'fO' must match pattern'
          };

  interface FooIn {
    void foo();

    void Foo(); // violation 'Method name 'Foo' must match pattern'

    void fOo(); // violation 'Method name 'fOo' must match pattern'

    void f0o();

    void f$o(); // violation 'Method name 'f\$o' must match pattern'

    void f_oo(); // violation 'Method name 'f_oo' must match pattern'

    void f(); // violation 'Method name 'f' must match pattern'

    void fO(); // violation 'Method name 'fO' must match pattern'
  }
}
