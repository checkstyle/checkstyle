package com.puppycrawl.tools.checkstyle.javadoc;



/**
 * Some Javadoc.
 * <p>
 * /^ WARN/   Some Javadoc.<p> //WARN
 *
 */
class InputInCorrectJavaDocParagraphCheck {

    /**
     * Some Javadoc.<P>  //WARN
     *
     * <p>  Some Javadoc. //WARN
     *
     * @since 8.0
     */
    public static final byte NUL = 0;
       
    /**
     * Some <p>Javadoc.//WARN
     *
     * <p>    Some Javadoc.//WARN
     *
     * @see <a href="http://code.google.com/webtoolkit/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
     *     Documentation about GWT emulated source</a>
     */
    boolean emulated() {return false;}
    
    /**<p>Some Javadoc.<p>  //WARN
     * <p>  //WARN
     * <p><p>  //WARN
     * <p>/^WARN/   Some Javadoc.<p>*/  //WARN
     class InnerInputCorrectJavaDocParagraphCheck {

        /**
         * Some Javadoc./WARN/<p>  //WARN
         *
         * @since 8.0
         */
        public static final byte NUL = 0;
           
        /**<p>  //WARN
         * /^WARN/ Some Javadoc.
         *
         * <P> //WARN
         * /^WARN/
         * <p> //WARN
         *  /^WARN/ Some Javadoc.<p> //WARN
         * @see <a href="http://code.google.com/webtoolkit/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about GWT emulated source</a>
         */
        boolean emulated() {return false;}
    }

    InnerInputCorrectJavaDocParagraphCheck anon = new InnerInputCorrectJavaDocParagraphCheck() {

    	/**
         * <p>Some Javadoc. //WARN
         *
         * Some Javadoc.
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**
         * /WARN/  Some Javadoc.<p> //WARN
         *
         *  <p>  Some Javadoc. //WARN
         *
         * @see <a href="http://code.google.com/webtoolkit/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about <p> GWT emulated source</a> //WARN
         */
        boolean emulated() {return false;}
    };
}
