/*
JavadocParagraph
violateExecutionOnNonTightHtml = (default)false
allowNewlineParagraph = false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

/**
 * Some Javadoc.
 * <p> // violation '<p> tag should be preceded with an empty line.'
 * /^ WARN/   Some Javadoc.<p> // violation '<p> tag should be preceded with an empty line.'
 *
 */
class InputJavadocParagraphIncorrect2 {

    /**
     * Some Javadoc.<P>  // violation '<p> tag should be preceded with an empty line.'
     *
     * <p>  Some Javadoc.
     *
     * @since 8.0
     */
    public static final byte NUL = 0;

    /**
     * Some <p>Javadoc. // violation '<p> tag should be preceded with an empty line.'
     *
     * <p>    Some Javadoc.
     *
     * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
     *     Documentation about GWT emulated source</a>
     */
    boolean emulated() {return false;}

    /**<p>Some Javadoc.<p>  // 2 violations
     * <p>  // violation  '<p> tag should be preceded with an empty line.'
     * <p><p>  // violation '<p> tag should be preceded with an empty line.'
     * <p>/^WARN/   Some Javadoc.<p>*/  // violation '<p> tag should be preceded with an empty line.'
     class InnerInputJavadocParagraphIncorrect {

        /**
         * Some Javadoc./WARN/<p>  // violation '<p> tag should be preceded with an empty line.'
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**
         * /^WARN/ Some Javadoc.
         *
         * <P>
         * /^WARN/
         * <p> // violation '<p> tag should be preceded with an empty line.'
         *  /^WARN/ Some Javadoc.<p> // violation '<p> tag should be preceded with an empty line.'
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about GWT emulated source</a>
         */
        boolean emulated() {return false;}
    }

    InnerInputJavadocParagraphIncorrect anon = new InnerInputJavadocParagraphIncorrect() {

            /**
         * Some Javadoc.
         *
         * Some Javadoc. // violation above 'Empty line should be followed by <p> tag on the next line.'
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**
         * /WARN/  Some Javadoc.<p> // violation '<p> tag should be preceded with an empty line.'
         *
         *  <p>  Some Javadoc.
         *
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about <p> GWT emulated source</a> // violation '<p> tag should be preceded with an empty line.'
         */
        boolean emulated() {return false;}

        /**
         *     Documentation about <p> GWT emulated source</a> // violation 'Javadoc comment at column 60 has parse error. Details: no viable alternative at input '</a' while parsing HTML_ELEMENT'
         *
         *
         * Some Javadoc. // violation above //DOUBLE WARN AT TWO PREVIOUS LINES 'Empty line should be followed by <p> tag on the next line.'
         */
         void doubleNewline() {}
    };
}
