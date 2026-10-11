/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = ^@return the *|^This method returns *|^A \
                            [{]@code [a-zA-Z0-9]+[}]( is a )
period = (default).


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

/**
 * Some Javadoc A {@code Foo} is a simple Javadoc.
 */
class InputSummaryJavadocCorrect {

    /**
     * Some Javadoc This method returns.
     */
    public static final byte NUL = 0;

    /**
     * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}.
     */
    void foo3() {}

    /**
     * This is valid.
     * @throws Exception if a problem occurs.
     */
    void foo4() throws Exception {}

    /** An especially This method returns short bit of Javadoc. */
    void foo5() {}

    /**
     * An especially short
     * bit of Javadoc. This method returns.
     */
    void foo6() {}

    /** {@inheritDoc} */
    void foo7() {}

    /**
     * {@inheritDoc} */
    void foo8() {}

    /**
     * {@inheritDoc}
     */
    void foo9() {}

    /**
     *
     *
     * {@inheritDoc}
     */
    void foo10() {}

    /**
     * {@inheritDoc}mm
     */
    void foo9a() {}

    /**
     * {@inheritDoc}mm.
     */
    void foo11() {}

    /**
     * {@inheritDoc} M m m m
     */
    void foo12() {}

    /**
     * {@inheritDoc} M m m m.
     */
    void foo13() {}

    /**
     * mm. {@inheritDoc}
     */
    void foo14() {}

    /**
     * M m m m. {@inheritDoc}
     */
    void foo15() {}

    class InnerInputCorrectJavaDocParagraphCheck {

         /**
          * foooo@foooo.
          */
        public static final byte NUL = 0;

        /**
         * Some java@doc.
         * This method returns.
         */
        public static final byte NUL_2 = 0;

        /**
         * Returns the customer ID. This method returns.
         */
        int getId() {return 666;}

        /**
         * This is valid.
         * <a href="mailto:vlad@htmlbook.ru"/>.
         */
        void foo2() {}
    }
}
