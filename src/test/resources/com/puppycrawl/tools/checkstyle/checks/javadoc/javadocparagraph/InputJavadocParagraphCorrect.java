/*
JavadocParagraph
violateExecutionOnNonTightHtml = (default)false
allowNewlineParagraph = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocparagraph;

/**
 * Some Summary.
 *
 * <p>Some Paragraph.
 *
 */
class InputJavadocParagraphCorrect {

    /**
     * Some Summary.
     *   
     * <p>{@code function} will never be invoked with a null value.
     *
     * @since 8.0
     */
    public static final byte NUL = 0;

    /**
     * Some Summary.
     *
     * <p>Some Paragraph.
     *
     * <pre>
     * class Foo {
     *
     *   void foo() {}
     * }
     * </pre>
     *
     * @see <a href="example.com">
     *     Documentation about GWT emulated source</a>
     */
    boolean emulated() {return false;}

    /**
     * Some Summary.
     *
     *<pre>
     * Test
     * </pre>
     *
     * <pre>
     * Test
     * </pre>
     */
    boolean test() {return false;}

    /**
     * Some Summary.
     *
     * <p>Some Paragraph.
     *
     */
     class InnerInputJavadocParagraphCorrect {

        /**
         * Some Summary.
         *
         * <p>Some Paragraph.
         *
         * <p>Some Paragraph.
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /**
         * Some Summary.
         *
         * <p>Some Paragraph.
         *
         * @see <a href="example.com">
         *     Documentation about GWT emulated source</a>
         */
        boolean emulated() {return false;}
    }

     InnerInputJavadocParagraphCorrect anon = new InnerInputJavadocParagraphCorrect() {

            /**
         * Some Summary.
         *
         * <p>Some Paragraph.
         *
         * <p>Some Paragraph.
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        /** 
         * Some Javadoc with space at the end of first line.
         *
         * <p>Some Paragraph.
         *
         * <p>Some Paragraph.
         *
         * @see <a href="example.com">
         *     Documentation about GWT emulated source</a>
         */
        boolean emulated() {return false;}
    };
}

/*
 *  This comment has paragraph without '<p>' tag.
 *
 *  It's fine, because this is plain comment.
 */
class ClassWithPlainComment {}
