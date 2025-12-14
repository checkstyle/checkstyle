/*
GoogleMethodName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemethodname;

import org.junit.jupiter.api.Test;

/** Test invalid test method names. */
public class InputGoogleMethodNameInvalidTest {
    @Test
    void transferMoney_DeductsFromSource() {}
    // violation above, 'Method name .* is not valid per Google Java Style Guide.'

    @Test
    void Testing_Foo() {}
    // violation above, 'Method name 'Testing_Foo' is not valid per Google Java Style Guide.'

    @Test
    void testing__foo() {}
    // violation above, 'Method name 'testing__foo' is not valid per Google Java Style Guide.'

    @Test
    void testing_foo_() {}
    // violation above, 'Method name 'testing_foo_' is not valid per Google Java Style Guide.'

    @Test
    void _testing() {}
    // violation above, 'Method name '_testing' is not valid per Google Java Style Guide.'

    @Test
    void TestingFooBad() {}
    // violation above, 'Method name 'TestingFooBad' is not valid per Google Java Style Guide.'

    @Test
    void test_1value() {}
    // violation above, 'Method name 'test_1value' is not valid per Google Java Style Guide.'

    @Test
    void test_FOO_bar() {}
    // violation above, 'Method name 'test_FOO_bar' is not valid per Google Java Style Guide.'

    @Test
    void testing_a() {}
    // violation above, 'Method name 'testing_a' is not valid per Google Java Style Guide.'

    @Test
    void test_fO_bar() {}
    // violation above, 'Method name 'test_fO_bar' is not valid per Google Java Style Guide.'
}
