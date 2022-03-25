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
     * <p>  // violation '<p> tag should be preceded with an empty line.'
     * <p><p>  // violation '<p> tag should be preceded with an empty line.'
     * <p>/^WARN/   Some Javadoc.<p>*/
    // violation above '<p> tag should be preceded with an empty line.'
     class InnerInputJavadocParagraphIncorrect {

        /**
         * Some Javadoc./WARN/<p>  // violation '<p> tag should be preceded with an empty line.'
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        // violation 2 lines below 'Redundant <p> tag.'
        /**
         * <p>
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

        // violation 2 lines below 'Redundant <p> tag.'
            /**
         * <p>Some Javadoc.
         *
         * Some Javadoc.
         *
         * @since 8.0
         */
        // violation 5 lines above 'Empty line should be followed by <p> tag on the next line.'
        public static final byte NUL = 0;

        /**
         * /WARN/  Some Javadoc.<p> // violation '<p> tag should be preceded with an empty line.'
         *
         *  <p>  Some Javadoc.
         *
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about <p> GWT emulated source</a>
         */
        // violation 2 lines above '<p> tag should be preceded with an empty line.'
        boolean emulated() {return false;}

        // violation 3 lines below 'Empty line should be followed by <p> tag on the next line.'
        /**
         * Double newline.
         *
         *
         * Some Javadoc. //DOUBLE WARN AT TWO PREVIOUS LINES
         */
        // violation 3 lines above 'Empty line should be followed by <p> tag on the next line.'
         void doubleNewline() {}
    };
}
