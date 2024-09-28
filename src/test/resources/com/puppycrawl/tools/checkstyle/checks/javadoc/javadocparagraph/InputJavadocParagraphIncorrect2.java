/*
JavadocParagraph
violateExecutionOnNonTightHtml = (default)false
allowNewlineParagraph = false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

// 2 violations 5 lines below:
//  '<p> tag should be placed immediately before the first word'
//  'tag should be preceded with an empty line.'
/**
 * Some Summary.
 * <p>
 * Some Paragraph.<p>
 */
// 2 violations 2 lines above:
//  '<p> tag should be placed immediately before the first word'
//  'tag should be preceded with an empty line.'
class InputJavadocParagraphIncorrect2 {

    // 2 violations 4 lines below:
    //  '<p> tag should be placed immediately before the first word'
    //  'tag should be preceded with an empty line.'
    /**
     * Some Summary.<P>
     *
     * <p>  Some paragraph.
     *
     * @since 8.0
     */
    // violation 4 lines above '<p> tag should be placed immediately before the first word'
    public static final byte NUL = 0;

    // violation 2 lines below 'tag should be preceded with an empty line.'
    /**
     * Some <p>Summary.
     *
     * <p>    Some paragraph.
     *
     * @see <a href="example.com">
     *     Documentation about GWT emulated source</a>
     */
    // violation 5 lines above '<p> tag should be placed immediately before the first word'
    boolean emulated() {return false;}

    // 3 violations 7 lines below:
    //  '<p> tag should be placed immediately before the first word'
    //  'tag should be preceded with an empty line.'
    //  'Redundant <p> tag.'
    // 2 violations 4 lines below:
    //  '<p> tag should be placed immediately before the first word'
    //  'tag should be preceded with an empty line.'
    /**<p>Some Summary.<p>
     * <p>
     * <p><p>
     * <p>   Some paragraph.<p>*/
    // 2 violations 2 lines above:
    //  '<p> tag should be placed immediately before the first word'
    //  '<p> tag should be preceded with an empty line.'
    // 2 violations 4 lines above:
    //  '<p> tag should be placed immediately before the first word'
    //  '<p> tag should be preceded with an empty line.'
     class InnerInputJavadocParagraphIncorrect {

        // 2 violations 4 lines below:
        //  '<p> tag should be placed immediately before the first word'
        //  '<p> tag should be preceded with an empty line.'
        /**
         * Some Summary.<p>
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        // 2 violations 5 lines below:
        //  '<p> tag should be placed immediately before the first word'
        //  'Redundant <p> tag.'
        // violation 5 lines below '<p> tag should be placed immediately before the first word'
        /**
         * <p>
         *  Some Summary.
         *
         * <P>
         *
         * <p>
         *   Some paragraph.<p>
         * @see <a href="example.com">
         *     Documentation about GWT emulated source</a>
         */
        // violation 5 lines above '<p> tag should be placed immediately before the first word'
        // 2 violations 5 lines above:
        //  '<p> tag should be placed immediately before the first word'
        //  '<p> tag should be preceded with an empty line.'
        boolean emulated() {return false;}

      /**
       * * <p>/^WARN/   Some Summary.<p>*/
      // 2 violations above:
      //  '<p> tag should be placed immediately before the first word'
      //  '<p> tag should be preceded with an empty line.'
      int yyy = 99;
    }
}
