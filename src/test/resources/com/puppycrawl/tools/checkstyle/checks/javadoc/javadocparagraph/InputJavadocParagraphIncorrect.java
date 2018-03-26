package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;



/**
 * Some Javadoc.
 * <p>
 * /^ WARN/   Some Javadoc.<p> //WARN
 *
 */
class InputJavadocParagraphIncorrect {

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
     * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
     *     Documentation about GWT emulated source</a>
     */
    boolean emulated() {return false;}
    
    /**<p>Some Javadoc.<p>  //WARN
     * <p>  //WARN
     * <p><p>  //WARN
     * <p>/^WARN/   Some Javadoc.<p>*/  //WARN
     class InnerInputJavadocParagraphIncorrect {

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
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about GWT emulated source</a>
         */
        boolean emulated() {return false;}
    }

    InnerInputJavadocParagraphIncorrect anon = new InnerInputJavadocParagraphIncorrect() {

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
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about <p> GWT emulated source</a> //WARN
         */
        boolean emulated() {return false;}

        /**
         * Double newline.
         *
         *
         * Some Javadoc. //DOUBLE WARN AT TWO PREVIOUS LINES
         */
         void doubleNewline() {}
    };
}
