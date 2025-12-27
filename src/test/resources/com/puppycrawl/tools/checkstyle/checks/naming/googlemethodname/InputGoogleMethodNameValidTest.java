/*
GoogleMethodName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemethodname;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.RepeatedTest;

/** Test valid test method names. */
public class InputGoogleMethodNameValidTest {

    @Test
    void transferMoney_deductsFromSource() {
    }

    @Test
    void solve6x6_returnsTrue() {
    }

    @Test
    void solve6x6_noSolution_returnsFalse() {
    }

    @Test
    void test_general_logic() {
    }

    @Test
    void openMenu_deletePreviousView() {
    }

    @Test
    void testingFoo() {
    }

    @Test
    void testingFoo_foo() {
    }

    @ParameterizedTest
    @ValueSource(strings = { "a", "b" })
    void testing_foo1(String str) {
    }

    @RepeatedTest(2)
    void testing_foo4() {
    }
}
