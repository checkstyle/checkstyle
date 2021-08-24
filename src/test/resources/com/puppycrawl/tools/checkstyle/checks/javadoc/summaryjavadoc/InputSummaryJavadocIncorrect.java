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
class InputSummaryJavadocIncorrect {

    /**
     * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}
     */
    void foo3() {}
    // violation below
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
    // violation below
    /**
     * <a href="mailto:vlad@htmlbook.ru"/>
     */
     class InnerInputCorrectJavaDocParagraphCheck {
    // violation below
         /**
          * foooo@foooo
          */
        public static final byte NUL = 0;

        /**
         * Some java@doc.
         */
        public static final byte NUL_2 = 0;
    // violation below
        /**
         * This method
         * returns some javadoc. Some javadoc.
         */
        boolean emulated() {return false;}
    // violation below
        /**
         * <a href="mailto:vlad@htmlbook.ru"/>
         */
        void foo2() {}
    // violation below
        /**
         * @return the
         * customer ID some javadoc.
         */
        int geId() {return 666;}

        /**
         * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}.
         */
        void foo3() {}
    // violation below
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
    // violation below
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
    // violation below
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
    // violation below
         /**
          * mm{@inheritDoc}
          */
         void foo7() {}
    // violation below
         /**
          * {@link #setBounds(int,int,int,int)}
          */
         void foo8() {}
    // violation below
         /**
          *
          */
         void foo10() {}
    };
    // violation below
    /**
     * M m m m {@inheritDoc}
     */
    void foo7() {}
    // violation below
    /** */
    <T> T foo8(T t) {return null;}
    // violation below
    /** */
    String[] foo9() {return null;}
}
