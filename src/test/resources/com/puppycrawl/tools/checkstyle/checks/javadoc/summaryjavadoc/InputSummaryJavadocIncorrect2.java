/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
period = (default).


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

/**
 * A {@code Foo. Foo}
 * is a simple Javadoc. Some javadoc.
 */
class InputSummaryJavadocIncorrect2 {

    // violation below 'First sentence .* missing an ending period.'
    /**
     * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}
     */
    void foo3() {}
    // violation below 'Summary javadoc is missing.'
    /**
     * @throws Exception if a problem occurs
     */
    void foo4() throws Exception {}

    /** An especially short bit of Javadoc. */
    void foo5() {}

    /**
     * An especially short bit of Javadoc.
     */
    void foo6() {}

    /**
     * Some Javadoc.
     */
    public static final byte NUL = 0;
    // violation below 'Summary javadoc is missing.'
    /**
     * <a href="mailto:vlad@htmlbook.ru"/>
     */
     class InnerInputCorrectJavaDocParagraphCheck {
    // violation below 'First sentence .* missing an ending period.'
         /**
          * foooo@foooo
          */
        public static final byte NUL = 0;

        /**
         * Some java@doc.
         */
        public static final byte NUL_2 = 0;

        /**
         * This method
         * returns some javadoc. Some javadoc.
         */
        boolean emulated() {return false;}
    // violation below 'Summary javadoc is missing.'
        /**
         * <a href="mailto:vlad@htmlbook.ru"/>
         */
        void foo2() {}
    // violation below 'Summary javadoc is missing.'
        /**
         * @return the
         * customer ID some javadoc.
         */
        int geId() {return 666;}

        /**
         * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}.
         */
        void foo3() {}
    // violation below 'Summary javadoc is missing.'
        /**
         * @throws Exception if a problem occurs
         */
        void foo4() throws Exception {}

        /** An especially short bit of Javadoc. */
        void foo5() {}

        /**
         * An especially short bit of Javadoc.
         */
        void foo6() {}
    }
}
