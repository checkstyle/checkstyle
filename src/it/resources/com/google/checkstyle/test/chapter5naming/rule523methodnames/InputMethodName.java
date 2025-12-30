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

  void Foo() {} // violation 'Method name 'Foo' must be lowerCamelCase.*'

  void fOo() {} // violation 'Method name 'fOo' must be lowerCamelCase.*'

  void f0o() {}

  void f$o() {} // violation 'Method name 'f\$o' must be lowerCamelCase.*'

  void f_oo() {} // violation 'Method name 'f_oo' has invalid underscore usage.*'

  void f() {} // violation 'Method name 'f' must be lowerCamelCase.* at least 2 characters.*'

  void fO() {} // violation 'Method name 'fO' must be lowerCamelCase.*'

  @Test
  void testing_foo() {
    class LocalFoo {
      void foo() {}

      // violation below 'Method name 'testing_foo' has invalid underscore usage.*'
      void testing_foo() {}
    }

    new Object() {
      void foo() {}

      // violation below 'Method name 'testing_foo' has invalid underscore usage.*'
      void testing_foo() {}
    };
  }

  void testing_foo(@FooTest String str) {
    // violation above 'Method name 'testing_foo' has invalid underscore usage.*'
    class LocalFoo {
      void foo() {}

      // violation below 'Method name 'testing_foo' has invalid underscore usage.*'
      void testing_foo() {}
    }

    new Object() {
      void foo() {}

      // violation below 'Method name 'testing_foo' has invalid underscore usage.*'
      void testing_foo() {}
    };
  }

  @Test // violation below, ''testing_Foo' is not valid, each segment must be lowerCamelCase.*'
  void testing_Foo() {}

  @Test // violation below, ''testing_fOo' is not valid, each segment must be lowerCamelCase.*'
  void testing_fOo() {}

  @Test
  void testingFoo() {}

  @Test
  void testingFoo_foo() {}

  @Test // violation below, 'Test method name 'testing_0123' has invalid underscore usage.*'
  void testing_0123() {}

  @Test // violation below, 'Test method name 'testing_0123_' has invalid underscore usage.*'
  void testing_0123_() {}

  @Test // violation below, 'Test method name 'testing__0123' has invalid underscore usage.*'
  void testing__0123() {}

  @Test // violation below, 'Test method name 'testing__0123_' has invalid underscore usage.*'
  void testing__0123_() {}

  @Test // violation below, 'Test method name 'testing__0123__' has invalid underscore usage.*'
  void testing__0123__() {}

  @Test // violation below, ''Testing_Foo' is not valid, each segment must be lowerCamelCase.*'
  void Testing_Foo() {}

  @Test // violation below, ''t_esting' is not valid, each segment must be lowerCamelCase.*'
  void t_esting() {}

  @Test
  void _testing() {} // violation 'Test method name '_testing' has invalid underscore usage.*'

  // violation below, 'Method name 'Testing_Foo2' has invalid underscore usage.*'
  void Testing_Foo2() {}

  @Test // violation below, ''TestingFooBad' is not valid, each segment must be lowerCamelCase.*'
  void TestingFooBad() {}

  @Test // violation below, 'Test method name 'testing_foo_' has invalid underscore usage.*'
  void testing_foo_() {}

  @Test // violation below, 'Test method name 'testing_Foo_' has invalid underscore usage.*'
  void testing_Foo_() {}

  @Test // violation below, 'Test method name 'testing__foo' has invalid underscore usage.*'
  void testing__foo() {}

  @Test // violation below, 'Test method name 'testing__Foo' has invalid underscore usage.*'
  void testing__Foo() {}

  @Test // violation below, 'Test method name 'testing__foo_' has invalid underscore usage.*'
  void testing__foo_() {}

  @Test // violation below, 'Test method name 'testing__Foo_' has invalid underscore usage.*'
  void testing__Foo_() {}

  @Test // violation below, 'Test method name 'testing_123Foo' has invalid underscore usage.*'
  public void testing_123Foo() {}

  // violation 2 lines below 'testing_123FOO' must contain no more than '1' consecutive capital'
  @Test
  public void testing_123FOO() {
    // violation above, 'Test method name 'testing_123FOO' has invalid underscore usage.*'
  }

  // violation 2 lines below 'TESTING_123Foo' must contain no more than '1' consecutive capital'
  @Test // violation below, 'Test method name 'TESTING_123Foo' has invalid underscore usage.*'
  public void TESTING_123Foo() {}

  @Test // violation below, ''testing_Foo123' is not valid, each segment must be lowerCamelCase.*'
  public void testing_Foo123() {}

  // violation 2 lines below 'testing_FOO123' must contain no more than '1' consecutive capital'
  @Test // violation below, ''testing_FOO123' is not valid, each segment must be lowerCamelCase.*'
  public void testing_FOO123() {}

  @Test // violation below, 'Test method name 'testing_Foo_123' has invalid underscore usage.*'
  public void testing_Foo_123() {}

  @Test // violation below, 'Test method name 'testing_123_Foo' has invalid underscore usage.*'
  public void testing_123_Foo() {}

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_foo1(String str) {
    class LocalFoo {
      void foo() {}

      // violation below 'Method name 'testing_foo' has invalid underscore usage.*'
      void testing_foo() {}
    }

    new Object() {
      void foo() {}

      // violation below 'Method name 'testing_foo' has invalid underscore usage.*'
      void testing_foo() {}
    };
  }

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_Foo1(String str) {
    // violation above, ''testing_Foo1' is not valid, each segment must be lowerCamelCase.*'
  }

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_fOo1(String str) {
    // violation above, ''testing_fOo1' is not valid, each segment must be lowerCamelCase.*'
  }

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testingFoo1(String str) {}

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testingFoo_foo1(String str) {}

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_01231(String str) {
    // violation above, 'Test method name 'testing_01231' has invalid underscore usage.*'
  }

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void Testing_Foo1(String str) {
    // violation above, ''Testing_Foo1' is not valid, each segment must be lowerCamelCase.*'
  }

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void t_esting1(String str) {
    // violation above, ''t_esting1' is not valid, each segment must be lowerCamelCase.*'
  }

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void _testing1(String str) {
    // violation above, 'Test method name '_testing1' has invalid underscore usage.*'
  }

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_124Foo() {} // violation ''testing_124Foo' has invalid underscore usage.*'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  // violation below ''testing_124FOO' must contain no more than '1''
  void testing_124FOO() {} // violation ''testing_124FOO' has invalid underscore usage.*'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  // violation below 'TESTING_124Foo' must contain no more than '1' consecutive capital letters'
  void TESTING_124Foo() {
    // violation above, 'Test method name 'TESTING_124Foo' has invalid underscore usage.*'
  }

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_Foo124() {
    // violation above, ''testing_Foo124' is not valid, each segment must be lowerCamelCase.*'
  }

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  // violation below 'testing_FOO124' must contain no more than '1' consecutive capital letters'
  void testing_FOO124() {
    // violation above, ''testing_FOO124' is not valid, each segment must be lowerCamelCase.*'
  }

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_Foo_124() {
    // violation above, 'Test method name 'testing_Foo_124' has invalid underscore usage.*'
  }

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_124_Foo() {
    // violation above, 'Test method name 'testing_124_Foo' has invalid underscore usage.*'
  }

  @RepeatedTest(2)
  void testing_foo4() {}

  @RepeatedTest(2)
  void testing_Foo2() {
    // violation above, ''testing_Foo2' is not valid, each segment must be lowerCamelCase.*'
  }

  @RepeatedTest(2)
  void testing_fOo2() {
    // violation above, ''testing_fOo2' is not valid, each segment must be lowerCamelCase.*'
  }

  @RepeatedTest(2)
  void testingFoo2() {}

  @RepeatedTest(2)
  void testingFoo_foo2() {}

  @RepeatedTest(2)
  void testing_01232() {} // violation ''testing_01232' has invalid underscore usage.*'

  @RepeatedTest(2)
  void Testing_Foo3() {
    // violation above, ''Testing_Foo3' is not valid, each segment must be lowerCamelCase.*'
  }

  @RepeatedTest(2)
  void t_esting2() {} // violation ''t_esting2' is not valid, each segment must be lowerCamelCase.*'

  @RepeatedTest(2)
  void _testing2() {} // violation 'Test method name '_testing2' has invalid underscore usage.*'

  @RepeatedTest(2)
  void TestingFooBad2() {
    // violation above, ''TestingFooBad2' is not valid, each segment must be lowerCamelCase.*'
  }

  @BeforeAll
  static void _testingFoooo() {
    // violation above, 'Method name '_testingFoooo' has invalid underscore usage.*'
  }

  @org.junit.jupiter.api.Test
  void testing_fq() {}

  class InnerFoo {
    void foo() {}

    void Foo() {} // violation 'Method name 'Foo' must be lowerCamelCase.*'

    void fOo() {} // violation 'Method name 'fOo' must be lowerCamelCase.*'

    void f0o() {}

    void f$o() {} // violation 'Method name 'f\$o' must be lowerCamelCase.*'

    void f_oo() {} // violation 'Method name 'f_oo' has invalid underscore usage.*'

    void f() {} // violation 'Method name 'f' must be lowerCamelCase.*'

    void fO() {} // violation 'Method name 'fO' must be lowerCamelCase.*'

    void testing_foo() {} // violation ''testing_foo' has invalid underscore usage.*'

    void testing_Foo() {
      // violation above, 'Method name 'testing_Foo' has invalid underscore usage.*'
    }

    void testing_fOo() {
      // violation above, 'Method name 'testing_fOo' has invalid underscore usage.*'
    }

    void testingFoo() {}

    void testingFoo_foo() {
      // violation above, 'Method name 'testingFoo_foo' has invalid underscore usage.*'
    }

    void testing_0123() {
      // violation above, 'Method name 'testing_0123' has invalid underscore usage.*'
    }

    void TestingFooBad() {}
    // violation above, 'Method name 'TestingFooBad' must be lowerCamelCase.*'
  }

  InnerFoo anon =
      new InnerFoo() {
        void foo() {}

        void Foo() {} // violation 'Method name 'Foo' must be lowerCamelCase.*'

        void fOo() {} // violation 'Method name 'fOo' must be lowerCamelCase.*'

        void f0o() {}

        void f$o() {} // violation 'Method name 'f\$o' must be lowerCamelCase.*'

        void f_oo() {} // violation 'Method name 'f_oo' has invalid underscore usage.*'

        void f() {} // violation 'Method name 'f' must be lowerCamelCase.*'

        void fO() {} // violation 'Method name 'fO' must be lowerCamelCase.*'
      };

  interface FooIn {
    void foo();

    void Foo(); // violation 'Method name 'Foo' must be lowerCamelCase.* start with lowercase.*'

    void fOo(); // violation 'Method name 'fOo' must be lowerCamelCase.*'

    void f0o();

    void f$o(); // violation 'Method name 'f\$o' must be lowerCamelCase.*'

    void f_oo(); // violation 'Method name 'f_oo' has invalid underscore usage.*'

    void f(); // violation 'Method name 'f' must be lowerCamelCase.* at least 2 characters.*'

    void fO(); // violation 'Method name 'fO' must be lowerCamelCase.*'
  }

  @interface FooTest {}
}
