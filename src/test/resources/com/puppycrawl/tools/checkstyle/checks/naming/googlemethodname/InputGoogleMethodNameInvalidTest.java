/*
GoogleMethodName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemethodname;

import org.junit.jupiter.api.Test;

/** Test invalid test method names. */
public class InputGoogleMethodNameInvalidTest {
    @Test
    void transferMoney_DeductsFromSource() {}
    // violation above, 'Test method name .* is not valid. Each segment must start lowercase'

    @Test
    void Testing_Foo() {}
    // violation above, ''Testing_Foo' is not valid. Each segment must start lowercase'

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
    // violation above, ''TestingFooBad' is not valid. Each segment must start lowercase'

    @Test
    void test_1value() {}
    // violation above, 'Test method name 'test_1value' has invalid underscore usage'

    @Test
    void test_FOO_bar() {}
    // violation above, ''test_FOO_bar' is not valid. Each segment must start lowercase'

    @Test
    void testing_a() {}
    // violation above, ''testing_a' is not valid. Each segment must .* min 2 chars'

    @Test
    void test_fO_bar() {}
    // violation above, ''test_fO_bar' .* avoid single lowercase letter followed by uppercase'

    @Test
    void solve6x6_returnsTrue() {}
    // violation above, ''solve6x6_returnsTrue' has invalid underscore usage'

    @Test
    void solve6x6_noSolution_returnsFalse() {}
    // violation above, ''solve6x6_noSolution_returnsFalse' has invalid underscore usage'

    @Test
    void test_foo() {}
    // violation above 'Each segment.*contain at least one uppercase letter for multi-segment names'

    @Test
    void login_fails() {}
    // violation above 'Each segment.*contain at least one uppercase letter for multi-segment names'

    @Test
    void testFoo_fA() {}
    // violation above, ''testFoo_fA' is not valid.* avoid single lowercase.* followed by uppercase'

    @Test
    void aggregate_on_bi() {}
    // violation above 'Each segment.*contain at least one uppercase letter for multi-segment names'

    @Test
    void test_login() {}
    // violation above 'Each segment.*contain at least one uppercase letter for multi-segment names'

    @Test
    void testFoo_bar() {}
    // violation above 'Each segment.*contain at least one uppercase letter for multi-segment names'

    @Test
    void aggregate_on_bi_element_predicate() {}
    // violation above 'Each segment.*contain at least one uppercase letter for multi-segment names'

    @Test
    void testLogin_succeeds() {}
    // violation above 'Each segment.*contain at least one uppercase letter for multi-segment names'

    @Test
    void test_loginFails() {}
    // violation above 'Each segment.*contain at least one uppercase letter for multi-segment names'
}
