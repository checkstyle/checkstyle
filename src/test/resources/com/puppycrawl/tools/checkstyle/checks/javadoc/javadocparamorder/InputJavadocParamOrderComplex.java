/*
JavadocParamOrder
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

public class InputJavadocParamOrderComplex {

    /**
     * Method with all params documented but some out of order in middle.
     *
     * @param aString first param
     * @param cString third param  // violation, Parameters should be in the same order
     * @param bString second param // violation, Parameters should be in the same order
     * @param dString fourth param
     */
    void method1(String aString, String bString, String cString, String dString) {}

    /**
     * Method where intersection requires filtering.
     *
     * @param extra1 does not exist
     * @param p5 fifth param // violation, Parameters should be in the same order
     * @param extra2 does not exist
     * @param p1 first param // violation, Parameters should be in the same order
     * @param extra3 does not exist
     * @param p3 third param
     * @param extra4 does not exist
     * @param p2 second param // violation, Parameters should be in the same order
     * @param extra5 does not exist
     */
    void method2(String p1, String p2, String p3, String p4, String p5) {}

    /**
     * Method with less documentation and reverse order in what's documented.
     *
     * @param p5 fifth param // violation, Parameters should be in the same order
     * @param p3 third param
     * @param p1 first param // violation, Parameters should be in the same order
     */
    void method3(String p1, String p2, String p3, String p4, String p5) {}

    /**
     * Complex case with type params mixed in.
     *
     * @param <T> first type
     * @param <U> second type
     * @param p1 first param  // violation, Parameters should be in the same order
     * @param p2 second param // violation, Parameters should be in the same order
     * @param <V> third type  // violation, Parameters should be in the same order
     * @param p3 third param
     */
    <T, U, V> void method4(String p1, String p2, String p3) {}

    /**
     * Constructor with many params in a mixed order.
     *
     * @param p5 fifth  // violation, Parameters should be in the same order
     * @param p1 first  // violation, Parameters should be in the same order
     * @param p4 fourth // violation, Parameters should be in the same order
     * @param p2 second // violation, Parameters should be in the same order
     * @param p3 third  // violation, Parameters should be in the same order
     */
    InputJavadocParamOrderComplex(String p1, String p2, String p3, String p4, String p5) {}

    /**
     * Method with only first param documented.
     *
     * @param p1 first param
     */
    void method5(String p1, String p2, String p3) {}

    /**
     * Static method with param order violation, Parameters should be in the same order.
     *
     * @param p3 third  // violation, Parameters should be in the same order
     * @param p2 second
     * @param p1 first  // violation, Parameters should be in the same order
     */
    static void method6(String p1, String p2, String p3) {}

    /**
     * Private method with violation.
     *
     * @param p2 second // violation, Parameters should be in the same order
     * @param p1 first  // violation, Parameters should be in the same order
     */
    private void method7(String p1, String p2) {}

    /**
         * Protected method with violation.
     *
     * @param p3 third // violation, Parameters should be in the same order
     * @param p2 second
     * @param p1 first // violation, Parameters should be in the same order
     */
    protected void method8(String p1, String p2, String p3) {}

    /**
     * Final method with params out of order.
     *
     * @param p2 second // violation, Parameters should be in the same order
     * @param p1 first  // violation, Parameters should be in the same order
     */
    final void method9(String p1, String p2) {}

    /**
     * Synchronized method, Parameters should be in the same order.
     *
     * @param p2 second // violation, Parameters should be in the same order
     * @param p1 first  // violation, Parameters should be in the same order
     */
    synchronized void method10(String p1, String p2) {}
}

