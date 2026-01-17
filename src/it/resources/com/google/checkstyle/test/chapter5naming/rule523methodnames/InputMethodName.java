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

  void Foo() {} // violation 'Method name 'Foo' must start with a lowercase letter, min 2 chars'

  void fOo() {} // violation 'Method name 'fOo' must start with a lowercase letter, min 2 chars'

  void f0o() {}

  void f$o() {} // violation 'Method name 'f\$o' must start with a lowercase letter, min 2 chars'

  void f_oo() {} // violation 'Method name 'f_oo' has invalid underscore usage'

  void f() {} // violation 'Method name 'f' must start with a lowercase letter, min 2 chars'

  void fO() {} // violation 'Method name 'fO'.* avoid single lowercase letter followed by uppercase'

  @Test
  void testing_foo() {
    class LocalFoo {
      void foo() {}

      // violation below 'Method name 'testing_foo' has invalid underscore usage'
      void testing_foo() {}
    }

    new Object() {
      void foo() {}

      // violation below 'Method name 'testing_foo' has invalid underscore usage'
      void testing_foo() {}
    };
  }

  void testing_foo(@FooTest String str) {
    // violation above 'Method name 'testing_foo' has invalid underscore usage'
    class LocalFoo {
      void foo() {}

      // violation below 'Method name 'testing_foo' has invalid underscore usage'
      void testing_foo() {}
    }

    new Object() {
      void foo() {}

      // violation below 'Method name 'testing_foo' has invalid underscore usage'
      void testing_foo() {}
    };
  }

  @Test // violation below, ''testing_Foo' is not valid. Each segment must start with a lowercase'
  void testing_Foo() {}

  @Test // violation below, ''testing_fOo' is not valid. Each segment must start with a lowercase'
  void testing_fOo() {}

  @Test
  void testingFoo() {}

  @Test
  void testingFoo_foo() {}

  @Test // violation below, 'Test method name 'testing_0123' has invalid underscore usage'
  void testing_0123() {}

  @Test // violation below, 'Test method name 'testing_0123_' has invalid underscore usage'
  void testing_0123_() {}

  @Test // violation below, 'Test method name 'testing__0123' has invalid underscore usage'
  void testing__0123() {}

  @Test // violation below, 'Test method name 'testing__0123_' has invalid underscore usage'
  void testing__0123_() {}

  @Test // violation below, 'Test method name 'testing__0123__' has invalid underscore usage'
  void testing__0123__() {}

  @Test // violation below, ''Testing_Foo' is not valid. Each segment must start with a lowercase'
  void Testing_Foo() {}

  @Test // violation below, ''t_esting' is not valid. Each segment .* min 2 chars'
  void t_esting() {}

  @Test
  void _testing() {} // violation 'Test method name '_testing' has invalid underscore usage'

  // violation below, 'Method name 'Testing_Foo2' has invalid underscore usage'
  void Testing_Foo2() {}

  @Test // violation below, ''TestingFooBad' is not valid. Each segment must start with a lowercase'
  void TestingFooBad() {}

  @Test // violation below, 'Test method name 'testing_foo_' has invalid underscore usage'
  void testing_foo_() {}

  @Test // violation below, 'Test method name 'testing_Foo_' has invalid underscore usage'
  void testing_Foo_() {}

  @Test // violation below, 'Test method name 'testing__foo' has invalid underscore usage'
  void testing__foo() {}

  @Test // violation below, 'Test method name 'testing__Foo' has invalid underscore usage'
  void testing__Foo() {}

  @Test // violation below, 'Test method name 'testing__foo_' has invalid underscore usage'
  void testing__foo_() {}

  @Test // violation below, 'Test method name 'testing__Foo_' has invalid underscore usage'
  void testing__Foo_() {}

  @Test // violation below, 'Test method name 'testing_123Foo' has invalid underscore usage'
  public void testing_123Foo() {}

  // violation 2 lines below 'testing_123FOO' must contain no more than '1' consecutive capital'
  @Test // violation below, 'Test method name 'testing_123FOO' has invalid underscore usage'
  public void testing_123FOO() {}

  // violation 2 lines below 'TESTING_123Foo' must contain no more than '1' consecutive capital'
  @Test // violation below, 'Test method name 'TESTING_123Foo' has invalid underscore usage'
  public void TESTING_123Foo() {}

  @Test // violation below ''testing_Foo123' is not valid. Each segment must start with a lowercase'
  public void testing_Foo123() {}

  // violation 2 lines below 'testing_FOO123' must contain no more than '1' consecutive capital'
  @Test // violation below ''testing_FOO123' is not valid. Each segment must start with a lowercase'
  public void testing_FOO123() {}

  @Test // violation below, 'Test method name 'testing_Foo_123' has invalid underscore usage'
  public void testing_Foo_123() {}

  @Test // violation below, 'Test method name 'testing_123_Foo' has invalid underscore usage'
  public void testing_123_Foo() {}

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_foo1(String str) {
    class LocalFoo {
      void foo() {}

      // violation below 'Method name 'testing_foo' has invalid underscore usage'
      void testing_foo() {}
    }

    new Object() {
      void foo() {}

      // violation below 'Method name 'testing_foo' has invalid underscore usage'
      void testing_foo() {}
    };
  }

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  // violation below, ''testing_Foo1' is not valid. Each segment must start with a lowercase'
  void testing_Foo1(String str) {}

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  // violation below, ''testing_fOo1' is not valid. Each segment must start with a lowercase'
  void testing_fOo1(String str) {}

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testingFoo1(String str) {}

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testingFoo_foo1(String str) {}

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  // violation below, 'Test method name 'testing_01231' has invalid underscore usage'
  void testing_01231(String str) {}

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  // violation below, ''Testing_Foo1' is not valid. Each segment must start with a lowercase'
  void Testing_Foo1(String str) {}

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  // violation below, ''t_esting1' is not valid. Each segment .* min 2 chars'
  void t_esting1(String str) {}

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  // violation below, 'Test method name '_testing1' has invalid underscore usage'
  void _testing1(String str) {}

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_124Foo() {} // violation ''testing_124Foo' has invalid underscore usage'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  // violation below ''testing_124FOO' must contain no more than '1''
  void testing_124FOO() {} // violation ''testing_124FOO' has invalid underscore usage'

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  // 2 violations 3 lines below:
  //    ''TESTING_124Foo' must contain no more than '1' consecutive capital letters'
  //    'Test method name 'TESTING_124Foo' has invalid underscore usage'
  void TESTING_124Foo() {}

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  // violation below, ''testing_Foo124' is not valid. Each segment must start with a lowercase'
  void testing_Foo124() {}

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  // 2 violations 3 lines below:
  //    ''testing_FOO124' must contain no more than '1' consecutive capital letters'
  //    ''testing_FOO124' is not valid. Each segment must start with a lowercase'
  void testing_FOO124() {}

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  // violation below, 'Test method name 'testing_Foo_124' has invalid underscore usage'
  void testing_Foo_124() {}

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  // violation below, 'Test method name 'testing_124_Foo' has invalid underscore usage'
  void testing_124_Foo() {}

  @RepeatedTest(2)
  void testing_foo4() {}

  @RepeatedTest(2)
  // violation below, ''testing_Foo2' is not valid. Each segment must start with a lowercase'
  void testing_Foo2() {}

  @RepeatedTest(2)
  // violation below, ''testing_fOo2' is not valid. Each segment must start with a lowercase'
  void testing_fOo2() {}

  @RepeatedTest(2)
  void testingFoo2() {}

  @RepeatedTest(2)
  void testingFoo_foo2() {}

  @RepeatedTest(2)
  void testing_01232() {} // violation ''testing_01232' has invalid underscore usage'

  @RepeatedTest(2)
  // violation below, ''Testing_Foo3' is not valid. Each segment must start with a lowercase'
  void Testing_Foo3() {}

  @RepeatedTest(2)
  void t_esting2() {} // violation ''t_esting2' is not valid. Each segment .* min 2 chars'

  @RepeatedTest(2)
  void _testing2() {} // violation 'Test method name '_testing2' has invalid underscore usage'

  @RepeatedTest(2)
  // violation below, ''TestingFooBad2' is not valid. Each segment must start with a lowercase'
  void TestingFooBad2() {}

  @BeforeAll // violation below, 'Method name '_testingFoooo' has invalid underscore usage'
  static void _testingFoooo() {}

  @org.junit.jupiter.api.Test
  void testing_fq() {}

  class InnerFoo {
    void foo() {}

    void Foo() {} // violation 'Method name 'Foo' must start with a lowercase letter, min 2 chars'

    // violation below, 'Method name 'fOo' .* avoid single lowercase letter followed by uppercase'
    void fOo() {}

    void f0o() {}

    void f$o() {} // violation 'Method name 'f\$o' must start with a lowercase letter, min 2 chars'

    void f_oo() {} // violation 'Method name 'f_oo' has invalid underscore usage'

    void f() {} // violation 'Method name 'f' must start with a lowercase letter, min 2 chars'

    // violation below, 'Method name 'fO' .* avoid single lowercase letter followed by uppercase'
    void fO() {}

    void testing_foo() {} // violation ''testing_foo' has invalid underscore usage'

    // violation below, 'Method name 'testing_Foo' has invalid underscore usage'
    void testing_Foo() {}

    // violation below, 'Method name 'testing_fOo' has invalid underscore usage'
    void testing_fOo() {}

    void testingFoo() {}

    // violation below, 'Method name 'testingFoo_foo' has invalid underscore usage'
    void testingFoo_foo() {}

    // violation below, 'Method name 'testing_0123' has invalid underscore usage'
    void testing_0123() {}

    void TestingFooBad() {}
    // violation above, 'Method name 'TestingFooBad' must start with a lowercase letter'
  }

  InnerFoo anon =
      new InnerFoo() {
        void foo() {}

        void Foo() {} // violation 'Method name 'Foo' must start with a lowercase letter'

        // violation below, ''fOo' .* avoid single lowercase letter followed by uppercase'
        void fOo() {}

        void f0o() {}

        void f$o() {} // violation 'Method name 'f\$o' must start with a lowercase letter'

        void f_oo() {} // violation 'Method name 'f_oo' has invalid underscore usage'

        void f() {} // violation 'Method name 'f' must start with a lowercase letter, min 2 chars'

        // violation below, ''fO' .* avoid single lowercase letter followed by uppercase'
        void fO() {}
      };

  interface FooIn {
    void foo();

    void Foo(); // violation 'Method name 'Foo' must start with a lowercase letter, min 2 chars'

    // violation below, 'Method name 'fOo' .* avoid single lowercase letter followed by uppercase'
    void fOo();

    void f0o();

    void f$o(); // violation 'Method name 'f\$o' must start with a lowercase letter, min 2 chars'

    void f_oo(); // violation 'Method name 'f_oo' has invalid underscore usage'

    void f(); // violation 'Method name 'f' must start with a lowercase letter, min 2 chars'

    // violation below, 'Method name 'fO' .* avoid single lowercase letter followed by uppercase'
    void fO();
  }

  @interface FooTest {}
}
