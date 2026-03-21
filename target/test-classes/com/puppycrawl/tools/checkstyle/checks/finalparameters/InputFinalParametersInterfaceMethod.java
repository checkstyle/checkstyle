/*
FinalParameters
ignorePrimitiveTypes = (default)false
ignoreUnnamedParameters = (default)true
tokens = METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.finalparameters;

/**
 * Test cases for detecting missing final parameters in interface methods.
 */
public interface InputFinalParametersInterfaceMethod {
    static void method1B(Object param1) { // violation, 'param1' should be final
    }

    static void method1G(final Object param1) {
    }

    private void method2B(Object param1) { // violation, 'param1' should be final
    }

    private void method2G(final Object param1) {
    }

    default void method3B(Object param1) { // violation, 'param1' should be final
    }

    default void method3G(final Object param1) {
    }

    void method4(Object param1);
}
