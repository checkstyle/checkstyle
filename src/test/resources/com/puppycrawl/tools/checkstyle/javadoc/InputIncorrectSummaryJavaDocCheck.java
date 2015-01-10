package com.puppycrawl.tools.checkstyle.javadoc;

/**
 * A {@code Foo. Foo}
 * is a simple Javadoc. Some javadoc.
 */
class InputInCorrectSummaryJavaDocCheck {
    
    /**
     * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}
     */
    void foo3() {}
    
    /**
     * @throws Exception if an error occurs
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

    /** 
     * <a href="mailto:vlad@htmlbook.ru"/> 
     */
     class InnerInputCorrectJavaDocParagraphCheck {

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
        
        /**
         * <a href="mailto:vlad@htmlbook.ru"/>
         */
        void foo2() {}

        /**
         * @return the
         * customer ID some javadoc.
         */
        int geId() {return 666;} 

        /**
         * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}
         */
        void foo3() {}
        
        /**
         * @throws Exception if an error occurs
         */
        void foo4() throws Exception {}
        
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

        /**
         * Some Javadoc.
         */
        public static final byte NUL = 0;

        /**
         * Some Javadoc.
         */
        void emulated(String s) {}
        
        /**
         * As of JDK 1.1, replaced by {@link #setBounds(int,int,int,int)}
         */
        void foo3() {}
        
        /**
         * @throws Exception if an error occurs
         */
        void foo4() throws Exception {}
        
        /** An especially short bit of Javadoc. */
        void foo5() {}

        /**
         * An especially short bit of Javadoc.
         */
        void foo6() {}
    };
}
