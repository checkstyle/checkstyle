package com.google.checkstyle.test.chapter7javadoc.rule712paragraphs;

/**
 * Some Javadoc.
 * <p>
 * // 2 violations above:
 * //                    '.* tag should be placed immediately before the first word'
 * //                    '.* tag should be preceded with an empty line.'
 * /^ WARN/   Some Javadoc.<p>
 * // 2 violations above:
 * //                    '.* tag should be placed immediately before the first word'
 * //                    '.* tag should be preceded with an empty line.'
 */
class InputIncorrectJavadocParagraphCheck {

    /**
     * Some Javadoc.<P>
     * // 2 violations above:
     * //                    '.* tag should be placed immediately before the first word'
     * //                    '.* tag should be preceded with an empty line.'
     * <p>  Some Javadoc.
     * // 2 violations above:
     * //                    '.* tag should be placed immediately before the first word'
     * //                    '.* tag should be preceded with an empty line.'
     *
     * @since 8.0
     */
    public static final byte NUL = 0;

    /**
     * Some <p>Javadoc. // violation '.* tag should be preceded with an empty line.'
     *
     * <p>Some Javadoc.
     *
     * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
     *     Documentation about GWT emulated source</a>
     */
    boolean emulated() {return false;}

    /**<p>Some Javadoc. // violation 'Redundant .* tag.'
     * <p>
     * // 2 violations above:
     * //                    '.* tag should be placed immediately before the first word'
     * //                    '.* tag should be preceded with an empty line.'
     * <p><p>
     * // 2 violations above:
     * //                    '.* tag should be placed immediately before the first word'
     * //                    '.* tag should be preceded with an empty line.'
     * <p>/^WARN/   Some Javadoc.<p>
     * // 2 violations above:
     *        //                    '.* tag should be placed immediately before the first word'
     *        //                    '.* tag should be preceded with an empty line.'
     */
     class InnerInputCorrectJavaDocParagraphCheck {

        /**
         * Some Javadoc.<p>
         * // 2 violations above:
         * //                    '.* tag should be placed immediately before the first word'
         * //                    '.* tag should be preceded with an empty line.'
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**<p>
         * // 2 violations above:
         * //                    '.* tag should be placed immediately before the first word'
         * //                    'Redundant .* tag.'
         * /^WARN/ Some Javadoc.
         *
         * <P> // violation '.* tag should be placed immediately before the first word'
         * /^WARN/
         * <p>
         * // 2 violations above:
         * //                    '.* tag should be placed immediately before the first word'
         * //                    '.* tag should be preceded with an empty line.'
         *  /^WARN/ Some Javadoc.<p>
         * // 2 violations above:
         * //                    '.* tag should be placed immediately before the first word'
         * //                    '.* tag should be preceded with an empty line.'
         *
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about GWT emulated source</a>
         */
        boolean emulated() {return false;}
    }

    InnerInputCorrectJavaDocParagraphCheck anon = new InnerInputCorrectJavaDocParagraphCheck() {

        /**
         * <p>Some Javadoc. // violation 'Redundant .* tag.'
         *
         * <p>Some Javadoc.
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**
         * /WARN/  Some Javadoc.<p>
         * // 2 violations above:
         * //                    '.* tag should be placed immediately before the first word'
         * //                    '.* tag should be preceded with an empty line.'
         *
         *  <p>  Some Javadoc.
         *  // violation above '.* tag should be placed immediately before the first word'
         *
         * @see <a href="http://www.gwtproject.org/doc/latest/DevGuideOrganizingProjects.html#DevGuideModules">
         *     Documentation about <p> GWT emulated source</a>
         * // 2 violations above:
         * //                    '.* tag should be placed immediately before the first word'
         * //                    '.* tag should be preceded with an empty line.'
         */
        boolean emulated() {return false;}
    };
}
