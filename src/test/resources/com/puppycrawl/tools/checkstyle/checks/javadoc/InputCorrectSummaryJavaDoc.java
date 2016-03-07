package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks comment block indentations over surrounding code.
 * e.g.:
 * <p>
 * {@code
 * /* some comment *&#47; - this is ok
 * double d = 3.14;
 *     /* some comment *&#47; - this is <b>not</b> ok.
 * double d1 = 5.0;
 * }
 * </p>
 * @param blockComment {@link TokenTypes#BLOCK_COMMENT_BEGIN block comment begin}.
 */
class InputCorrectSummaryJavaDoc {

    /**
     * Some Javadoc This method returns.
     */
    public static final byte NUL = 0;
    
    /** Single line Javadoc with 2 tail astrisks. **/
    void foo1() {}
    /**
     * Multiple line Javadoc with 2 tail astrisks. *
     **/
    void foo2() {}
    /**
     * Multiple line Javadoc with 2 tail astrisks.
     **/
    void foo3() {}
    
    /**
     * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}.
     */
    void foo4() {}
    
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
     * <a href="mailto:vlad@htmlbook.ru"/>.
     */
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
         * Returns the customer ID. This method returns
         */
        int getId() {return 666;}
        
        /**
         * <a href="mailto:vlad@htmlbook.ru"/>.
         */
        void foo2() {}
        
        /**
         * As of JDK 1.1,
         * replaced by {@link #setBounds(int,int,int,int)}. This method returns.
         */
        void foo3() {}
        
        /** 
         * JAXB Provider Use Only: Provides partial default
         * implementations for some of the javax.xml.bind interfaces.
         */
        void foo5() {}

        /**
         * An especially short (int... A) bit of Javadoc. This
         * method returns
         */
        void foo6() {}
    }

     /**
      * Some
      * javadoc. A {@code Foo} is a simple Javadoc.
      * 
      * Some Javadoc. A {@code Foo}
      * is a simple Javadoc.
      */
    InnerInputCorrectJavaDocParagraphCheck anon = new InnerInputCorrectJavaDocParagraphCheck() {

        /**
         * JAXB 1.0 only default validation event handler.
         */
        public static final byte NUL = 0;

        /**
         * Returns the current state.
         * This method returns.
         */
        boolean emulated(String s) {return false;}
        
        /**
         * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}.
         */
        void foo3() {}
        
        /** An especially short bit of Javadoc. */
        void foo5() {}

        /**
         * An especially short bit of Javadoc.
         */
        void foo6() {}
        
        /**
         * Some Javadoc. This method returns some javadoc.
         */
        boolean emulated() {return false;}
        
        /**
         * Some Javadoc. This method returns some javadoc. Some Javadoc.
         */
        boolean emulated1() {return false;}
        
    };
}
