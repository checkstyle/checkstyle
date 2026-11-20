/*
JavadocParagraph
allowNewlineParagraph = false
violateExecutionOnNonTightHtml = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

// 2 violations 8 lines below:
//  '<p> tag should be placed immediately before the first word'
//  'tag should be preceded with an empty line.'
// 2 violations 6 lines below:
//  '<p> tag should be placed immediately before the first word'
//  'tag should be preceded with an empty line.'
/**
 * Some Summary.
 * <p>
 * Some Paragraph.<p>
 */
class InputJavadocParagraphIncorrect2 {

    // 2 violations 5 lines below:
    //  '<p> tag should be placed immediately before the first word'
    //  'tag should be preceded with an empty line.'
    // violation 4 lines below '<p> tag should be placed immediately before the first word'
    /**
     * Some Summary.<P>
     *
     * <p>  Some paragraph.
     *
     * @since 8.0
     */
    public static final byte NUL = 0;

    // violation 3 lines below 'tag should be preceded with an empty line.'
    // violation 4 lines below '<p> tag should be placed immediately before the first word'
    /**
     * Some <p>Summary.
     *
     * <p>    Some paragraph.
     *
     * @see <a href="example.com">
     *     Documentation about GWT emulated source</a>
     */
    boolean emulated() {return false;}

    // 3 violations 16 lines below:
    //  'Redundant <p> tag.'
    //  '<p> tag should be placed immediately before the first word'
    //  'tag should be preceded with an empty line.'
    // 2 violations 13 lines below:
    //  '<p> tag should be placed immediately before the first word'
    //  'tag should be preceded with an empty line.'
    // 3 violations 11 lines below:
    //  '<p> tag should be preceded with an empty line.'
    //  '<p> tag should be placed immediately before the first word'
    //  '<p> tag should be preceded with an empty line.'
    // 4 violations 8 lines below:
    //  '<p> tag should be placed immediately before the first word'
    //  'tag should be preceded with an empty line.'
    //  '<p> tag should be placed immediately before the first word'
    //  'tag should be preceded with an empty line.'
    /**<p>Some Summary.<p>
     * <p>
     * <p><p>
     * <p>   Some paragraph.<p>*/
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

        // 2 violations 9 lines below:
        //  '<p> tag should be placed immediately before the first word'
        //  'Redundant <p> tag.'
        // violation 9 lines below '<p> tag should be placed immediately before the first word'
        // violation 10 lines below '<p> tag should be placed immediately before the first word'
        // 2 violations 10 lines below:
        //  '<p> tag should be placed immediately before the first word'
        //  '<p> tag should be preceded with an empty line.'
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
        boolean emulated() {return false;}

      // 4 violations 6 lines below:
      //  '<p> tag should be placed immediately before the first word'
      //  '<p> tag should be preceded with an empty line.'
      //  '<p> tag should be placed immediately before the first word'
      //  '<p> tag should be preceded with an empty line.'
      /**
       * * <p>   Some Summary.<p>*/
      int yyy = 99;
    }
}
