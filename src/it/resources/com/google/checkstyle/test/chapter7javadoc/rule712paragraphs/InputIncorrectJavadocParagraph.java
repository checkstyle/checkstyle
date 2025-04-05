package com.google.checkstyle.test.chapter7javadoc.rule712paragraphs;

// 2 violations 5 lines below:
//  '<p> tag should be placed immediately before the first word'
//  '<p> tag should be preceded with an empty line.'
/**
 * Some Javadoc.
 * <p>
 * Some Javadoc.<p>
 */
// 2 violations 2 lines above:
//  '<p> tag should be placed immediately before the first word'
//  '<p> tag should be preceded with an empty line.'

class InputIncorrectJavadocParagraph {

  // 2 violations 4 lines below:
  //  '<p> tag should be placed immediately before the first word'
  //  '<p> tag should be preceded with an empty line.'
  /**
   * Some Javadoc.<p>
   *
   * <p>  Some Javadoc.
   *
   * @since 8.0
   */
  // violation 4 lines above '<p> tag should be placed immediately before the first word'
  public static final byte NUL = 0;

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

  // violation 4 lines below 'Redundant <p> tag.'
  // 2 violations 4 lines below:
  //  '<p> tag should be placed immediately before the first word'
  //  '<p> tag should be preceded with an empty line.'
  /**<p>Some Javadoc.
   * <p>
   * <p><p>
   * <p> Some Javadoc.<p>
   */
  // 3 violations 3 lines above:
  //  '<p> tag should be preceded with an empty line.'
  //  '<p> tag should be placed immediately before the first word'
  //  '<p> tag should be preceded with an empty line.'
  // 4 violations 6 lines above:
  //  '<p> tag should be placed immediately before the first word'
  //  '<p> tag should be preceded with an empty line.'
  //  '<p> tag should be placed immediately before the first word'
  //  '<p> tag should be preceded with an empty line.'
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

    // 2 violations 5 lines below:
    //  '<p> tag should be placed immediately before the first word'
    //  'Redundant <p> tag.'
    // violation 5 lines below '<p> tag should be placed immediately before the first word'
    // violation 6 lines below '<p> tag should be placed immediately before the first word'
    /**<p>
     *  Some Javadoc.
     *
     * <P>
     *
     * <p>
     *   Some Javadoc.<p>
     * @see <a href="example.com">Documentation about GWT emulated source</a>
     */
    // 2 violations 3 lines above:
    //  '<p> tag should be placed immediately before the first word'
    //  '<p> tag should be preceded with an empty line.'
    // violation 5 lines above 'Javadoc tag '@see' should be preceded with an empty line.'
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

  /**
   * Some summary.
   *
   * <p><h1>Testing...</h1></p>
   */
  // violation 2 lines above '<p> tag should not precede HTML block-tag '<h1>''
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
