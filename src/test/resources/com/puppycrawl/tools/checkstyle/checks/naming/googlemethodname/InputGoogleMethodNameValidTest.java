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
    void test1_3(){
    }

    @Test
    void testGeneral_logicWorks() {
    }

    @Test
    void openMenu_deletePreviousView() {
    }

    @Test
    void testingFoo() {
    }

    @Test
    void testingFoo1_1() {
    }

    @Test
    void testingFoo_fooBar() {
    }

    @ParameterizedTest
    @ValueSource(strings = { "a", "b" })
    void testingParam_fooWorks(String str) {
    }

    @RepeatedTest(2)
    void testingRepeated_fooWorks() {
    }

}
