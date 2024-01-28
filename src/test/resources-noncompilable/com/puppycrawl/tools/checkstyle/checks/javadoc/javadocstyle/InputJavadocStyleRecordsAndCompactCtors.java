/*
JavadocStyle
scope = (default)private
excludeScope = (default)null
checkFirstSentence = (default)true
endOfSentenceFormat = (default)([.?!][ \t\n\r\f<])|([.?!]$)
checkEmptyJavadoc = (default)false
checkHtml = (default)true
tokens = (default)ANNOTATION_DEF, ANNOTATION_FIELD_DEF, CLASS_DEF, CTOR_DEF, \
         ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, METHOD_DEF, PACKAGE_DEF, \
         VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

//non-compiled with javac: Compilable with Java14
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

        // violation 7 lines below 'Unclosed HTML tag found: <b>'
        // violation 9 lines below 'Extra HTML tag found: </td>'
        // violation 9 lines below 'Extra HTML tag found: </style>'
        // violation 10 lines below 'Unclosed HTML tag found: <code>dummy'
        /**
         * Test HTML in Javadoc comment
         * <dl>
         * <dt><b>
         * <dd>The dt and dd don't require end tags.
         * </dl>
         * </td>
         * <style>this tag isn't supported in Javadoc</style>
         *
         * @param arg1 <code>dummy
         */
        private void method5(int arg1) {
        }
    }

    /**
     * @param a A parameter
     */
    public record MySecondRecord() {
        static String props = "";

        // violation 3 lines below 'First sentence should end with a period.'
        // violation 3 lines below 'Extra HTML tag found: </code>'
        // violation 3 lines below 'should fail <'
        /**
         * Public check should fail</code>
         * should fail <
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
        // violation 3 lines below 'Unclosed HTML tag found: <code>'
        /**
         * This Javadoc contains unclosed tag.
         * <code>unclosed 'code' tag<code>
         */
        private static void unclosedTag() {
            System.out.println("stuff");
        }

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
