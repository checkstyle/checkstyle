/*
JavadocParagraph
violateExecutionOnNonTightHtml = (default)false
allowNewlineParagraph = false


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

public class InputJavadocParagraphIncorrect3 {
    InputJavadocParagraphIncorrect3 anon = new InputJavadocParagraphIncorrect3() {

        // violation 2 lines below 'Redundant <p> tag.'
        /**
         * <p>Some Summary.
         *
         * Some Paragraph.
         *
         * @since 8.0
         */
        // violation 5 lines above 'Empty line should be followed by <p> tag on the next line.'
        public static final byte NUL = 0;

        // 2 violations 5 lines below:
        //  '<p> tag should be placed immediately before the first word'
        //  '<p> tag should be preceded with an empty line.'
        // violation 4 lines below '<p> tag should be placed immediately before the first word'
        /**
         *   Some Summary.<p>
         *
         *  <p>  Some Paragraph.
         *
         * @see <a href="example.com">
         *     Documentation about <p> GWT emulated source</a>
         */
        boolean emulated() {return false;}

        // violation 3 lines below 'Empty line should be followed by <p> tag on the next line.'
        /**
         * Double newline.
         *
         *
         * Some Paragraph.
         */
        // violation 3 lines above 'Empty line should be followed by <p> tag on the next line.'
        void doubleNewline() {}
    };
}
