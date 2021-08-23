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
     * <p>  Some Javadoc. // violation
     *
     * @since 8.0
     */
    public static final byte NUL = 0;

    /**
     * Some <p>Javadoc. // violation
     *
     * <p>    Some Javadoc. // violation
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
         * <P> // violation
         * /^WARN/
         * <p> // 2 violations
         *  /^WARN/ Some Javadoc.<p> // 2 violations
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about GWT emulated source</a>
         */
        boolean emulated() {return false;}
    }

    InnerInputJavadocParagraphIncorrect anon = new InnerInputJavadocParagraphIncorrect() {

            /**
         * <p>Some Javadoc. // violation
         *
         * Some Javadoc. // violation above
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**
         * /WARN/  Some Javadoc.<p> // 2 violations
         *
         *  <p>  Some Javadoc. // violation
         *
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about <p> GWT emulated source</a> // 2 violations
         */
        boolean emulated() {return false;}

        /**
         * Double newline. // violation below
         *
         *
         * Some Javadoc. //DOUBLE WARN AT TWO PREVIOUS LINES // violation above
         */
         void doubleNewline() {}
    };
}
