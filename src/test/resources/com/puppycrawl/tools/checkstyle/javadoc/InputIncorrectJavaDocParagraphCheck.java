package com.puppycrawl.tools.checkstyle.javadoc;

import InputCorrectJavaDocParagraphCheck.InnerInputCorrectJavaDocParagraphCheck;

/**
 * Some Javadoc.
 * <p>
 * /^ WARN/   Some Javadoc.<p>
 *
 */
class InputCorrectJavaDocParagraphCheck {

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
    
    /**<p>Some Javadoc.<p>
     * <p>
     * <p><p>
     * <p>/^WARN/   Some Javadoc.<p>*/
     class InnerInputCorrectJavaDocParagraphCheck {

        /**
         * Some Javadoc./WARN/<p>
         *
         * @since 8.0
         */
        public static final byte NUL = 0;
           
        /**<p>
         * /^WARN/ Some Javadoc.
         *
         * <P>
         * /^WARN/
         * <p>
         *  /^WARN/ Some Javadoc.<p>
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
         * /WARN/  Some Javadoc.<p>
         *
         *  <p>  Some Javadoc. //WARN
         *
         * @see <a href="http://code.google.com/webtoolkit/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about <p> GWT emulated source</a>
         */
        boolean emulated() {return false;}
    };
}
