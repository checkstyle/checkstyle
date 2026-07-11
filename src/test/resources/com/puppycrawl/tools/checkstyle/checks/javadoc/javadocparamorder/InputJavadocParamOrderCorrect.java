/*
JavadocParamOrder
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparamorder;

public class InputJavadocParamOrderCorrect {
    /**
     * Some text.
     *
     * @param aString Some text.
     * @param aInt Some text.
     * @return Some text.
     * @throws Exception Some text.
     * @deprecated Some text.
     */
    String method(String aString, int aInt) throws Exception {
        return "null";
    }

    /**
     * Some text.
     *
     * @param aString Some text.
     * @param aInt Some text.
     * @throws Exception Some text.
     * @serialData Some javadoc.
     */
    void method2(String aString, int aInt) throws Exception {}

    /**
    * Some text.
    *
    * @param aString Some text.
    * @return Some text.
    * @throws Exception Some text.
    */
    String method3(String aString) throws Exception {
        return "null";
    }

    static class InnerClass {
        /**
         * Some text.
         *
         * @param aDouble exists.
         * @param bDouble exists.
         * @param cDouble exists.
         * @return 1.0
         */
        private double method4(double aDouble, double bDouble, double cDouble) {
           return 1.0;
        }

        /**
         * Some text.
         *
         * @param aDouble exists.
         * @param bDouble exists.
         * @param cDouble does not exist.
         * @param eDouble exists.
         * @param fDouble does not exist.
         * @return 1.0
         */
        private double method5(double aDouble, double bDouble, double eDouble) {
           return 1.0;
        }
    }

    /**
     * Some text.
     *
     * @param bDouble does not exist
     * @param cDouble exists
     * @param dDouble does not exist
     * @param eDouble exists
     * @param fDouble does not exist
     * @return 1.0
     */
    static double method4(double aDouble, double cDouble, double eDouble) {
        return 1.0;
    }

    /**
     * Some text.
     *
     * @param bDouble does not exist
     * @param cDouble exists
     * @param dDouble does not exist
     * @param fDouble does not exist
     * @return 1.0
     */
    static double method5(double aDouble, double cDouble, double eDouble) {
        return 1.0;
    }

    /**
     * Some text.
     *
     * @param bDouble does not exist
     * @param dDouble does not exist
     * @param fDouble does not exist
     * @return
     */
    static double method6(double aDouble, double cDouble, double eDouble) {
        return 1.0;
    }

}
