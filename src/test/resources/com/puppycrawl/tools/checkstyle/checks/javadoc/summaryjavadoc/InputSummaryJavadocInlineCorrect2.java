/*
SummaryJavadoc
violateExecutionOnNonTightHtml = (default)false
forbiddenSummaryFragments = (default)^$
period = (default).


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

class InputSummaryJavadocInlineCorrect2 {
    /**
     * {@summary foo.}
     */
    private static final byte NUL = 0;

    /**
     * {@summary Some java@doc.
     * This method returns.}
     */
    private static final byte NUL_2 = 0;

    /**
     * {@summary Returns the customer ID.
     * This method returns. }
     */
    private int getId() {return 666;}

    /**
     * {@summary This is valid.
     * <a href="mailto:vlad@htmlbook.ru"/>.}
     */
    private void foo2() {}

    /**
     * {@summary As of JDK 1.1,
     * replaced by {@link #setBounds(int,int,int,int)}. This method returns.}
     */
    private void foo3() {}

    /**
     * {@summary This is description. }
     * @throws Exception if a problem occurs.
     */
    private void foo4() throws Exception {}

    /**
     * {@summary An especially short (int... A) bit of Javadoc. This
     * method returns.}
     */
    void foo6() {}

    InputSummaryJavadocInlineCorrectTwo iden =
        new InputSummaryJavadocInlineCorrectTwo() {
        /**
         * {@summary JAXB 1.0 only default validation event handler.}
         */
        private static final byte NUL = 0;

        /**
         * {@summary Returns the current state.
         * This method returns.}
         */
        private boolean emulated(String s) {return false;}

        /**
         * {@summary As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}.}
         */
        private void foo3() {}

        /**
         * {@summary This is valid.}
         * @throws Exception if a problem occurs.
         */
        private void foo4() throws Exception {}

        /** {@summary An especially short bit of Javadoc.} */
        void foo5() {}

        /**
         * {@summary An especially short bit of Javadoc.}
         */
        void foo6() {}

        /**
         * {@summary This code is <p>right</p>. }
         */
        private void foo7(){}

        /**
         * {@summary Some Javadoc. This method returns some javadoc.}
         */
        private boolean emulated() {return false;}

        /**
         * {@summary Some Javadoc. This method returns some javadoc. Some Javadoc.}
         */
        private boolean emulated1() {return false;}

        /**
         * {@summary This is valid.}
         * @return Some Javadoc the customer ID.
         */
        private int geId() {return 666;}

        /**
         * {@summary This is valid.}
         * @return Sentence one. Sentence two.
         */
        private String twoSentences() {return "Sentence one. Sentence two.";}

        /** {@summary Stop instances being created.} **/
        private String twoSentences1() {return "Sentence one. Sentence two.";}
    };
}
