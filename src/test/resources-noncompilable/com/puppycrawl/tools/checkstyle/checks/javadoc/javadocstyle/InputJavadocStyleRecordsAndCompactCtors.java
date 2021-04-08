//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

/*
 * Config: default
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
        private void method5(int arg1) {
        }
    }

    /**
     * @param a A parameter
     */
    public record MySecondRecord() {
        static String props = "";

        /**
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
        private static void unclosedTag() {
            System.out.println("stuff");
        }

        public MyFourthRecord { // ok
            /**
             * No period at the end of this sentence
             */
            String myOtherString = "mystring"; // ok
        }
    }

    public record MyFifthRecord() { // ok
        /**
         * No period here on compact ctor
         */
        public MyFifthRecord { // violation
        }
    }
}
