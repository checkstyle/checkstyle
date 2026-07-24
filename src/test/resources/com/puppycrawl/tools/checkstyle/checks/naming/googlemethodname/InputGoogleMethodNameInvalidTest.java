/*
GoogleMethodName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemethodname;

import org.junit.jupiter.api.Test;

/** Test invalid test method names. */
public class InputGoogleMethodNameInvalidTest {
    @Test
    void transferMoney_DeductsFromSource() {}
    // violation above, '.* 'transferMoney_DeductsFromSource' segment must .*, start lowercase'

    @Test
    void Testing_Foo() {}
    // violation above, 'Test method name 'Testing_Foo' segment .* start lowercase'

    @Test
    void testing__foo() {}
    // violation above, 'Test method name 'testing__foo' has invalid underscore usage'

    @Test
    void testing_foo_() {}
    // violation above, 'Test method name 'testing_foo_' has invalid underscore usage'

    @Test
    void _testing() {}
    // violation above, 'Test method name '_testing' has invalid underscore usage'

    @Test
    void TestingFooBad() {}
    // violation above, 'Test method name 'TestingFooBad' segment must .* start lowercase'

    @Test
    void test_1value() {}
    // violation above, 'Test method name 'test_1value' has invalid underscore usage'

    @Test
    void test_FOO_bar() {}
    // violation above, 'Test method name 'test_FOO_bar' segment must .* start lowercase'

    @Test
    void testing_a() {}
    // violation above, 'Test method name 'testing_a' segment must be more than a character'

    @Test
    void test_fO_bar() {}
    // violation above, 'Test method name 'test_fO_bar' .* single lowercase followed by uppercase.'

    @Test
    void solve6x6_returnsTrue() {}
    // violation above, 'Test method name '.*' has invalid underscore usage'

    @Test
    void solve6x6_noSolution_returnsFalse() {}
    // violation above, '.* 'solve6x6_noSolution_returnsFalse' has invalid underscore usage'

    @Test
    void foo$bar() {}
    // violation above, '.* 'foo\$bar' must only have letters, digits and underscores'

}
