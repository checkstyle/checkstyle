/*
JavadocParamOrder
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

public class InputJavadocParamOrderEdgeCases {

    /**
     * Method with no parameters.
     */
    void method1() {}

    /**
     * Method with single parameter.
     *
     * @param p1 the parameter
     */
    void method2(String p1) {}

    /**
     * Method with duplicate param tags, all same.
     *
     * @param p1 first
     * @param p1 duplicate
     * @param p1 duplicate
     */
    void method4(String p1) {}

    /**
     * Method where param tag appears out of order with its duplicate.
     *
     * @param p1 first
     * @param p2 second
     * @param p1 duplicate, out of order
     * @param p3 third
     */
    void method5(String p1, String p2, String p3) {}

    /**
     * Method with no documented params.
     */
    void method6(String p1, String p2) {}

    /**
     * Method with only last param documented.
     *
     * @param p3 third
     */
    void method8(String p1, String p2, String p3) {}

    /**
     * Method with only middle param documented.
     *
     * @param p2 second
     */
    void method9(String p1, String p2, String p3) {}

    /**
     * Constructor with no parameters.
     */
    InputJavadocParamOrderEdgeCases() {}

    /**
     * Constructor with duplicate params.
     *
     * @param p1 first
     * @param p2 second
     * @param p1 duplicate
     */
    InputJavadocParamOrderEdgeCases(String p1, String p2) {}

    /**
     * Constructor with only some params documented.
     *
     * @param p2 second
     */
    InputJavadocParamOrderEdgeCases(String p1, String p2, String p3) {}

    class ConstructorWithDirectParent {
        /**
         * @param p1 first
         * @param p2 second
         */
        ConstructorWithDirectParent(String p1, String p2) {}
    }
}

