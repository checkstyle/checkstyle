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

  void Foo() {} // violation 'Method name 'Foo' is not valid per Google Java Style Guide.'

  void fOo() {} // violation 'Method name 'fOo' is not valid per Google Java Style Guide.'

  void f0o() {}

  void f$o() {} // violation 'Method name 'f\$o' is not valid per Google Java Style Guide.'

  void f_oo() {} // violation 'Method name 'f_oo' is not valid per Google Java Style Guide.'

  void f() {} // violation 'Method name 'f' is not valid per Google Java Style Guide.'

  void fO() {} // violation 'Method name 'fO' is not valid per Google Java Style Guide.'

  @Test
  void testing_foo() {
    class LocalFoo {
      void foo() {}

      // violation below 'Method name 'testing_foo' is not valid per Google Java Style Guide.'
      void testing_foo() {}
    }

    new Object() {
      void foo() {}

      // violation below 'Method name 'testing_foo' is not valid per Google Java Style Guide.'
      void testing_foo() {}
    };
  }

  void testing_foo(@FooTest String str) {
    // violation above 'Method name 'testing_foo' is not valid per Google Java Style Guide.'
    class LocalFoo {
      void foo() {}

      // violation below 'Method name 'testing_foo' is not valid per Google Java Style Guide.'
      void testing_foo() {}
    }

    new Object() {
      void foo() {}

      // violation below 'Method name 'testing_foo' is not valid per Google Java Style Guide.'
      void testing_foo() {}
    };
  }

  @Test
  void testing_Foo() {}
  // violation above, 'Method name 'testing_Foo' is not valid per Google Java Style Guide.'

  @Test
  void testing_fOo() {}
  // violation above, 'Method name 'testing_fOo' is not valid per Google Java Style Guide.'

  @Test
  void testingFoo() {}

  @Test
  void testingFoo_foo() {}

  @Test
  void testing_0123() {}
  // violation above, 'Method name 'testing_0123' is not valid per Google Java Style Guide.'

  @Test
  void testing_0123_() {}
  // violation above, 'Method name 'testing_0123_' is not valid per Google Java Style Guide.'

  @Test
  void testing__0123() {}
  // violation above, 'Method name 'testing__0123' is not valid per Google Java Style Guide.'

  @Test
  void testing__0123_() {}
  // violation above, 'Method name 'testing__0123_' is not valid per Google Java Style Guide.'

  @Test
  void testing__0123__() {}
  // violation above, 'Method name 'testing__0123__' is not valid per Google Java Style Guide.'

  @Test
  void Testing_Foo() {}
  // violation above, 'Method name 'Testing_Foo' is not valid per Google Java Style Guide.'

  @Test
  void t_esting() {}
  // violation above, 'Method name 't_esting' is not valid per Google Java Style Guide.'

  @Test
  void _testing() {} // violation 'Method name '_testing' is not valid per Google Java Style Guide.'

  void Testing_Foo2() {}
  // violation above, 'Method name 'Testing_Foo2' is not valid per Google Java Style Guide.'

  @Test
  void TestingFooBad() {}
  // violation above, 'Method name 'TestingFooBad' is not valid per Google Java Style Guide.'

  @Test
  void testing_foo_() {}
  // violation above, 'Method name 'testing_foo_' is not valid per Google Java Style Guide.'

  @Test
  void testing_Foo_() {}
  // violation above, 'Method name 'testing_Foo_' is not valid per Google Java Style Guide.'

  @Test
  void testing__foo() {}
  // violation above, 'Method name 'testing__foo' is not valid per Google Java Style Guide.'

  @Test
  void testing__Foo() {}
  // violation above, 'Method name 'testing__Foo' is not valid per Google Java Style Guide.'

  @Test
  void testing__foo_() {}
  // violation above, 'Method name 'testing__foo_' is not valid per Google Java Style Guide.'

  @Test
  void testing__Foo_() {}
  // violation above, 'Method name 'testing__Foo_' is not valid per Google Java Style Guide.'

  @Test
  public void testing_123Foo() {}
  // violation above, 'Method name 'testing_123Foo' is not valid per Google Java Style Guide.'

  // violation 2 lines below 'testing_123FOO' must contain no more than '1' consecutive capital'
  @Test
  public void testing_123FOO() {}
  // violation above, 'Method name 'testing_123FOO' is not valid per Google Java Style Guide.'

  // violation 2 lines below 'TESTING_123Foo' must contain no more than '1' consecutive capital'
  @Test
  public void TESTING_123Foo() {}
  // violation above, 'Method name 'TESTING_123Foo' is not valid per Google Java Style Guide.'

  @Test
  public void testing_Foo123() {}
  // violation above, 'Method name 'testing_Foo123' is not valid per Google Java Style Guide.'

  // violation 2 lines below 'testing_FOO123' must contain no more than '1' consecutive capital'
  @Test
  public void testing_FOO123() {}
  // violation above, 'Method name 'testing_FOO123' is not valid per Google Java Style Guide.'

  @Test
  public void testing_Foo_123() {}
  // violation above, 'Method name 'testing_Foo_123' is not valid per Google Java Style Guide.'

  @Test
  public void testing_123_Foo() {}
  // violation above, 'Method name 'testing_123_Foo' is not valid per Google Java Style Guide.'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_foo1(String str) {
    class LocalFoo {
      void foo() {}

      // violation below 'Method name 'testing_foo' is not valid per Google Java Style Guide.'
      void testing_foo() {}
    }

    new Object() {
      void foo() {}

      // violation below 'Method name 'testing_foo' is not valid per Google Java Style Guide.'
      void testing_foo() {}
    };
  }

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_Foo1(String str) {}
  // violation above, 'Method name 'testing_Foo1' is not valid per Google Java Style Guide.'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_fOo1(String str) {}
  // violation above, 'Method name 'testing_fOo1' is not valid per Google Java Style Guide.'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testingFoo1(String str) {}

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testingFoo_foo1(String str) {}

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_01231(String str) {}
  // violation above, 'Method name 'testing_01231' is not valid per Google Java Style Guide.'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void Testing_Foo1(String str) {}
  // violation above, 'Method name 'Testing_Foo1' is not valid per Google Java Style Guide.'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void t_esting1(String str) {}
  // violation above, 'Method name 't_esting1' is not valid per Google Java Style Guide.'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void _testing1(String str) {}
  // violation above, 'Method name '_testing1' is not valid per Google Java Style Guide.'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_124Foo() {}
  // violation above, 'Method name 'testing_124Foo' is not valid per Google Java Style Guide.'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  // violation below ''testing_124FOO' must contain no more than '1''
  void testing_124FOO() {}
  // violation above, 'Method name 'testing_124FOO' is not valid per Google Java Style Guide.'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  // violation below 'TESTING_124Foo' must contain no more than '1' consecutive capital letters'
  void TESTING_124Foo() {}
  // violation above, 'Method name 'TESTING_124Foo' is not valid per Google Java Style Guide.'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_Foo124() {}
  // violation above, 'Method name 'testing_Foo124' is not valid per Google Java Style Guide.'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  // violation below 'testing_FOO124' must contain no more than '1' consecutive capital letters'
  void testing_FOO124() {}
  // violation above, 'Method name 'testing_FOO124' is not valid per Google Java Style Guide.'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_Foo_124() {}
  // violation above, 'Method name 'testing_Foo_124' is not valid per Google Java Style Guide.'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_124_Foo() {}
  // violation above, 'Method name 'testing_124_Foo' is not valid per Google Java Style Guide.'

  @RepeatedTest(2)
  void testing_foo4() {}

  @RepeatedTest(2)
  void testing_Foo2() {}
  // violation above, 'Method name 'testing_Foo2' is not valid per Google Java Style Guide.'

  @RepeatedTest(2)
  void testing_fOo2() {}
  // violation above, 'Method name 'testing_fOo2' is not valid per Google Java Style Guide.'

  @RepeatedTest(2)
  void testingFoo2() {}

  @RepeatedTest(2)
  void testingFoo_foo2() {}

  @RepeatedTest(2)
  void testing_01232() {}
  // violation above, 'Method name 'testing_01232' is not valid per Google Java Style Guide.'

  @RepeatedTest(2)
  void Testing_Foo3() {}
  // violation above, 'Method name 'Testing_Foo3' is not valid per Google Java Style Guide.'

  @RepeatedTest(2)
  void t_esting2() {}
  // violation above, 'Method name 't_esting2' is not valid per Google Java Style Guide.'

  @RepeatedTest(2)
  void _testing2() {}
  // violation above, 'Method name '_testing2' is not valid per Google Java Style Guide.'

  @RepeatedTest(2)
  void TestingFooBad2() {}
  // violation above, 'Method name 'TestingFooBad2' is not valid per Google Java Style Guide.'

  @BeforeAll
  static void _testingFoooo() {}
  // violation above, 'Method name '_testingFoooo' is not valid per Google Java Style Guide.'

  @org.junit.jupiter.api.Test
  void testing_fq() {}

  class InnerFoo {
    void foo() {}

    void Foo() {} // violation 'Method name 'Foo' is not valid per Google Java Style Guide.'

    void fOo() {} // violation 'Method name 'fOo' is not valid per Google Java Style Guide.'

    void f0o() {}

    void f$o() {} // violation 'Method name 'f\$o' is not valid per Google Java Style Guide.'

    void f_oo() {} // violation 'Method name 'f_oo' is not valid per Google Java Style Guide.'

    void f() {} // violation 'Method name 'f' is not valid per Google Java Style Guide.'

    void fO() {} // violation 'Method name 'fO' is not valid per Google Java Style Guide.'

    void testing_foo() {}
    // violation above, 'Method name 'testing_foo' is not valid per Google Java Style Guide.'

    void testing_Foo() {}
    // violation above, 'Method name 'testing_Foo' is not valid per Google Java Style Guide.'

    void testing_fOo() {}
    // violation above, 'Method name 'testing_fOo' is not valid per Google Java Style Guide.'

    void testingFoo() {}

    void testingFoo_foo() {}
    // violation above, 'Method name 'testingFoo_foo' is not valid per Google Java Style Guide.'

    void testing_0123() {}
    // violation above, 'Method name 'testing_0123' is not valid per Google Java Style Guide.'

    void TestingFooBad() {}
    // violation above, 'Method name 'TestingFooBad' is not valid per Google Java Style Guide.'
  }

  InnerFoo anon =
      new InnerFoo() {
        void foo() {}

        void Foo() {} // violation 'Method name 'Foo' is not valid per Google Java Style Guide.'

        void fOo() {} // violation 'Method name 'fOo' is not valid per Google Java Style Guide.'

        void f0o() {}

        void f$o() {} // violation 'Method name 'f\$o' is not valid per Google Java Style Guide.'

        void f_oo() {} // violation 'Method name 'f_oo' is not valid per Google Java Style Guide.'

        void f() {} // violation 'Method name 'f' is not valid per Google Java Style Guide.'

        void fO() {} // violation 'Method name 'fO' is not valid per Google Java Style Guide.'
      };

  interface FooIn {
    void foo();

    void Foo(); // violation 'Method name 'Foo' is not valid per Google Java Style Guide.'

    void fOo(); // violation 'Method name 'fOo' is not valid per Google Java Style Guide.'

    void f0o();

    void f$o(); // violation 'Method name 'f\$o' is not valid per Google Java Style Guide.'

    void f_oo(); // violation 'Method name 'f_oo' is not valid per Google Java Style Guide.'

    void f(); // violation 'Method name 'f' is not valid per Google Java Style Guide.'

    void fO(); // violation 'Method name 'fO' is not valid per Google Java Style Guide.'
  }

  @interface FooTest {}
}
