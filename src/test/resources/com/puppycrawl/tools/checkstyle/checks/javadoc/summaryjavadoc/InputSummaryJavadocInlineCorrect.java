package com.puppycrawl.tools.checkstyle.checks.javadoc.summaryjavadoc;

/**
 * Config: default.
 */
class InputSummaryJavadocInlineCorrect {

    /**
     * {@summary Simple JavaDoc. }
     */
    private void foo1() {} // ok

    /**
     * {@summary {@code ABC} Javadoc. }
     */
    private void foo2() {} // ok

    public class InputSummaryJavadocInlineParagraphCheck {
        /**
         *  {@summary foo.}
         */
        public static final byte NUL = 0; // ok

        /**
         * {@summary Some java@doc.
         * This method returns.}
         */
        public static final byte NUL_2 = 0; // ok

        /**
         * {@summary Returns the customer ID.
         *  This method returns. }
         */
        int getId() {return 666;} // ok

        /**
         * {@summary This is valid.
         * <a href="mailto:vlad@htmlbook.ru"/>.}
         */
        void foo2() {} // ok

        /**
         * {@summary As of JDK 1.1,
         * replaced by {@link #setBounds(int,int,int,int)}. This method returns.}
         */
        void foo3() {} // ok

        /**
         * {@summary This is description. }
         * @throws Exception if a problem occurs.
         */
        void foo4() throws Exception {} // ok

        /**
         * {@summary JAXB Provider Use Only: Provides partial default
         * implementations for some of the javax.xml.bind interfaces.}
         */
        void foo5() {} // ok

        /**
         * {@summary An especially short (int... A) bit of Javadoc. This
         * method returns.}
         */
        void foo6() {} // ok
    }

    InputSummaryJavadocInlineParagraphCheck iden =
        new InputSummaryJavadocInlineParagraphCheck() { // ok
        /**
         * {@summary JAXB 1.0 only default validation event handler.}
         */
        public static final byte NUL = 0; // ok

        /**
         * {@summary Returns the current state.
         * This method returns.}
         */
        boolean emulated(String s) {return false;} // ok

        /**
         * {@summary As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}.}
         */
        void foo3() {} // ok

        /**
         * {@summary This is valid.}
         * @throws Exception if a problem occurs.
         */
        void foo4() throws Exception {} // ok

        /** {@summary An especially short bit of Javadoc.} */
        void foo5() {} // ok

        /**
         * {@summary An especially short bit of Javadoc.}
         */
        void foo6() {} // ok

        /**
         * {@summary This code is <p>right</p>. }
         */
        void foo7(){} // ok

        /**
         * {@summary Some Javadoc. This method returns some javadoc.}
         */
        boolean emulated() {return false;} // ok

        /**
         * {@summary Some Javadoc. This method returns some javadoc. Some Javadoc.}
         */
        boolean emulated1() {return false;} // ok

        /**
         * {@summary This is valid.}
         * @return Some Javadoc the customer ID. // ok
         */
        int geId() {return 666;} // ok

        /**
         * {@summary This is valid.}
         * @return Sentence one. Sentence two. // ok
         */
        String twoSentences() {return "Sentence one. Sentence two.";} // ok

        /** {@summary Stop instances being created.} **/ // ok
        String twoSentences1() {return "Sentence one. Sentence two.";} // ok
    }; // ok
}
