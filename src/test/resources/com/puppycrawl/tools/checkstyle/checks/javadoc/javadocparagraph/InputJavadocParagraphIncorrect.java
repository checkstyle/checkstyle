/*
JavadocParagraph
violateExecutionOnNonTightHtml = (default)false
allowNewlineParagraph = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

/**
 * Some Javadoc. // violation '\Q Javadoc content should start from the next line
 * after /\*\*.\E'
 * <p>
 * /^ WARN/   Some Javadoc.<p> // violation '\Q Javadoc content should start from the next line
 * after /\*\*.\E'
 *
 */
class InputJavadocParagraphIncorrect {

    /**
     * Some Javadoc.<P> // violation '\Q Javadoc content should start from the next line
     * after /\*\*.\E'
     *
     * <p>  Some Javadoc. // violation '\Q Javadoc content should start from the next line
     * after /\*\*.\E'
     *
     * @since 8.0
     */
    public static final byte NUL = 0;

    /**
     * Some <p>Javadoc. // violation '\Q Javadoc content should start from the next line
     * after /\*\*.\E'
     *
     * <p> Some Javadoc. // violation '\Q Javadoc content should start from the next line
    * after /\*\*.\E'
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
         * Some Javadoc./WARN/<p>  // violation 'Javadoc content should start from the next
         * line after /\*\*.'
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**<p>
         * /^WARN/ Some Javadoc. // violation above '\Q Javadoc content should start from the next
         * line after /\*\*.\E'
         *
         * <P> // violation '\Q Javadoc content should start from the next line
         * after /\*\*.\E'
         * /^WARN/
         * <p> // violation '\Q Javadoc content should start from the next line
         * after /\*\*.\E'
         *  /^WARN/ Some Javadoc.<p> // violation '\Q Javadoc content should start from the next
         *  line after /\*\*.\E'
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
         * * after /\*\*.\E'
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**
         * /WARN/  Some Javadoc.<p> // violation '\Q Javadoc content should start from the next line
         * after /\*\*.\E'
         *
         *  <p>  Some Javadoc. // violation '\Q Javadoc content should start from the next line
         * after /\*\*.\E'
         *
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about <p> GWT emulated source</a> // 2 violations 'Javadoc content
         *     should start from the next line after /\*\*.'
         */
        boolean emulated() {return false;}

        /**
         * Double newline. // violation below '\Q Javadoc content should start from the next line
         * after /\*\*.\E'
         *
         *
         * Some Javadoc. //violation above '\Q Javadoc content should start from the next line
         * after /\*\*.\E'
         */
         void doubleNewline() {}
    };
}
