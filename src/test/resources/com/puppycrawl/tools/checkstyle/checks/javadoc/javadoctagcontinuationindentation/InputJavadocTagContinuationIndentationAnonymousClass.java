/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = (default)false
offset = (default)4


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

class InputJavadocTagContinuationIndentationAnonymousClass {
    Object anon = new Object()
    {
        /**
         * Some text.
         * @throws Exception Some text.
         * @param aString Some text.
         *   Some javadoc. // violation, 'Line continuation .* expected level should be 4'
         * @serialData Some javadoc.
         *    Some javadoc. // violation, 'Line continuation .* expected level should be 4'
         * @see Some text.
         * @return Some text.
         */
        String method(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @param aString Some text.
         *     Some javadoc.
         * @throws Exception Some text.
         * @return Some text.
         */
        String method1(String aString) throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @throws Exception Some text.
         *     Some javadoc.
         * @param aString Some text.
         */
        void method2(String aString) throws Exception {}

        /**
         * Some text.
         * @see Some text.
         *     Some javadoc.
         * @throws Exception Some text.
         */
        void method3() throws Exception {}

        /**
         * Some text.
         * @throws Exception Some text.
         * @return Some text.
         */
        String method4() throws Exception
        {
            return "null";
        }

        /**
         * Some text.
         * @see Some text.
         * @return Some text.
         * @param aString Some text.
         */
        String method5(String aString)
        {
            return "null";
        }

        /**
         * Some text.
         *       Some javadoc.
         * @param aString Some text.
         *    Some javadoc. // violation, 'Line continuation .* expected level should be 4'
         * @return Some text.
         * @param aInt Some text.
         *    Some javadoc. // violation, 'Line continuation .* expected level should be 4'
         * @throws Exception Some text.
         *    Some javadoc. // violation, 'Line continuation .* expected level should be 4'
         * @param aBoolean Some text.
         * @see Some text.
         */
        String method6(String aString, int aInt, boolean aBoolean) throws Exception
        {
            return "null";
        }
    };
}
