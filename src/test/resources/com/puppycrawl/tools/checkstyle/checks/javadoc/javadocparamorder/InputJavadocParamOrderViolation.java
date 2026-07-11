/*
JavadocParamOrder
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

public class InputJavadocParamOrderViolation {

    /**
     * Test method with mixed order.
     *
     * @param p3 third  // violation, Parameters should be in the same order
     * @param p1 first  // violation, Parameters should be in the same order
     * @param p2 second // violation, Parameters should be in the same order
     */
    void method2(String p1, String p2, String p3) {}

    /**
     * Test with complete reversal.
     *
     * @param c third  // violation, Parameters should be in the same order
     * @param b second
     * @param a first  // violation, Parameters should be in the same order
     */
    void method3(String a, String b, String c) {}

    /**
     * Test with extra and wrong order params.
     *
     * @param nonExistent does not exist
     * @param p2 second // violation, Parameters should be in the same order
     * @param p1 first  // violation, Parameters should be in the same order
     */
    void method5(String p1, String p2) {}

    /**
     * Test multiple out of order from intersection.
     *
     * @param a exists
     * @param nonExistent1 does not exist
     * @param c exists // violation, Parameters should be in the same order
     * @param nonExistent2 does not exist
     * @param b exists // violation, Parameters should be in the same order
     */
    void method6(String a, String b, String c) {}

    /**
     * Test with some params missing and some out of order.
     *
     * @param p3 exists // violation, Parameters should be in the same order
     * @param p1 exists // violation, Parameters should be in the same order
     */
    void method7(String p1, String p2, String p3) {}

    /**
     * Inner class with wrong param order.
     */
    class InnerClass {
        /**
         * Test method in inner class with wrong order.
         *
         * @param y exists // violation, Parameters should be in the same order
         * @param x exists // violation, Parameters should be in the same order
         */
        void innerMethod(String x, String y) {}
    }

    /**
     * Test constructor with wrong param order.
     *
     * @param p2 Parameter 2 desc. // violation, Parameters should be in the same order
     * @param p1 Parameter 1 desc. // violation, Parameters should be in the same order
     */
    InputJavadocParamOrderViolation(String p1, String p2) {}
}

