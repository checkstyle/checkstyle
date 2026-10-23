/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = @return the *|This method returns
period = (default).


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

public class InputSummaryJavadocInlineForbidden {

    /**
     * {@summary A simple correct Javadoc.}
     */
    void foo1() {
    }

    /**
     * {@summary This code {@input Javadoc} is right.}
     */
    void foo3() {
    }
    // violation 2 lines below 'Summary .* missing an ending period.'
    /**
     * {@summary This code is wrong }
     */
    void foo5() {
    }
    // violation 2 lines below 'Summary .* missing an ending period.'
    /**
     * {@summary This code {@see Javadoc} is wrong }
     */
    void foo6() {
    }
    // violation 2 lines below 'Summary .* missing an ending period.'
    /**
     * {@summary As of , replaced by {@link #setBounds(int, int, int, int)}}
     */
    void foo11() {
    }
    // violation 2 lines below 'Forbidden summary fragment.'
    /**
     * {@summary This method returns something.}
     */
    public static final byte NUL = 0;
    // violation 2 lines below 'Summary javadoc is missing.'
    /**
     * {@summary <a href="mailto:vlad@htmlbook.ru"/>}
     */
    class InnerInputCorrectJavaDocParagraphCheck {
        // violation 2 lines below 'Summary .* missing an ending period.'
        /**
         * {@summary foooo@foooo}
         */
        public static final byte NUL = 0;

        /**
         * {@summary Some java@doc.}
         */
        public static final byte NUL_2 = 0;
        // violation 2 lines below 'Forbidden summary fragment.'
        /**
         * {@summary @return the
         * customer ID some javadoc.}
         */
        int geId() {
            return 666;
        }

        /**
         * {@summary As of JDK 1.1, replaced by {@link #setBounds(int, int, int, int)}.}
         */
        void foo3() {
        }

    }
}
