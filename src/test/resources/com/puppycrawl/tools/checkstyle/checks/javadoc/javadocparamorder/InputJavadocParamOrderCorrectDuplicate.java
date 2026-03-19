/*
JavadocParamOrder
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

public class InputJavadocParamOrderCorrectDuplicate {

    /**
     * Method with duplicate params where both are adjacent and in order.
     *
     * @param p1 exists
     * @param p1 exists
     * @param p2 duplicate
     */
    void method1(String p1, String p2) {}

    /**
     * Method with duplicate params that are out of order.
     *
     * @param p1 exits
     * @param p2 exists
     * @param p1 duplicate
     */
    void method2(String p1, String p2) {}

    /**
     * Constructor with duplicate params in order.
     *
     * @param p1 exits
     * @param p1 duplicate
     * @param p2 exists
     */
    InputJavadocParamOrderCorrectDuplicate(String p1, String p2) {}

    /**
     * Multiple duplicates with correct ordering.
     *
     * @param p1 first
     * @param p1 duplicate
     * @param p2 second
     * @param p2 duplicate
     * @param p3 third
     */
    void method3(String p1, String p2, String p3) {}

    /**
     * Method with duplicates and extra params mixed.
     *
     * @param p1 exits
     * @param extra does not exist
     * @param p1 duplicate
     * @param p2 exists
     */
    void method4(String p1, String p2) {}

    /**
     * Method with multiple duplicates and intersection ordering.
     *
     * @param p1 occurs 3 times
     * @param extra1 does not exist
     * @param p1 duplicate
     * @param extra2 does not exist
     * @param p2 exists
     * @param p1 third duplicate
     * @param p3 exists
     */
    void method5(String p1, String p2, String p3) {}
}
