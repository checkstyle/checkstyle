/*
JavadocParagraph
violateExecutionOnNonTightHtml = (default)false
allowNewlineParagraph = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

/**
 * Some Javadoc. // 2 violations below
 * <p>
 * /^ WARN/   Some Javadoc.<p> // 2 violations
 *
 */
class InputJavadocParagraphIncorrect {

    /**
     * Some Javadoc.<P> // 2 violations
     *
     * <p>  Some Javadoc. // violation 'tag should be placed immediately before the first word'
     *
     * @since 8.0
     */
    public static final byte NUL = 0;

    // violation 2 lines below '<p> tag should be preceded with an empty line.'
    /**
     * Some <p>Javadoc.
     *
     * <p>    Some Javadoc. // violation 'tag should be placed immediately before the first word'
     *
     * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
     *     Documentation about GWT emulated source</a>
     */
    boolean emulated() {return false;}

    /**<p>Some Javadoc.<p>  // 3 violations
     * <p>  // 2 violations
     * <p><p>  // 2 violations
     * <p>/^WARN/   Some Javadoc.<p>*/  // 2 violations
     class InnerInputJavadocParagraphIncorrect {

        /**
         * Some Javadoc./WARN/<p>  // 2 violations
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**<p>
         * /^WARN/ Some Javadoc. // 2 violations above
         *
         * <P> // violation 'tag should be placed immediately before the first word'
         * /^WARN/
         * <p> // 2 violations
         *  /^WARN/ Some Javadoc.<p> // 2 violations
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
         * /WARN/  Some Javadoc.<p> // 2 violations
         *
         *  <p>  Some Javadoc. // violation 'tag should be placed immediately before the first word'
         *
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about <p> GWT emulated source</a> // 2 violations
         */
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
