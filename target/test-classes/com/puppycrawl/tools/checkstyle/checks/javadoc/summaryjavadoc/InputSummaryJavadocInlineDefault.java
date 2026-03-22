/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
period = (default).


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

/**
 * Config: default.
 */
class InputSummaryJavadocInlineDefault {

    /**
     * {@summary A simple correct Javadoc.}
     */
    void foo1() {}
    /**
     * {@summary This code is wrong }
     */ // violation above 'Summary .* missing an ending period.'
    void foo5(){}
    /**
     * {@summary This code {@see Javadoc} is wrong }
     */ // violation above 'Summary .* missing an ending period.'
    void foo6(){}
    // violation below 'Summary javadoc is missing.'
    /**
     * {@sometag This code {@see Javadoc} is wrong }
     */
    void foo7(){}

    /**
     * {@summary <p>This code is right.</p>}
     */
    void foo8(){}
    /**
     * {@summary As of , replaced by {@link #setBounds(int,int,int,int)}}
     */ // violation above 'Summary .* missing an ending period.'
    void foo11() {}
    /**
     * {@summary {@throws Exception if a problem occurs}}
     */ // violation above 'Summary .* missing an ending period.'
    void foo12() throws Exception {}

    /** {@summary An especially short bit of Javadoc.} */
    void foo13() {}

    /**
     * {@summary Some Javadoc.}
     */
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

        /**
         * {@summary This method
         * returns some javadoc. Some javadoc.}
         */
        boolean emulated() {return false;}

        /**
         * {@summary @return the
         * customer ID some javadoc.}
         */
        int geId() {return 666;}

        /**
         * {@summary from {@link #setBounds(int,int,int,int)}.}
         */
        void foo3() {}

        /** {@summary  An especially short bit of Javadoc.} */
        void foo5() {}

        /**
         * {@summary An especially short bit of Javadoc.}
         */
        void foo6() {}
    }
}

