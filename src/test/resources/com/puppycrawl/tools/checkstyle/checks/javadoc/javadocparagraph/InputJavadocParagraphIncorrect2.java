/*
JavadocParagraph
violateExecutionOnNonTightHtml = (default)false
allowNewlineParagraph = false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

/**
 * Some Javadoc.
 * <p> // violation 'tag should be placed immediately before the first word, with no space
 * after.'
 * /^ WARN/   Some Javadoc.<p> // violation 'Empty line should be followed by <p> tag on the
 * next line.'
 *
 */
class InputJavadocParagraphIncorrect2 {

    /**
     * Some Javadoc.<P>  // violation 'tag should be placed immediately before the first word,
     * with no space after.'
     *
     * <p>  Some Javadoc.
     *
     * @since 8.0
     */
    public static final byte NUL = 0;

    /**
     * Some <p>Javadoc. // violation 'Empty line should be followed by <p> tag on the
     * \ * next line.'
     *
     * <p>    Some Javadoc.
     *
     * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
     *     Documentation about GWT emulated source</a>
     */
    boolean emulated() {return false;}

    /**<p>Some Javadoc.<p>  // 2 violations
     * <p>  // violation 'Empty line should be followed by <p> tag on the next line.'
     * <p><p>  // violation 'Empty line should be followed by <p> tag on the next line.'
     * <p>/^WARN/   Some Javadoc.<p>*/  // violation

    class InnerInputJavadocParagraphIncorrect {

        /**
         * Some Javadoc./WARN/<p>  // violation 'tag should be placed immediately before the
         * first word, with no space after.'
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**<p>  // violation 'Empty line should be followed by <p> tag on the next line.'
         * /^WARN/ Some Javadoc.
         *
         * <P>
         * /^WARN/
         * <p> // violation 'Empty line should be followed by <p> tag on the next line.'
         *  /^WARN/ Some Javadoc.<p> // violation 'tag should be placed immediately before the
         *  first word, with no space after.'
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about GWT emulated source</a>
         */
        boolean emulated() {return false;}
    }

    InnerInputJavadocParagraphIncorrect anon = new InnerInputJavadocParagraphIncorrect() {

        /**
         * <p>Some Javadoc. // violation 'Empty line should be followed by <p> tag on the
         * next line.'
         *
         * Some Javadoc. // violation above
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**
         * /WARN/  Some Javadoc.<p> // violation 'tag should be placed immediately before
         * the first word, with no space after.'
         *
         *  <p>  Some Javadoc.
         *
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about <p> GWT emulated source</a> // violation 'tag should be
         * placed immediately before the first word, with no space after.'
         */
        boolean emulated() {return false;}

        /**
         * Double newline. // violation below
         *
         *
         * Some Javadoc. // violation above //DOUBLE WARN AT TWO PREVIOUS LINES
         */
        void doubleNewline() {}
    };
}
