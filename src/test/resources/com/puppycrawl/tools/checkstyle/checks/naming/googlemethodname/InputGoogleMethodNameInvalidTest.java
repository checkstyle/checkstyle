/*
GoogleMethodName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemethodname;

import org.junit.jupiter.api.Test;

/** Test invalid test method names. */
public class InputGoogleMethodNameInvalidTest {
    @Test
    void transferMoney_DeductsFromSource() {}
    // violation above, 'Test method name .* is not valid, each segment must be lowerCamelCase.*'

    @Test
    void Testing_Foo() {}
    // violation above, ''Testing_Foo' is not valid, each segment must be lowerCamelCase.*'

    @Test
    void testing__foo() {}
    // violation above, 'Test method name 'testing__foo' has invalid underscore usage.*'

    @Test
    void testing_foo_() {}
    // violation above, 'Test method name 'testing_foo_' has invalid underscore usage.*'

    @Test
    void _testing() {}
    // violation above, 'Test method name '_testing' has invalid underscore usage.*'

    @Test
    void TestingFooBad() {}
    // violation above, ''TestingFooBad' is not valid, each segment must be lowerCamelCase.*'

    @Test
    void test_1value() {}
    // violation above, 'Test method name 'test_1value' has invalid underscore usage.*'

    @Test
    void test_FOO_bar() {}
    // violation above, ''test_FOO_bar' is not valid, each segment must be lowerCamelCase.*'

    @Test
    void testing_a() {}
    // violation above, ''testing_a' is not valid, each segment must be lowerCamelCase.*'

    @Test
    void test_fO_bar() {}
    // violation above, ''test_fO_bar' is not valid, each segment must be lowerCamelCase.*'

    @Test
    void solve6x6_returnsTrue() {
    // violation above, ''solve6x6_returnsTrue' has invalid underscore usage.*'
    }

    @Test
    void solve6x6_noSolution_returnsFalse() {
    // violation above, ''solve6x6_noSolution_returnsFalse' has invalid underscore usage.*'
    }
}
