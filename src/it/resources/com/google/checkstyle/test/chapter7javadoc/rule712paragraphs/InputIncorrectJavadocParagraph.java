package com.google.checkstyle.test.chapter7javadoc.rule712paragraphs;

// 2 violations 8 lines below:
//  '<p> tag should be placed immediately before the first word'
//  '<p> tag should be preceded with an empty line.'
// 2 violations 6 lines below:
//  '<p> tag should be placed immediately before the first word'
//  '<p> tag should be preceded with an empty line.'
/**
 * Some Javadoc.
 * <p>
 * Some Javadoc.<p>
 */

class InputIncorrectJavadocParagraph {

  // 2 violations 5 lines below:
  //  '<p> tag should be placed immediately before the first word'
  //  '<p> tag should be preceded with an empty line.'
  // violation 4 lines below '<p> tag should be placed immediately before the first word'
  /**
   * Some Javadoc.<p>
   *
   * <p>  Some Javadoc.
   *
   * @since 8.0
   */
  public static final byte NUL = 0;

  // violation 2 lines below 'First sentence of Javadoc is missing an ending period.'
  // violation 2 lines below '<p> tag should be preceded with an empty line.'
  /**
   * Some <p>Javadoc.
   *
   * <p>Some Javadoc.
   *
   * @see <a href="example.com">Documentation about GWT emulated source</a>
   */
  boolean emulated() {
    return false;
  }

  // violation 14 lines below "Javadoc content should start from the next line."
  // violation 13 lines below 'Redundant <p> tag.'
  // 2 violations 13 lines below:
  //  '<p> tag should be placed immediately before the first word'
  //  '<p> tag should be preceded with an empty line.'
  // 3 violations 11 lines below:
  //  '<p> tag should be preceded with an empty line.'
  //  '<p> tag should be placed immediately before the first word'
  //  '<p> tag should be preceded with an empty line.'
  // 4 violations 8 lines below:
  //  '<p> tag should be placed immediately before the first word'
  //  '<p> tag should be preceded with an empty line.'
  //  '<p> tag should be placed immediately before the first word'
  //  '<p> tag should be preceded with an empty line.'
  /**<p>Some Javadoc.
   * <p>
   * <p><p>
   * <p> Some Javadoc.<p>
   */
  class InnerInputCorrectJavaDocParagraphCheck {

    // 2 violations 4 lines below:
    //  '<p> tag should be placed immediately before the first word'
    //  '<p> tag should be preceded with an empty line.'
    /**
     * Some Javadoc.<p>
     *
     * @since 8.0
     */
    public static final byte NUL = 0;

    // 3 violations 10 lines below:
    //  'Javadoc content should start from the next line.'
    //  '<p> tag should be placed immediately before the first word'
    //  'Redundant <p> tag.'
    // violation 9 lines below '<p> tag should be placed immediately before the first word'
    // violation 10 lines below '<p> tag should be placed immediately before the first word'
    // 2 violations 10 lines below:
    //  '<p> tag should be placed immediately before the first word'
    //  '<p> tag should be preceded with an empty line.'
    // violation 8 lines below 'Javadoc tag '@see' should be preceded with an empty line.'
    /**<p>
     *  Some Javadoc.
     *
     * <P>
     *
     * <p>
     *   Some Javadoc.<p>
     * @see <a href="example.com">Documentation about GWT emulated source</a>
     */
    boolean emulated() {
      return false;
    }
  }

  InnerInputCorrectJavaDocParagraphCheck anon =
      new InnerInputCorrectJavaDocParagraphCheck() {

        // violation 2 lines below 'Redundant <p> tag.'
        /**
         * <p>Some Javadoc.
         *
         * <p>Some Javadoc.
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        // 2 violations 5 lines below:
        //  '<p> tag should be placed immediately before the first word'
        //  '<p> tag should be preceded with an empty line.'
        // violation 4 lines below '<p> tag should be placed immediately before the first word'
        /**
         *   Some Javadoc.<p>
         *
         *  <p>  Some Javadoc.
         *
         * @see <a href="example.com">Documentation about <p> GWT emulated source</a>
         */
        boolean emulated() {
          return false;
        }
      };

  // violation 4 lines below '<p> tag should not precede HTML block-tag '<h1>''
  /**
   * Some summary.
   *
   * <p><h1>Testing...</h1></p>
   */
  class InnerPrecedingPtag {
    // 2 violations 6 lines below:
    //  '<p> tag should be placed immediately before the first word'
    //  '<p> tag should not precede HTML block-tag '<ul>''
    /**
     * Some summary.
     *
     *<p>
     *  <ul>
     *    <p>
     *      <li>1</li> should NOT give violation as there is not empty line before
     *    </p>
     *  </ul>
     *</p>
     */
    public void foo() {}

    // 2 violations 6 lines below:
    //  '<p> tag should be placed immediately before the first word'
    //  '<p> tag should not precede HTML block-tag '<table>''
    /**
     *  Some summary.
     *
     * <p>
     *  <table>
     *  </table>
     * </p>
     */
    public void fooo() {}

    // 2 violations 6 lines below:
    //  '<p> tag should be placed immediately before the first word'
    //  '<p> tag should not precede HTML block-tag '<pre>''
    /**
     * Some summary.
     *
     * <p>
     *   <pre>testing...</pre>
     *   <pre>testing...</pre>
     * </p>
     */
    public void foooo() {}
  }
}
