package com.google.checkstyle.test.chapter5naming.rule523methodnames;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

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

  @Test
  void testing_foo() {}

  @Test
  void testing_Foo() {}

  @Test
  void testing_fOo() {}

  @Test
  void testingFoo() {}

  @Test
  void testingFoo_foo() {}

  @Test
  void testing_0123() {}

  @Test
  void Testing_Foo() {} // violation 'Method name 'Testing_Foo' must match pattern'

  @Test
  void t_esting() {} // violation 'Method name 't_esting' must match pattern'

  @Test
  void _testing() {} // violation 'Method name '_testing' must match pattern'

  void Testing_Foo2() {} // violation 'Method name 'Testing_Foo2' must match pattern'

  @Test
  void TestingFooBad() {} // violation 'Method name 'TestingFooBad' must match pattern'

  @ParameterizedTest
  void testing_foo1() {}

  @ParameterizedTest
  void testing_Foo1() {}

  @ParameterizedTest
  void testing_fOo1() {}

  @ParameterizedTest
  void testingFoo1() {}

  @ParameterizedTest
  void testingFoo_foo1() {}

  @ParameterizedTest
  void testing_01231() {}

  @ParameterizedTest
  void Testing_Foo1() {} // violation 'Method name 'Testing_Foo1' must match pattern'

  @ParameterizedTest
  void t_esting1() {} // violation 'Method name 't_esting1' must match pattern'

  @ParameterizedTest
  void _testing1() {} // violation 'Method name '_testing1' must match pattern'

  @ParameterizedTest
  void TestingFooBad1() {} // violation 'Method name 'TestingFooBad1' must match pattern'

  @RepeatedTest(0)
  void testing_foo2() {}

  @RepeatedTest(0)
  void testing_Foo2() {}

  @RepeatedTest(0)
  void testing_fOo2() {}

  @RepeatedTest(0)
  void testingFoo2() {}

  @RepeatedTest(0)
  void testingFoo_foo2() {}

  @RepeatedTest(0)
  void testing_01232() {}

  @RepeatedTest(0)
  void Testing_Foo3() {} // violation 'Method name 'Testing_Foo3' must match pattern'

  @RepeatedTest(0)
  void t_esting2() {} // violation 'Method name 't_esting2' must match pattern'

  @RepeatedTest(0)
  void _testing2() {} // violation 'Method name '_testing2' must match pattern'

  @RepeatedTest(0)
  void TestingFooBad2() {} // violation 'Method name 'TestingFooBad2' must match pattern'

  @BeforeAll
  void _testingFoooo() {} // violation 'Method name '_testingFoooo' must match pattern'

  class InnerFoo {
    void foo() {}

    void Foo() {} // violation 'Method name 'Foo' must match pattern'

    void fOo() {} // violation 'Method name 'fOo' must match pattern'

    void f0o() {}

    void f$o() {} // violation 'Method name 'f\$o' must match pattern'

    void f_oo() {} // violation 'Method name 'f_oo' must match pattern'

    void f() {} // violation 'Method name 'f' must match pattern'

    void fO() {} // violation 'Method name 'fO' must match pattern'

    void testing_foo() {} // violation 'Method name 'testing_foo' must match pattern'

    void testing_Foo() {} // violation 'Method name 'testing_Foo' must match pattern'

    void testing_fOo() {} // violation 'Method name 'testing_fOo' must match pattern'

    void testingFoo() {}

    void testingFoo_foo() {} // violation 'Method name 'testingFoo_foo' must match pattern'

    void testing_0123() {} // violation 'Method name 'testing_0123' must match pattern'

    void TestingFooBad() {} // violation 'Method name 'TestingFooBad' must match pattern'
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
