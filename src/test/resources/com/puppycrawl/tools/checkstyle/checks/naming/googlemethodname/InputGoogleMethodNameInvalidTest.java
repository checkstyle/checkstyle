/*
GoogleMethodName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemethodname;

import org.junit.jupiter.api.Test;

/** Test invalid test method names. */
public class InputGoogleMethodNameInvalidTest {
    @Test
    void transferMoney_DeductsFromSource() {}
    // violation above """Test method name 'transferMoney_DeductsFromSource' segment must be more
    // than a character, start lowercase, and not have a single lowercase followed by
    // uppercase, or consecutive uppercase."""

    @Test
    void Testing_Foo() {}
    // violation above """Test method name 'Testing_Foo' segment must be more
    // than a character, start lowercase, and not have a single lowercase followed by
    // uppercase, or consecutive uppercase."""

    @Test
    void testing__foo() {}
    // violation above """Test method name 'testing__foo' has invalid underscore
    // usage, underscore only allowed between letters or between digits."""

    @Test
    void testing_foo_() {}
    // violation above """Test method name 'testing_foo_' has invalid underscore
    // usage, underscore only allowed between letters or between digits."""

    @Test
    void _testing() {}
    // violation above """Test method name '_testing' has invalid underscore
    // usage, underscore only allowed between letters or between digits."""

    @Test
    void TestingFooBad() {}
    // violation above """Test method name 'TestingFooBad' segment must be more
    // than a character, start lowercase, and not have a single lowercase followed by
    // uppercase, or consecutive uppercase."""

    @Test
    void test_1value() {}
    // violation above """Test method name 'test_1value' has invalid underscore
    // usage, underscore only allowed between letters or between digits."""

    @Test
    void test_FOO_bar() {}
    // violation above """Test method name 'test_FOO_bar' segment must be more
    // than a character, start lowercase, and not have a single lowercase followed by
    // uppercase, or consecutive uppercase."""


    @Test
    void testing_a() {}
    // violation above """Test method name 'testing_a' segment must be more
    // than a character, start lowercase, and not have a single lowercase followed by
    // uppercase, or consecutive uppercase."""

    @Test
    void test_fO_bar() {}
    // violation above """Test method name 'test_fO_bar' segment must be
    // more than a character, start lowercase, and not have a single lowercase followed
    // by uppercase, or consecutive uppercase."""


    @Test
    void solve6x6_returnsTrue() {}
    // violation above """Test method name 'solve6x6_returnsTrue' has invalid underscore
    // usage, underscore only allowed between letters or between digits."""

    @Test
    void solve6x6_noSolution_returnsFalse() {}
    // violation above """Test method name 'solve6x6_noSolution_returnsFalse' has invalid underscore
    // usage, underscore only allowed between letters or between digits."""

    @Test
    void foo$bar() {}
    // violation above, 'Method name 'foo\$bar' must only have letters, digits and underscores.'

    @Test
    void fooBAR() {}
    // violation above """Test method name 'fooBAR' segment must be more than a character,
    // start lowercase, and not have a single lowercase followed by uppercase,
    // or consecutive uppercase."""
}
