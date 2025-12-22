/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = ^@return the *|^This method returns |^A \
                            [{]@code [a-zA-Z0-9]+[}]( is a )
period = (default).


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

/**
 * A {@code Foo. Foo}
 * is a simple Javadoc. Some javadoc.
 */
class InputSummaryJavadocIncorrect4 {

    // violation below 'Forbidden summary fragment.'
     /**
      * A {@code InnerInputCorrectJavaDocParagraphCheck} is a simple code.
      */
    InnerInputCorrectJavaDocParagraphCheck anon = new InnerInputCorrectJavaDocParagraphCheck() {

        /**
         * Some Javadoc.
         */
        public static final byte NUL = 0;

        /**
         * Some Javadoc.
         */
        void emulated(String s) {}

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
    // violation below 'First sentence .* missing an ending period.'
         /**
          * mm{@inheritDoc}
          */
         void foo7() {}
    // violation below 'Summary javadoc is missing.'
         /**
          * {@link #setBounds(int,int,int,int)}
          */
         void foo8() {}
    // violation below 'Summary javadoc is missing.'
         /**
          *
          */
         void foo10() {}
    };
    // violation below 'First sentence .* missing an ending period.'
    /**
     * M m m m {@inheritDoc}
     */
    void foo7() {}
    // violation below 'Summary javadoc is missing.'
    /** */
    <T> T foo8(T t) {return null;}
    // violation below 'Summary javadoc is missing.'
    /** */
    String[] foo9() {return null;}

    /**
     * This is the summary.
     * {@summary This is NOT summary according to javadoc tool}
     */
    void testMethod() {
    }
}
