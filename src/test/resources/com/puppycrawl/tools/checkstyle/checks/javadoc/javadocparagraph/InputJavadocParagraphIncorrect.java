/*
JavadocParagraph
violateExecutionOnNonTightHtml = (default)false
allowNewlineParagraph = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

/**
 * Some Javadoc. // 2 violations below 'Javadoc content should start from the next line after
 * /\*\*.'
 * <p>
 * /^ WARN/   Some Javadoc.<p> // 2 violations 'Javadoc content should start from the next line
 * after /\*\*.'
 *
 */
class InputJavadocParagraphIncorrect {

    /**
     * Some Javadoc.<P> // 2 violations 'Javadoc content should start from the next line after
     * /\*\*.'
     *
     * <p>  Some Javadoc. // violation 'Javadoc content should start from the next line after
     * /\*\*.'
     *
     * @since 8.0
     */
    public static final byte NUL = 0;

    /**
     * Some <p>Javadoc. // violation 'Javadoc content should start from the next line after
     * /\*\*.'
     *
     * <p>    Some Javadoc. // violation 'Javadoc content should start from the next line after
     * /\*\*.'
     *
     * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
     *     Documentation about GWT emulated source</a>
     */
    boolean emulated() {return false;}

    /**<p>Some Javadoc.<p>  // 3 violations 'Javadoc content should start from the next line
     * after /\*\*.'
     * <p>  // 2 violations 'Javadoc content should start from the next line after /\*\*.'
     * <p><p>  // 2 violations 'Javadoc content should start from the next line after /\*\*.'
     * <p>/^WARN/   Some Javadoc.<p>*/  // 2 violations
     class InnerInputJavadocParagraphIncorrect {

        /**
         * Some Javadoc./WARN/<p>  // 2 violations 'Javadoc content should start from the next
         * line after /\*\*.'
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**<p>
         * /^WARN/ Some Javadoc. // 2 violations above 'Javadoc content should start from the next
         * line after /\*\*.'
         *
         * <P> // violation 'Javadoc content should start from the next line after /\*\*.'
         * /^WARN/
         * <p> // 2 violations 'Javadoc content should start from the next line after /\*\*.'
         *  /^WARN/ Some Javadoc.<p> // 2 violations 'Javadoc content should start from the next
         *  line after /\*\*.'
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about GWT emulated source</a>
         */
        boolean emulated() {return false;}
    }

    InnerInputJavadocParagraphIncorrect anon = new InnerInputJavadocParagraphIncorrect() {

            /**
         * <p>Some Javadoc. // violation 'Javadoc content should start from the next line after
         * /\*\*.'
         *
         * Some Javadoc. // violation above 'Javadoc content should start from the next line after
         * /\*\*.'
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**
         * /WARN/  Some Javadoc.<p> // 2 violations 'Javadoc content should start from the next
         * line after /\*\*.'
         *
         *  <p>  Some Javadoc. // violation 'Javadoc content should start from the next line
         *  after /\*\*.'
         *
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about <p> GWT emulated source</a> // 2 violations 'Javadoc content
         *     should start from the next line after /\*\*.'
         */
        boolean emulated() {return false;}

        /**
         * Double newline. // violation below 'Javadoc content should start from the next line
         * after /\*\*.'
         *
         *
         * Some Javadoc. //DOUBLE WARN AT TWO PREVIOUS LINES // violation above
         */
         void doubleNewline() {}
    };
}
