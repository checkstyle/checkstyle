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

  void Foo() {}

  // violation 2 lines above """Method name 'Foo' must be more than a character,
  // start lowercase, and not have a single lowercase followed by
  // uppercase, or consecutive uppercase."""

  void fOo() {}

  // violation 2 lines above """Method name 'fOo' must be more than a character,
  // start lowercase, and not have a single lowercase followed by
  // uppercase, or consecutive uppercase."""

  void f0o() {}

  void f$o() {}

  // violation 2 lines above """Method name 'f\$o' must be more than a character,
  // start lowercase, and not have a single lowercase followed by
  // uppercase, or consecutive uppercase."""

  void f_oo() {}

  // violation 2 lines above """Method name 'f_oo' has invalid underscore
  // usage, underscores only allowed between adjacent digits."""

  void f() {}

  // violation 2 lines above """Method name 'f' must be more than a character,
  // start lowercase, and not have a single lowercase followed by
  // uppercase, or consecutive uppercase."""

  void fO() {}

  // violation 2 lines above """Method name 'fO' must be more than a character,
  // start lowercase, and not have a single lowercase followed by
  // uppercase, or consecutive uppercase."""

  @Test
  void testing_foo() {
    class LocalFoo {
      void foo() {}

      void testing_foo() {}
      // violation above """Method name 'testing_foo' has invalid underscore
      // usage, underscores only allowed between adjacent digits."""
    }

    new Object() {
      void foo() {}

      void testing_foo() {}
      // violation above """Method name 'testing_foo' has invalid underscore
      // usage, underscores only allowed between adjacent digits."""
    };
  }

  void testing_foo(@FooTest String str) {
    // violation above """Method name 'testing_foo' has invalid underscore
    // usage, underscores only allowed between adjacent digits."""

    class LocalFoo {
      void foo() {}

      void testing_foo() {}
      // violation above """Method name 'testing_foo' has invalid underscore
      // usage, underscores only allowed between adjacent digits."""
    }

    new Object() {
      void foo() {}

      void testing_foo() {}
      // violation above """Method name 'testing_foo' has invalid underscore
      // usage, underscores only allowed between adjacent digits."""
    };
  }

  @Test
  void testing_Foo() {}

  // violation 2 lines above """Test method name 'testing_Foo' segment must be more
  // than a character, start lowercase, and not have a single lowercase followed
  // by uppercase, or consecutive uppercase."""

  @Test
  void testing_fOo() {}

  // violation 2 lines above """Test method name 'testing_fOo' segment must be more
  // than a character, start lowercase, and not have a single lowercase followed
  // by uppercase, or consecutive uppercase."""

  @Test
  void testingFoo() {}

  @Test
  void testingFoo_foo() {}

  @Test
  void testing_0123() {}

  // violation 2 lines above """Test method name 'testing_0123' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @Test
  void testing_0123_() {}

  // violation 2 lines above """Test method name 'testing_0123_' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @Test
  void testing__0123() {}

  // violation 2 lines above """Test method name 'testing__0123' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @Test
  void testing__0123_() {}

  // violation 2 lines above """Test method name 'testing__0123_' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @Test
  void testing__0123__() {}

  // violation 2 lines above """Test method name 'testing__0123__' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @Test
  void Testing_Foo() {}

  // violation 2 lines above """Test method name 'Testing_Foo' segment must be more
  // than a character, start lowercase, and not have a single lowercase followed
  // by uppercase, or consecutive uppercase."""

  @Test
  void t_esting() {}

  // violation 2 lines above """Test method name 't_esting' segment must be more
  // than a character, start lowercase, and not have a single lowercase followed
  // by uppercase, or consecutive uppercase."""

  @Test
  void _testing() {}

  // violation 2 lines above """Test method name '_testing' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  void Testing_Foo2() {}

  // violation 2 lines above """Method name 'Testing_Foo2' has invalid underscore
  // usage, underscores only allowed between adjacent digits."""

  @Test
  void TestingFooBad() {}

  // violation 2 lines above """Test method name 'TestingFooBad' segment must be more
  // than a character, start lowercase, and not have a single lowercase followed
  // by uppercase, or consecutive uppercase."""

  @Test
  void testing_foo_() {}

  // violation 2 lines above """Test method name 'testing_foo_' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @Test
  void testing_Foo_() {}

  // violation 2 lines above """Test method name 'testing_Foo_' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @Test
  void testing__foo() {}

  // violation 2 lines above """Test method name 'testing__foo' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @Test
  void testing__Foo() {}

  // violation 2 lines above """Test method name 'testing__Foo' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @Test
  void testing__foo_() {}

  // violation 2 lines above """Test method name 'testing__foo_' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @Test
  void testing__Foo_() {}

  // violation 2 lines above """Test method name 'testing__Foo_' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @Test
  public void testing_123Foo() {}

  // violation 2 lines above """Test method name 'testing_123Foo' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  // violation 2 lines below 'testing_123FOO' must contain no more than '1' consecutive capital'
  @Test
  public void testing_123FOO() {}

  // violation 2 lines above """Test method name 'testing_123FOO' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  // violation 2 lines below 'TESTING_123Foo' must contain no more than '1' consecutive capital'
  @Test
  public void TESTING_123Foo() {}

  // violation 2 lines above """Test method name 'TESTING_123Foo' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @Test
  public void testing_Foo123() {}

  // violation 2 lines above """Test method name 'testing_Foo123' segment must be more
  // than a character, start lowercase, and not have a single lowercase followed
  // by uppercase, or consecutive uppercase."""

  // violation 2 lines below 'testing_FOO123' must contain no more than '1' consecutive capital'
  @Test
  public void testing_FOO123() {}

  // violation 2 lines above """Test method name 'testing_FOO123' segment must be more
  // than a character, start lowercase, and not have a single lowercase followed
  // by uppercase, or consecutive uppercase."""

  @Test
  public void testing_Foo_123() {}

  // violation 2 lines above """Test method name 'testing_Foo_123' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @Test
  public void testing_123_Foo() {}

  // violation 2 lines above """Test method name 'testing_123_Foo' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_foo1(String str) {
    class LocalFoo {
      void foo() {}

      void testing_foo() {}
      // violation above """Method name 'testing_foo' has invalid underscore
      // usage, underscores only allowed between adjacent digits."""
    }

    new Object() {
      void foo() {}

      void testing_foo() {}
      // violation above """Method name 'testing_foo' has invalid underscore
      // usage, underscores only allowed between adjacent digits."""
    };
  }

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_Foo1(String str) {}

  // violation 2 lines above """Test method name 'testing_Foo1' segment must be more
  // than a character, start lowercase, and not have a single lowercase followed
  // by uppercase, or consecutive uppercase."""

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_fOo1(String str) {}

  // violation 2 lines above """Test method name 'testing_fOo1' segment must be more
  // than a character, start lowercase, and not have a single lowercase followed
  // by uppercase, or consecutive uppercase."""

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testingFoo1(String str) {}

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testingFoo_foo1(String str) {}

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_01231(String str) {}

  // violation 2 lines above """Test method name 'testing_01231' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void Testing_Foo1(String str) {}

  // violation 2 lines above """Test method name 'Testing_Foo1' segment must be more
  // than a character, start lowercase, and not have a single lowercase followed
  // by uppercase, or consecutive uppercase."""

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void t_esting1(String str) {}

  // violation 2 lines above """Test method name 't_esting1' segment must be more
  // than a character, start lowercase, and not have a single lowercase followed
  // by uppercase, or consecutive uppercase."""

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void _testing1(String str) {}

  // violation 2 lines above """Test method name '_testing1' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_124Foo() {}

  // violation 2 lines above """Test method name 'testing_124Foo' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  // violation below 'testing_124FOO' must contain no more than '1' consecutive capital letters'
  void testing_124FOO() {}

  // violation 2 lines above """Test method name 'testing_124FOO' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  // violation below 'TESTING_124Foo' must contain no more than '1' consecutive capital letters'
  void TESTING_124Foo() {}

  // violation 2 lines above """Test method name 'TESTING_124Foo' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_Foo124() {}

  // violation 2 lines above """Test method name 'testing_Foo124' segment must be more
  // than a character, start lowercase, and not have a single lowercase followed
  // by uppercase, or consecutive uppercase."""

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  // violation below 'testing_FOO124' must contain no more than '1' consecutive capital letters'
  void testing_FOO124() {}

  // violation 2 lines above """Test method name 'testing_FOO124' segment must be more
  // than a character, start lowercase, and not have a single lowercase followed
  // by uppercase, or consecutive uppercase."""

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_Foo_124() {}

  // violation 2 lines above """Test method name 'testing_Foo_124' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @ParameterizedTest
  @ValueSource(strings = {"racecar", "radar", "level", "madam", "noon"})
  void testing_124_Foo() {}

  // violation 2 lines above """Test method name 'testing_124_Foo' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @RepeatedTest(2)
  void testing_foo4() {}

  @RepeatedTest(2)
  void testing_Foo2() {}

  // violation 2 lines above """Test method name 'testing_Foo2' segment must be more
  // than a character, start lowercase, and not have a single lowercase followed
  // by uppercase, or consecutive uppercase."""

  @RepeatedTest(2)
  void testing_fOo2() {}

  // violation 2 lines above """Test method name 'testing_fOo2' segment must be more
  // than a character, start lowercase, and not have a single lowercase followed
  // by uppercase, or consecutive uppercase."""

  @RepeatedTest(2)
  void testingFoo2() {}

  @RepeatedTest(2)
  void testingFoo_foo2() {}

  @RepeatedTest(2)
  void testing_01232() {}

  // violation 2 lines above """Test method name 'testing_01232' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @RepeatedTest(2)
  void Testing_Foo3() {}

  // violation 2 lines above """Test method name 'Testing_Foo3' segment must be more
  // than a character, start lowercase, and not have a single lowercase followed
  // by uppercase, or consecutive uppercase."""

  @RepeatedTest(2)
  void t_esting2() {}

  // violation 2 lines above """Test method name 't_esting2' segment must be more
  // than a character, start lowercase, and not have a single lowercase followed
  // by uppercase, or consecutive uppercase."""

  @RepeatedTest(2)
  void _testing2() {}

  // violation 2 lines above """Test method name '_testing2' has invalid
  // underscore usage, underscore only allowed between letters or between digits"""

  @RepeatedTest(2)
  void TestingFooBad2() {}

  // violation 2 lines above """Test method name 'TestingFooBad2' segment must be more
  // than a character, start lowercase, and not have a single lowercase followed
  // by uppercase, or consecutive uppercase."""

  @BeforeAll
  static void _testingFoooo() {}

  // violation 2 lines above """Method name '_testingFoooo' has invalid underscore
  // usage, underscores only allowed between adjacent digits."""

  @org.junit.jupiter.api.Test
  void testing_fq() {}

  class InnerFoo {
    void foo() {}

    void Foo() {}

    // violation 2 lines above """Method name 'Foo' must be more than a character,
    // start lowercase, and not have a single lowercase followed by
    // uppercase, or consecutive uppercase."""

    void fOo() {}

    // violation 2 lines above """Method name 'fOo' must be more than a character,
    // start lowercase, and not have a single lowercase followed by
    // uppercase, or consecutive uppercase."""

    void f0o() {}

    void f$o() {}

    // violation 2 lines above """Method name 'f\$o' must be more than a character,
    // start lowercase, and not have a single lowercase followed by
    // uppercase, or consecutive uppercase."""

    void f_oo() {}

    // violation 2 lines above """Method name 'f_oo' has invalid underscore
    // usage, underscores only allowed between adjacent digits."""

    void f() {}

    // violation 2 lines above """Method name 'f' must be more than a character,
    // start lowercase, and not have a single lowercase followed by
    // uppercase, or consecutive uppercase."""

    void fO() {}

    // violation 2 lines above """Method name 'fO' must be more than a character,
    // start lowercase, and not have a single lowercase followed by
    // uppercase, or consecutive uppercase."""

    void testing_foo() {}

    // violation 2 lines above """Method name 'testing_foo' has invalid underscore
    // usage, underscores only allowed between adjacent digits."""

    void testing_Foo() {}

    // violation 2 lines above """Method name 'testing_Foo' has invalid underscore
    // usage, underscores only allowed between adjacent digits."""

    void testing_fOo() {}

    // violation 2 lines above """Method name 'testing_fOo' has invalid underscore
    // usage, underscores only allowed between adjacent digits."""

    void testingFoo() {}

    void testingFoo_foo() {}

    // violation 2 lines above """Method name 'testingFoo_foo' has invalid underscore
    // usage, underscores only allowed between adjacent digits."""

    void testing_0123() {}

    // violation 2 lines above """Method name 'testing_0123' has invalid underscore
    // usage, underscores only allowed between adjacent digits."""

    void TestingFooBad() {}
    // violation above """Method name 'TestingFooBad' must be more than a character,
    // start lowercase, and not have a single lowercase followed by
    // uppercase, or consecutive uppercase."""
  }

  InnerFoo anon =
      new InnerFoo() {
        void foo() {}

        void Foo() {}

        // violation 2 lines above """Method name 'Foo' must be more than a character,
        // start lowercase, and not have a single lowercase followed by
        // uppercase, or consecutive uppercase."""

        void fOo() {}

        // violation 2 lines above """Method name 'fOo' must be more than a character,
        // start lowercase, and not have a single lowercase followed by
        // uppercase, or consecutive uppercase."""

        void f0o() {}

        void f$o() {}

        // violation 2 lines above """Method name 'f\$o' must be more than a character,
        // start lowercase, and not have a single lowercase followed by
        // uppercase, or consecutive uppercase."""

        void f_oo() {}

        // violation 2 lines above """Method name 'f_oo' has invalid underscore
        // usage, underscores only allowed between adjacent digits."""

        void f() {}

        // violation 2 lines above """Method name 'f' must be more than a character,
        // start lowercase, and not have a single lowercase followed by
        // uppercase, or consecutive uppercase."""

        void fO() {}
        // violation above """Method name 'fO' must be more than a character,
        // start lowercase, and not have a single lowercase followed by
        // uppercase, or consecutive uppercase."""
      };

  interface FooIn {
    void foo();

    void Foo();

    // violation 2 lines above """Method name 'Foo' must be more than a character,
    // start lowercase, and not have a single lowercase followed by
    // uppercase, or consecutive uppercase."""

    void fOo();

    // violation 2 lines above """Method name 'fOo' must be more than a character,
    // start lowercase, and not have a single lowercase followed by
    // uppercase, or consecutive uppercase."""

    void f0o();

    void f$o();

    // violation 2 lines above """Method name 'f\$o' must be more than a character,
    // start lowercase, and not have a single lowercase followed by
    // uppercase, or consecutive uppercase."""

    void f_oo();

    // violation 2 lines above """Method name 'f_oo' has invalid underscore
    // usage, underscores only allowed between adjacent digits."""

    void f();

    // violation 2 lines above """Method name 'f' must be more than a character,
    // start lowercase, and not have a single lowercase followed by
    // uppercase, or consecutive uppercase."""

    void fO();
    // violation above """Method name 'fO' must be more than a character,
    // start lowercase, and not have a single lowercase followed by
    // uppercase, or consecutive uppercase."""
  }

  @interface FooTest {}
}
