/*
JavadocParamOrder
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

public class InputJavadocParamOrderTypeParams {

    /**
     * Method with type parameter in correct order.
     *
     * @param <T> type param
     * @param p1 first
     * @param p2 second
     */
    <T> void method1(String p1, String p2) {}

    /**
     * Method with type parameter out of order.
     *
     * @param p1 first // violation, Parameters should be in the same order
     * @param p2 second // violation, Parameters should be in the same order
     * @param <T> type param // violation, Parameters should be in the same order
     */
    <T> void method2(String p1, String p2) {}

    /**
     * Method with type parameter out of order.
     *
     * @param p1 first // violation, Parameters should be in the same order
     * @param <T> type  // violation, Parameters should be in the same order
     * @param p2 second
     */
    <T> void method3(String p1, String p2) {}

    /**
     * Method with multiple type parameters.
     *
     * @param <T> first type
     * @param <U> second type
     * @param p1 first param
     */
    <T, U> void method4(String p1) {}

    /**
     * Method with multiple type parameters out of order.
     *
     * @param <U> second type param // violation, Parameters should be in the same order
     * @param <T> first type param  // violation, Parameters should be in the same order
     * @param p1 first param
     */
    <T, U> void method5(String p1) {}

    /**
     * Method with mixed params and type params out of order.
     *
     * @param p1 first param // violation, Parameters should be in the same order
     * @param <T> first type param  // violation, Parameters should be in the same order
     * @param p2 second param       // violation, Parameters should be in the same order
     * @param <U> second type param // violation, Parameters should be in the same order
     */
    <T, U> void method6(String p1, String p2) {}

    /**
     * Method with type params and params, all in order as declared.
     *
     * @param <T> first type param
     * @param <U> second type param
     * @param p1 first param
     * @param p2 second param
     */
    <T, U> void method7(String p1, String p2) {}

    /**
     * Method with only some type params documented.
     *
     * @param <T> first type param
     * @param p1 first param
     * @param p2 second param
     */
    <T, U> void method8(String p1, String p2) {}

    /**
     * Method with type params reverse order.
     *
     * @param p2 the second param       // violation, Parameters should be in the same order
     * @param p1 the first param        // violation, Parameters should be in the same order
     * @param <U> the second type param // violation, Parameters should be in the same order
     * @param <T> the first type param  // violation, Parameters should be in the same order
     */
    <T, U> void method9(String p1, String p2) {}

    /**
     * Constructor with type parameter.
     *
     * @param <T> the type param
     * @param p1 first param
     */
    <T> InputJavadocParamOrderTypeParams(String p1) {}

    /**
     * Constructor with type parameter out of order.
     *
     * @param p1 first param     // violation, Parameters should be in the same order
     * @param <T> the type param // violation, Parameters should be in the same order
     */
    <T> InputJavadocParamOrderTypeParams(String p1, int p2) {}
}

