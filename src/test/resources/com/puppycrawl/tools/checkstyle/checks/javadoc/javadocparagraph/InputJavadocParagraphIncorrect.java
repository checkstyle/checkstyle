/*
JavadocParagraph
violateExecutionOnNonTightHtml = (default)false
allowNewlineParagraph = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

// violation 3 lines below 'tag should be preceded with an empty line.'
/**
 * Some Javadoc.
 * <p>
 * /^ WARN/   Some Javadoc.<p>
 *
 */
// violation 3 lines above 'tag should be preceded with an empty line.'
class InputJavadocParagraphIncorrect {

    // violation 2 lines below 'tag should be preceded with an empty line.'
    /**
     * Some Javadoc.<P>
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

    // 2 violations 4 lines below:
    //  'tag should be preceded with an empty line.'
    //  'Redundant <p> tag.'
    // violation 2 lines below 'tag should be preceded with an empty line.'
    /**<p>Some Javadoc.<p>
     * <p>
     * <p><p>
     * <p>/^WARN/   Some Javadoc.<p>*/
    // violation 2 lines above 'tag should be preceded with an empty line.'
    // 2 violations 2 lines above:
    //  '<p> tag should be placed immediately before the first word'
    //  'tag should be preceded with an empty line.'
     class InnerInputJavadocParagraphIncorrect {

        // violation 2 lines below 'tag should be preceded with an empty line.'
        /**
         * Some Javadoc./WARN/<p>
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        // violation below 'Redundant \<p\> tag.'
        /**<p>
         * /^WARN/ Some Javadoc.
         *
         * <P> // violation 'tag should be placed immediately before the first word'
         * /^WARN/
         * <p>
         *  /^WARN/ Some Javadoc.<p>
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about GWT emulated source</a>
         */
        // violation 5 lines above 'tag should be preceded with an empty line.'
        // violation 5 lines above 'tag should be preceded with an empty line.'
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

        // violation 2 lines below 'tag should be preceded with an empty line.'
        /**
         * /WARN/  Some Javadoc.<p>
         *
         *  <p>  Some Javadoc. // violation 'tag should be placed immediately before the first word'
         *
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about <p> GWT emulated source</a>
         */
        // 2 violations 2 lines above:
        //  'tag should be placed immediately before the first word'
        //  'tag should be preceded with an empty line.'
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
