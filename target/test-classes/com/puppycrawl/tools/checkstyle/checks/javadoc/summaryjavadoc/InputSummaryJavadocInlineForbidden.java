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
    /**
     * {@summary This code is wrong }
     */ // violation above 'Summary .* missing an ending period.'
    void foo5() {
    }
    /**
     * {@summary This code {@see Javadoc} is wrong }
     */ // violation above 'Summary .* missing an ending period.'
    void foo6() {
    }
    /**
     * {@summary As of , replaced by {@link #setBounds(int, int, int, int)}}
     */ // violation above 'Summary .* missing an ending period.'
    void foo11() {
    }
    /**
     * {@summary This method returns something.}
     */ // violation above 'Forbidden summary fragment.'
    public static final byte NUL = 0;
    /**
     * {@summary <a href="mailto:vlad@htmlbook.ru"/>}
     */ // violation above 'Summary javadoc is missing.'
    class InnerInputCorrectJavaDocParagraphCheck {
        /**
         * {@summary foooo@foooo}
         */ // violation above 'Summary .* missing an ending period.'
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
