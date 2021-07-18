/*
JavadocParagraph
violateExecutionOnNonTightHtml = (default)false
allowNewlineParagraph = false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

/**
 * Some Javadoc.
 * <p>
 * /^ WARN/   Some Javadoc.<p> // violation
 *
 */
class InputJavadocParagraphIncorrect2 {

    /**
     * Some Javadoc.<P>  // violation
     *
     * <p>  Some Javadoc.
     *
     * @since 8.0
     */
    public static final byte NUL = 0;

    /**
     * Some <p>Javadoc. // violation
     *
     * <p>    Some Javadoc.
     *
     * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
     *     Documentation about GWT emulated source</a>
     */
    boolean emulated() {return false;}

    /**<p>Some Javadoc.<p>  // violation
     * <p>  // violation
     * <p><p>  // violation
     * <p>/^WARN/   Some Javadoc.<p>*/  // violation
     class InnerInputJavadocParagraphIncorrect {

        /**
         * Some Javadoc./WARN/<p>  // violation
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**<p>  // violation
         * /^WARN/ Some Javadoc.
         *
         * <P>
         * /^WARN/
         * <p> // violation
         *  /^WARN/ Some Javadoc.<p> // violation
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about GWT emulated source</a>
         */
        boolean emulated() {return false;}
    }

    InnerInputJavadocParagraphIncorrect anon = new InnerInputJavadocParagraphIncorrect() {

            /**
         * <p>Some Javadoc. // violation
         *
         * Some Javadoc.
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**
         * /WARN/  Some Javadoc.<p> // violation
         *
         *  <p>  Some Javadoc.
         *
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about <p> GWT emulated source</a> // violation
         */
        boolean emulated() {return false;}

        /**
         * Double newline.
         *
         *
         * Some Javadoc. //DOUBLE WARN AT TWO PREVIOUS LINES
         */
         void doubleNewline() {}
    };
}
