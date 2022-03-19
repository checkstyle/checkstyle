/*
JavadocParagraph
violateExecutionOnNonTightHtml = (default)false
allowNewlineParagraph = false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

/**
 * Some Javadoc.
 * <p> // violation '\Q tag should be placed immediately before the first word, with no space
 * after.\E'
 * /^ WARN/   Some Javadoc.<p> // violation '\Q Empty line should be followed by <p> tag on the
 * next line.\E'
 *
 */
class InputJavadocParagraphIncorrect2 {

    /**
     * Some Javadoc.<P>  // violation '\Q tag should be placed immediately before the first word,
     * with no space after.\E'
     *
     * <p>  Some Javadoc.
     *
     * @since 8.0
     */
    public static final byte NUL = 0;

    /**
     * Some <p>Javadoc. // violation '\Q Empty line should be followed by <p> tag on the
     * \ * next line.\E'
     *
     * <p>    Some Javadoc.
     *
     * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
     *     Documentation about GWT emulated source</a>
     */
    boolean emulated() {return false;}

    /**<p>Some Javadoc.<p>  // 2 violations
     * <p>  // violation '\Q Empty line should be followed by <p> tag on the next line.\E'
     * <p><p>  // violation '\Q Empty line should be followed by <p> tag on the next line.\E'
     * <p>/^WARN/   Some Javadoc.<p>*/  // violation

    class InnerInputJavadocParagraphIncorrect {

        /**
         * Some Javadoc./WARN/<p>  // violation '\Q tag should be placed immediately before the
         * first word, with no space after.\E'
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**<p>  // violation '\Q Empty line should be followed by <p> tag on the next line.\E'
         * /^WARN/ Some Javadoc.
         *
         * <P>
         * /^WARN/
         * <p> // violation '\Q Empty line should be followed by <p> tag on the next line.\E'
         *  /^WARN/ Some Javadoc.<p> // violation '\Q tag should be placed immediately before the
         *  first word, with no space after.\E'
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about GWT emulated source</a>
         */
        boolean emulated() {return false;}
    }

    InnerInputJavadocParagraphIncorrect anon = new InnerInputJavadocParagraphIncorrect() {

        /**
         * <p>Some Javadoc. // violation '\Q Empty line should be followed by <p> tag on the
         * next line.\E'
         *
         * Some Javadoc. // violation above
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**
         * /WARN/  Some Javadoc.<p> // violation '\Q tag should be placed immediately before
         * the first word, with no space after.\E'
         *
         *  <p>  Some Javadoc.
         *
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about <p> GWT emulated source</a> // violation '\Q tag should be
         * placed immediately before the first word, with no space after.\E'
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
