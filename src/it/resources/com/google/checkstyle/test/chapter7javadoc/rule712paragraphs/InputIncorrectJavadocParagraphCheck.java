package com.google.checkstyle.test.chapter7javadoc.rule712paragraphs;

/**
 * Some Javadoc.
 * <p> //warn
 * /^ WARN/   Some Javadoc.<p> //warn
 *
 */
class InputCorrectJavaDocParagraphCheck1 {

    /**
     * Some Javadoc.<P>  //warn
     *
     * <p>  Some Javadoc. //warn
     *
     * @since 8.0
     */
    public static final byte NUL = 0;
       
    /**
     * Some <p>Javadoc.//warn
     *
     * <p>Some Javadoc.
     *
     * @see <a href="http://code.google.com/webtoolkit/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
     *     Documentation about GWT emulated source</a>
     */
    boolean emulated() {return false;}
    
    /**<p>Some Javadoc. //warn
     * <p> //warn
     * <p><p> //warn
     * <p>/^WARN/   Some Javadoc.<p>*/ //warn
     class InnerInputCorrectJavaDocParagraphCheck {

        /**
         * Some Javadoc.<p> //warn
         *
         * @since 8.0
         */
        public static final byte NUL = 0;
           
        /**<p> //warn
         * /^WARN/ Some Javadoc.
         *
         * <P> //warn
         * /^WARN/
         * <p> //warn
         *  /^WARN/ Some Javadoc.<p> //warn
         * @see <a href="http://code.google.com/webtoolkit/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about GWT emulated source</a>
         */
        boolean emulated() {return false;}
    }

    InnerInputCorrectJavaDocParagraphCheck anon = new InnerInputCorrectJavaDocParagraphCheck() {

    	/**
         * <p>Some Javadoc. //warn
         *
         * <p>Some Javadoc.
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**
         * /WARN/  Some Javadoc.<p> //warn
         *
         *  <p>  Some Javadoc. //warn
         *
         * @see <a href="http://code.google.com/webtoolkit/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about <p> GWT emulated source</a> //warn
         */
        boolean emulated() {return false;}
    };
}
