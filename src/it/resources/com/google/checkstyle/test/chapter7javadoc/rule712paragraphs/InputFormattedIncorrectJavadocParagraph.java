package com.google.checkstyle.test.chapter7javadoc.rule712paragraphs;

/**
 * Some Javadoc.
 *
 * <p>Some Javadoc.
 *
 * <p>
 */
// violation 2 lines above '<p> tag should be placed immediately before the first word'
class InputFormattedIncorrectJavadocParagraph {

  // violation 4 lines below '<p> tag should be placed immediately before the first word'
  /**
   * Some Javadoc.
   *
   * <p>
   *
   * <p>Some Javadoc.
   *
   * @since 8.0
   */
  public static final byte NUL = 0;

  /**
   * Some
   *
   * <p>Javadoc.
   *
   * <p>Some Javadoc.
   *
   * @see <a href="example.com">Documentation about GWT emulated source</a>
   */
  boolean emulated() {
    return false;
  }

  // violation 6 lines below '<p> tag should be placed immediately before the first word'
  // violation 7 lines below '<p> tag should be placed immediately before the first word'
  // violation 8 lines below '<p> tag should be placed immediately before the first word'
  /**
   * Some Javadoc.
   *
   * <p>
   *
   * <p>
   *
   * <p>
   *
   * <p>Some Javadoc.
   *
   * <p>
   */
  // violation 2 lines above '<p> tag should be placed immediately before the first word'
  class InnerInputCorrectJavaDocParagraphCheck {

    // violation 4 lines below '<p> tag should be placed immediately before the first word'
    /**
     * Some Javadoc.
     *
     * <p>
     *
     * @since 8.0
     */
    public static final byte NUL = 0;

    // violation 5 lines below '<p> tag should be placed immediately before the first word'
    // violation 8 lines below '<p> tag should be placed immediately before the first word'
    /**
     * Some Javadoc.
     *
     * <p>
     *
     * <p>Some Javadoc.
     *
     * <p>
     *
     * @see <a href="example.com">Documentation about GWT emulated source</a>
     */
    boolean emulated() {
      return false;
    }
  }

  InnerInputCorrectJavaDocParagraphCheck anon =
      new InnerInputCorrectJavaDocParagraphCheck() {

        /**
         * Some Javadoc.
         *
         * <p>Some Javadoc.
         *
         * @since 8.0
         */
        public static final byte NUL = 0;

        // violation 4 lines below '<p> tag should be placed immediately before the first word'
        /**
         * Some Javadoc.
         *
         * <p>
         *
         * <p>Some Javadoc.
         *
         * @see <a href="example.com">Documentation about
         *     <p>GWT emulated source</a>
         */
        boolean emulated() {
          return false;
        }
      };

  // 2 violations 6 lines below:
  //  '<p> tag should be placed immediately before the first word'
  //  '<p> tag should not precede HTML block-tag '<h1>''
  /**
   * Some summary.
   *
   * <p>
   *
   * <h1>Testing...</h1>
   */
  class InnerPrecedingPtag {

    // 2 violations 6 lines below:
    //  '<p> tag should be placed immediately before the first word'
    //  '<p> tag should not precede HTML block-tag '<ul>''
    /**
     * Some summary.
     *
     * <p>
     *
     * <ul>
     *   <p>
     *   <li>1 should NOT give violation as there is not empty line before
     * </ul>
     */
    public void foo() {}

    // 2 violations 6 lines below:
    //  '<p> tag should be placed immediately before the first word'
    //  '<p> tag should not precede HTML block-tag '<table>''
    /**
     * Some summary.
     *
     * <p>
     *
     * <table>
     *  </table>
     */
    public void fooo() {}

    // 2 violations 6 lines below:
    //  '<p> tag should be placed immediately before the first word'
    //  '<p> tag should not precede HTML block-tag '<pre>''
    /**
     * Some summary.
     *
     * <p>
     *
     * <pre>testing...</pre>
     *
     * <pre>testing...</pre>
     */
    public void foooo() {}
  }
}
