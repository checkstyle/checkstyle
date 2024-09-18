package com.google.checkstyle.test.chapter5naming.rule523methodnames;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
  void testing_foo() {
    class LocalFoo {
      void foo() {}

      // violation below 'Method name 'testing_foo' must match pattern'
      void testing_foo() {}
    }

    new Object() {
      void foo() {}

      // violation below 'Method name 'testing_foo' must match pattern'
      void testing_foo() {}
    };
  }

  void testing_foo(@FooTest String str) {
    // violation above 'Method name 'testing_foo' must match pattern'
    class LocalFoo {
      void foo() {}

      // violation below 'Method name 'testing_foo' must match pattern'
      void testing_foo() {}
    }

    new Object() {
      void foo() {}

      // violation below 'Method name 'testing_foo' must match pattern'
      void testing_foo() {}
    };
  }

  @Test
  void testing_Foo() {} // violation 'Method name 'testing_Foo' must match pattern'

  @Test
  void testing_fOo() {} // violation 'Method name 'testing_fOo' must match pattern'

  @Test
  void testingFoo() {}

  @Test
  void testingFoo_foo() {}

  @Test
  void testing_0123() {} // violation 'Method name 'testing_0123' must match pattern'

  @Test
  void testing_0123_() {} // violation 'Method name 'testing_0123_' must match pattern'

  @Test
  void testing__0123() {} // violation 'Method name 'testing__0123' must match pattern'

  @Test
  void testing__0123_() {} // violation 'Method name 'testing__0123_' must match pattern'

  @Test
  void testing__0123__() {} // violation 'Method name 'testing__0123__' must match pattern'

  @Test
  void Testing_Foo() {} // violation 'Method name 'Testing_Foo' must match pattern'

  @Test
  void t_esting() {} // violation 'Method name 't_esting' must match pattern'

  @Test
  void _testing() {} // violation 'Method name '_testing' must match pattern'

  void Testing_Foo2() {} // violation 'Method name 'Testing_Foo2' must match pattern'

  @Test
  void TestingFooBad() {} // violation 'Method name 'TestingFooBad' must match pattern'

  @Test
  void testing_foo_() {} // violation 'Method name 'testing_foo_' must match pattern'

  @Test
  void testing_Foo_() {} // violation 'Method name 'testing_Foo_' must match pattern'

  @Test
  void testing__foo() {} // violation 'Method name 'testing__foo' must match pattern'

  @Test
  void testing__Foo() {} // violation 'Method name 'testing__Foo' must match pattern'

  @Test
  void testing__foo_() {} // violation 'Method name 'testing__foo_' must match pattern'

  @Test
  void testing__Foo_() {} // violation 'Method name 'testing__Foo_' must match pattern'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_foo1(String str) {
    class LocalFoo {
      void foo() {}

      // violation below 'Method name 'testing_foo' must match pattern'
      void testing_foo() {}
    }

    new Object() {
      void foo() {}

      // violation below 'Method name 'testing_foo' must match pattern'
      void testing_foo() {}
    };
  }

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_Foo1(String str) {} // violation 'Method name 'testing_Foo1' must match pattern'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_fOo1(String str) {} // violation 'Method name 'testing_fOo1' must match pattern'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testingFoo1(String str) {}

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testingFoo_foo1(String str) {}

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_01231(String str) {} // violation 'Method name 'testing_01231' must match pattern'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void Testing_Foo1(String str) {} // violation 'Method name 'Testing_Foo1' must match pattern'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void t_esting1(String str) {} // violation 'Method name 't_esting1' must match pattern'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void _testing1(String str) {} // violation 'Method name '_testing1' must match pattern'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void TestingFooBad1(String str) {} // violation 'Method name 'TestingFooBad1' must match pattern'

  @RepeatedTest(2)
  void testing_foo2() {}

  @RepeatedTest(2)
  void testing_Foo2() {} // violation 'Method name 'testing_Foo2' must match pattern'

  @RepeatedTest(2)
  void testing_fOo2() {} // violation 'Method name 'testing_fOo2' must match pattern'

  @RepeatedTest(2)
  void testingFoo2() {}

  @RepeatedTest(2)
  void testingFoo_foo2() {}

  @RepeatedTest(2)
  void testing_01232() {} // violation 'Method name 'testing_01232' must match pattern'

  @RepeatedTest(2)
  void Testing_Foo3() {} // violation 'Method name 'Testing_Foo3' must match pattern'

  @RepeatedTest(2)
  void t_esting2() {} // violation 'Method name 't_esting2' must match pattern'

  @RepeatedTest(2)
  void _testing2() {} // violation 'Method name '_testing2' must match pattern'

  @RepeatedTest(2)
  void TestingFooBad2() {} // violation 'Method name 'TestingFooBad2' must match pattern'

  @BeforeAll
  static void _testingFoooo() {} // violation 'Method name '_testingFoooo' must match pattern'

  @org.junit.jupiter.api.Test
  void testing_fq() {}

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

  @interface FooTest {}
}
