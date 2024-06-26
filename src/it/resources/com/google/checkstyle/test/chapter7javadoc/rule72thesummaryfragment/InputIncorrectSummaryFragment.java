package com.google.checkstyle.test.chapter7javadoc.rule72thesummaryfragment;

/**
 * A {@code Foo. Foo}
 * is a simple Javadoc. Some javadoc.
 */
class InputIncorrectSummaryFragment {

/*warn*//**
     * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}
     */
    void foo3() {}
    // violation 4 lines above 'First sentence of Javadoc is missing an ending period.'

    /**
     * @throws Exception if a problem occurs
     */
    void foo4() throws Exception {}
    // violation 4 lines above 'Summary javadoc is missing.'

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

    /**
     * <a href="mailto:vlad@htmlbook.ru"/>
     */
     class InnerInputCorrectJavaDocParagraphCheck {
     // violation 4 lines above 'Summary javadoc is missing.'

        /**
          * foooo@foooo
          */
        public static final byte NUL = 0;
        // violation 4 lines above 'First sentence of Javadoc is missing an ending period.'

        /**
         * Some java@doc.
         */
        public static final byte NUL_2 = 0;

        /**
         * This method
         * returns some javadoc. Some javadoc.
         */
        boolean emulated() {return false;}
        // violation 5 lines above 'Forbidden summary fragment.'

        /**
         * <a href="mailto:vlad@htmlbook.ru"/>
         */
        void foo2() {}
        // violation 4 lines above 'Summary javadoc is missing.'

        /**
         * @return the
         * customer ID some javadoc.
         */
        int geId() {return 666;}
        // violation 5 lines above 'Summary javadoc is missing.'

        /**
         * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}.
         */
        void foo3() {}

        /**
         * @throws Exception if a problem occurs
         */
        void foo4() throws Exception {}
        // violation 4 lines above 'Summary javadoc is missing.'

        /** An especially short bit of Javadoc. */
        void foo5() {}

        /**
         * An especially short bit of Javadoc.
         */
        void foo6() {}
    }

    /**
      * A {@code InnerInputCorrectJavaDocParagraphCheck} is a simple code.
      */
    InnerInputCorrectJavaDocParagraphCheck anon = new InnerInputCorrectJavaDocParagraphCheck() {
    // violation 4 lines above 'Forbidden summary fragment.'

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

        /**
         * @throws Exception if a problem occurs
         */
        void foo4() throws Exception {}
        // violation 4 lines above 'Summary javadoc is missing.'

        /** An especially short bit of Javadoc. */
        void foo5() {}

        /**
         * An especially short bit of Javadoc.
         */
        void foo6() {}
    };
}
