package com.puppycrawl.tools.checkstyle.javadoc;

/**
 * Some Javadoc.
 *
 * <p>Some Javadoc.
 *
 */
class InputCorrectJavaDocParagraphCheck {

    /**
     * Some Javadoc.
     *   
     * <p>{@code function} will never be invoked with a null value.
     *
     * @since 8.0
     */
    public static final byte NUL = 0;
       
    /**
     * Some Javadoc.
     *
     * <p>Some Javadoc.
     * 
     * <pre>
     * class Foo {
     * 
     *   void foo() {}
     * }
     * </pre>
     *
     * @see <a href="http://code.google.com/webtoolkit/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
     *     Documentation about GWT emulated source</a>
     */
    boolean emulated() {return false;}
    
    /**
     * Some Javadoc.
     *
     * <p>Some Javadoc.
     *
     */
     class InnerInputCorrectJavaDocParagraphCheck {

        /**
         * Some Javadoc.
         *
         * <p>Some Javadoc.
         *
         * <p>Some Javadoc.
         *
         * @since 8.0
         */
        public static final byte NUL = 0;
           
        /**
         * Some Javadoc.
         *
         * <p>Some Javadoc.
         *
         * @see <a href="http://code.google.com/webtoolkit/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about GWT emulated source</a>
         */
        boolean emulated() {return false;}
    }
    
    InnerInputCorrectJavaDocParagraphCheck anon = new InnerInputCorrectJavaDocParagraphCheck() {

    	/**
         * Some Javadoc.
         *
         * <p>Some Javadoc.
         *
         * <p>Some Javadoc.
         *
         * @since 8.0
         */
        public static final byte NUL = 0;
           
        /**
         * Some Javadoc.
         *
         * <p>Some Javadoc.
         *
         * <p>Some Javadoc.
         *
         * @see <a href="http://code.google.com/webtoolkit/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about GWT emulated source</a>
         */
        boolean emulated() {return false;}
    };
}
