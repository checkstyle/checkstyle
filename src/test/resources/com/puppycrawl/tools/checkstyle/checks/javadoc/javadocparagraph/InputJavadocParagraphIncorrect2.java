/*
JavadocParagraph
violateExecutionOnNonTightHtml = (default)false
allowNewlineParagraph = false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

/**
 * Some Javadoc.
 * <p> // violation '\Q Javadoc content should start from the next line after /\*\*.\E'
 * /^ WARN/   Some Javadoc.<p> // violation '\Q Javadoc content should start from the next line
 * after /\*\*.\E'
 *
 */
class InputJavadocParagraphIncorrect2 {

    /**
     * Some Javadoc.<P>  // violation '\Q Javadoc content should start from the next line after
     * /\*\*.\E'
     *
     * <p>  Some Javadoc.
     *
     * @since 8.0
     */
    public static final byte NUL = 0;

    /**
     * Some <p>Javadoc. // violation 'Javadoc content should start from the next line after
     * /\*\*.'
     *
     * <p>    Some Javadoc.
     *
     * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
     *     Documentation about GWT emulated source</a>
     */
    boolean emulated() {return false;}

    /**<p>Some Javadoc.<p>  // violation '\Q Javadoc content should start from the next line
     * after /\*\*.\E'
     * <p>  // violation '\Q Javadoc content should start from the next line
     * after /\*\*.\E'
     * <p><p>  // violation '\Q Javadoc content should start from the next line
     * after /\*\*.\E'
     * <p>/^WARN/   Some Javadoc.<p>*/  // violation
     class InnerInputJavadocParagraphIncorrect {

        /**
         * Some Javadoc./WARN/<p>  // violation '\Q Javadoc content should start from the next line
         * after /\*\*.\E'
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**<p>  // violation '\Q Javadoc content should start from the next line after /\*\*.\E'
         * /^WARN/ Some Javadoc.
         *
         * <P>
         * /^WARN/
         * <p> // violation '\Q Javadoc content should start from the next line
         * after /\*\*.\E'
         *  /^WARN/ Some Javadoc.<p> // violation '\\Q Javadoc content should start from the next
         *  line after /\*\*. \\E'
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about GWT emulated source</a>
         */
        boolean emulated() {return false;}
    }

    InnerInputJavadocParagraphIncorrect anon = new InnerInputJavadocParagraphIncorrect() {

            /**
         * <p>Some Javadoc. // violation '\Q Javadoc content should start from the next line
         * after /\*\*.\E'
         *
         * Some Javadoc. // violation above '\Q Javadoc content should start from the next line
         * after /\*\*.\E'
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**
         * /WARN/  Some Javadoc.<p> // violation '\Q Javadoc content should start from the next
         * line after /\*\*.\E'
         *
         *  <p>  Some Javadoc.
         *
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about <p> GWT emulated source</a> // violation '\Q Javadoc content
         *     should start from the next line after /\*\*.\E'
         */
        boolean emulated() {return false;}

        /**
         * Double newline. // violation below '\Q Javadoc content should start from the next line
         * after /\*\*.\E'
         *
         *
         * Some Javadoc. // violation above '\Q Javadoc content should start from the next line
         * after /\*\*.\E'
         */
         void doubleNewline() {}
    };
}
