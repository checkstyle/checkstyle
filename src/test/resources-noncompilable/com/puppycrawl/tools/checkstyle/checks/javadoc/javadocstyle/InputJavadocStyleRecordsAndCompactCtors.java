//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;
/* Config:
 *
 * scope = private
 * excludeScope = null
 * checkFirstSentence = true
 * endOfSentenceFormat = "([.?!][ \t\n\r\f<])|([.?!]$)"
 * checkEmptyJavadoc = false
 * checkHtml = true
 * tokens = {ANNOTATION_DEF , ANNOTATION_FIELD_DEF , CLASS_DEF , CTOR_DEF , ENUM_CONSTANT_DEF ,
 *  ENUM_DEF , INTERFACE_DEF , METHOD_DEF , PACKAGE_DEF , VARIABLE_DEF, RECORD_DEF,
 *  COMPACT_CTOR_DEF}
 */
public class InputJavadocStyleRecordsAndCompactCtors { // ok

    public record MyRecord() { // ok

        /**
         * This Javadoc is missing an ending period
         */
        private static String second; // violation

        /**
         * We don't want {@link com.puppycrawl.tools.checkstyle.checks.JavadocStyleCheck}
         * tags to stop the scan for the end of sentence.
         *
         * @see Something
         */
        public MyRecord() { // ok
        }

        /**
         * This is ok!
         */
        private void method1() { // ok
        }

        /**
         * // violation
         * This should fail even.though.there are embedded periods
         */
        private void method4() { // violation
        }

        /**
         * Test HTML in Javadoc comment
         * <dl>
         * <dt><b>This guy is missing end of bold tag // violation
         * <dd>The dt and dd don't require end tags.
         * </dl>
         * </td>Extra tag shouldn't be here // violation
         * <style>this tag isn't supported in Javadoc</style> // violation
         *
         * @param arg1 <code>dummy. // violation
         */
        private void method5(int arg1) { // violation
        }
    }

    /**
     * @param a A parameter
     */
    public record MySecondRecord() {
        static String props = "";

        /**
         * // violation
         * Public check should fail</code> // violation and line below, too
         * should fail <
         */
        public void method8() { // violation
        }
    }

    /**
     *
     */
    public record MyThirdRecord(String myString) { // ok
    }

    public record MyFourthRecord(String myString) { // ok
        /**
         * This Javadoc contains unclosed tag.
         * <code>unclosed 'code' tag<code> // violation
         */
        private static void unclosedTag() { // violation
            System.out.println("stuff");
        }

        public MyFourthRecord { // ok
            /**
             * No period at the end of this sentence // ok
             */
            String myOtherString = "mystring"; // ok
        }
    }

    public record MyFifthRecord() { // ok
        /**
         * No period here on compact ctor // violation
         */
        public MyFifthRecord { // violation
        }
    }
}
