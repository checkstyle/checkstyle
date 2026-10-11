/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = (default)4


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

class InputJavadocTagContinuationIndentationInnerClass {
    /**
     * Some javadoc.
     *
     * @version 1.0
     * @since Some javadoc.
     * @serialData Some javadoc.
     * @author max
     */
    class InnerClassWithAnnotations
    {
        /**
         * Some text.
         * @return Some text.
         * @see Some text.
         *     Some javadoc.
         * @param aString Some text.
         * @throws Exception Some text.
         *     Some javadoc.
         */
        String method(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @throws Exception Some text.
         *     Some javadoc.
         * @return Some text.
         * @param aString Some text.
         *     Some javadoc.
         */
        String method1(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @serialData Some javadoc.
         *     Some javadoc.
         * @param aString Some text.
         *     Some javadoc.
         * @throws Exception Some text.
         */
        void method2(String aString) throws Exception {}

        /**
         * Some text.
         * @see Some text.
         * @throws Exception Some text.
         */
        void method3() throws Exception {}

        /**
         * Some text.
         * @throws Exception Some text.
         * @serialData Some javadoc.
         * @return Some text.
         */
        String method4() throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         * @see Some text.
         * @return Some text.
         */
        String method5(String aString)
        {
            return "null";
        }

        // violation 7 lines below 'Line continuation .* expected level should be 4'
        // violation 9 lines below 'Line continuation .* expected level should be 4'
        /**
         * Some text.
         * @param aString Some text.
         * @return Some text.
         * @param aInt Some text.
         *    Some javadoc.
         * @throws Exception Some text.
         * @param aBoolean Some text.
         *    Some javadoc.
         * @see Some text.
         */
        String method6(String aString, int aInt, boolean aBoolean) throws Exception
        {
            return "null";
        }
    }
}
