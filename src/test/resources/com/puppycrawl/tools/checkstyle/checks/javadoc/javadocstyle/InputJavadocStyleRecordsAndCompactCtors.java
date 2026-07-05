/*
JavadocStyle
checkEmptyJavadoc = (default)false
checkFirstSentence = (default)true
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
excludeScope = (default)null
scope = (default)private
violateExecutionOnNonTightHtml = (default)false
tokens = (default)ANNOTATION_DEF, ANNOTATION_FIELD_DEF, CLASS_DEF, CTOR_DEF, \
         ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, METHOD_DEF, PACKAGE_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF

*/


package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyleRecordsAndCompactCtors {

    public record MyRecord() {

        // violation 1 lines below 'First sentence should end with a period.'
        /**
         * This Javadoc is missing an ending period
         */
        private static String second;

        /**
         * We don't want {@link com.puppycrawl.tools.checkstyle.checks.JavadocStyleCheck}
         * tags to stop the scan for the end of sentence.
         *
         * @see Something
         */
        public MyRecord() {
        }

        /**
         * This is ok!
         */
        private void method1() {
        }

        // violation 1 lines below 'First sentence should end with a period.'
        /**
         * This should fail even.though.there are embedded periods
         */
        private void method4() {
        }

    }

    /**
     * @param a A parameter
     */
    public record MySecondRecord() {
        static String props = "";

        // violation below 'First sentence should end with a period.'
        /**
         * Public check should fail
         */
        public void method8() {
        }
    }

    /**
     *
     */
    public record MyThirdRecord(String myString) {
    }

    public record MyFourthRecord(String myString) {
        public MyFourthRecord {
            /**
             * No period at the end of this sentence
             */
            String myOtherString = "mystring";
        }
    }

    public record MyFifthRecord() {
        // violation 1 lines below 'First sentence should end with a period.'
        /**
         * No period here on compact ctor
         */
        public MyFifthRecord {
        }
    }
}
