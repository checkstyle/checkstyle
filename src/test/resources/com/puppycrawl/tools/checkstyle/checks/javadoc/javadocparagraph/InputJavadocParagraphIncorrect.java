/*
JavadocParagraph
allowNewlineParagraph = (default)true
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

// violation 4 lines below 'tag should be preceded with an empty line.'
// violation 4 lines below 'tag should be preceded with an empty line.'
/**
 * Some Summary.
 * <p>
 * Some paragraph.<p>
 *
 */
class InputJavadocParagraphIncorrect {
    // violation 3 lines below 'tag should be preceded with an empty line.'
    // violation 4 lines below 'tag should be placed immediately before the first word'
    /**
     * Some summary.<P>
     *
     * <p>  Some paragraph.
     *
     * @since 8.0
     */
    public static final byte NUL = 0;

    // violation 3 lines below '<p> tag should be preceded with an empty line.'
    // violation 4 lines below 'tag should be placed immediately before the first word'
    /**
     * Some <p>summary.
     *
     * <p>    Some paragraph.
     *
     * @see <a href="example.com">
     *     Documentation about GWT emulated source</a>
     */
    boolean emulated() {return false;}

    // 2 violations 12 lines below:
    //  'Redundant <p> tag.'
    //  'tag should be preceded with an empty line.'
    // violation 10 lines below 'tag should be preceded with an empty line.'
    // 2 violations 10 lines below:
    //  'tag should be preceded with an empty line.'
    //  'tag should be preceded with an empty line.'
    // 4 violations 8 lines below:
    //  '<p> tag should be placed immediately before the first word'
    //  'tag should be preceded with an empty line.'
    //  '<p> tag should be placed immediately before the first word'
    //  'tag should be preceded with an empty line.'
    /**<p>Some summary.<p>
     * <p>
     * <p><p>
     * <p>   Some paragraph.<p>*/
     class InnerInputJavadocParagraphIncorrect {
        // violation 2 lines below 'tag should be preceded with an empty line.'
        /**
         * Some Summary.<p>
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        // violation 2 lines below 'Redundant <p> tag.'
        // violation 7 lines below 'tag should be preceded with an empty line.'
        /**<p>
         *  Some Summary.
         *
         * <P>
         *
         * <p>
         *   Some paragraph.<p>
         * @see <a href="example.com">
         *     Documentation about GWT emulated source</a>
         */
        boolean emulated() {return false;}
    }

    InnerInputJavadocParagraphIncorrect anon = new InnerInputJavadocParagraphIncorrect() {
        // violation 3 lines below 'Redundant <p> tag.'
        // violation 3 lines below 'Empty line should be followed by <p> tag on the next line.'
            /**
         * <p>Some summary.
         *
         * Some paragraph.
         *
         * @since 8.0
         */
        public static final byte NUL = 0;
        // violation 3 lines below 'tag should be preceded with an empty line.'
        // violation 4 lines below 'tag should be placed immediately before the first word'
        /**
         *   Some summary.<p>
         *
         *  <p>  Some paragraph.
         *
         * @see <a href="example.com">
         *     Documentation about <p> GWT emulated source</a>
         */
        boolean emulated() {return false;}

        // violation 4 lines below 'Empty line should be followed by <p> tag on the next line.'
        // violation 4 lines below 'Empty line should be followed by <p> tag on the next line.'
        /**
         * Some Summary.
         *
         *
         * Some paragraph.
         */
         void doubleNewline() {}
    };
}
