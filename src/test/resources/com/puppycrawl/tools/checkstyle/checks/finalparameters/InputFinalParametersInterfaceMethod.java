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
    static void method1(Object param1) { // violation, 'param1' should be final
    }

    private void method2(Object param1) { // violation, 'param1' should be final
    }

    default void method3(Object param1) { // violation, 'param1' should be final
    }

    void method4(Object param1);
}
