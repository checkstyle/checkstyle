/*
JavadocParagraph
violateExecutionOnNonTightHtml = (default)false
allowNewlineParagraph = false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

/**
 * Some Javadoc.
 * <p> // violation 'Javadoc content should start from the next line after /\*\*.'
 * /^ WARN/   Some Javadoc.<p> // violation 'Javadoc content should start from the next line after /\*\*.'
 *
 */
class InputJavadocParagraphIncorrect2 {

    /**
     * Some Javadoc.<P>  // violation 'Javadoc content should start from the next line after /\*\*.'
     *
     * <p>  Some Javadoc.
     *
     * @since 8.0
     */
    public static final byte NUL = 0;

    /**
     * Some <p>Javadoc. // violation 'Javadoc content should start from the next line after /\*\*.'
     *
     * <p>    Some Javadoc.
     *
     * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
     *     Documentation about GWT emulated source</a>
     */
    boolean emulated() {return false;}

    /**<p>Some Javadoc.<p>  // 2 violations
     * <p>  // violation 'Javadoc content should start from the next line after /\*\*.'
     * <p><p>  // violation 'Javadoc content should start from the next line after /\*\*.'
     * <p>/^WARN/   Some Javadoc.<p>*/  // violation 'Javadoc content should start from the next line after /\*\*.'
     class InnerInputJavadocParagraphIncorrect {

        /**
         * Some Javadoc./WARN/<p>  // violation 'Javadoc content should start from the next line after /\*\*.'
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**<p>  // violation 'Javadoc content should start from the next line after /\*\*.'
         * /^WARN/ Some Javadoc.
         *
         * <P>
         * /^WARN/
         * <p> // violation
         *  /^WARN/ Some Javadoc.<p> // violation 'Javadoc content should start from the next line after /\*\*.'
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about GWT emulated source</a>
         */
        boolean emulated() {return false;}
    }

    InnerInputJavadocParagraphIncorrect anon = new InnerInputJavadocParagraphIncorrect() {

            /**
         * <p>Some Javadoc. // violation 'Javadoc content should start from the next line after /\*\*.'
         *
         * Some Javadoc. // violation above 'Javadoc content should start from the next line after /\*\*.'
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**
         * /WARN/  Some Javadoc.<p> // violation 'Javadoc content should start from the next line after /\*\*.'
         *
         *  <p>  Some Javadoc.
         *
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about <p> GWT emulated source</a> // violation 'Javadoc content should start from the next
         *     line after /\*\*.'
         */
        boolean emulated() {return false;}

        /**
         * Double newline. // violation below 'Javadoc content should start from the next line after /\*\*.'
         *
         *
         * Some Javadoc. //DOUBLE WARN AT TWO PREVIOUS LINES // violation above 'Javadoc content should start from the
         * next line after /\*\*.'
         */
         void doubleNewline() {}
    };
}
